package lib.Vision;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.photonvision.PhotonCamera;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;

public class PhotonCam implements VisionInterface{

    private final PhotonCamera cam;
    private final AprilTagFieldLayout layout;
    private final Transform3d robotToCam;

    public PhotonCam(String key,Transform3d robotToCam){
        this.cam = new PhotonCamera(key);
        this.layout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);
        this.robotToCam = robotToCam;
    }

    @Override
    public void updateResults(CameraResults results){

        results.isConnected = cam.isConnected();

        Set<Short> tagIds = new HashSet<>();
        List<PoseObservation> poseObservations = new LinkedList<>();

        for(var result : cam.getAllUnreadResults()){
            if (result.hasTargets()) {
                results.lastestTagObservation = new TagObservation(
                    Rotation2d.fromDegrees(result.getBestTarget().getYaw()),
                    Rotation2d.fromDegrees(result.getBestTarget().getPitch()),
                    result.getBestTarget().getArea());
            }else{
                results.lastestTagObservation = new TagObservation(new Rotation2d(), new Rotation2d(), 0);
            }

            if (result.multitagResult.isPresent()) {
                var multiTagResult = result.multitagResult.get();

                Transform3d fieldToCam = multiTagResult.estimatedPose.best;
                Transform3d fieldToRobot = fieldToCam.plus(robotToCam.inverse());
                
                Pose3d botPose = new Pose3d(fieldToRobot.getTranslation(), fieldToRobot.getRotation());

                double totalTagDistance = 0.0;

                for(var target : result.targets){
                    totalTagDistance += target.getBestCameraToTarget().getTranslation().getNorm();
                }

                tagIds.addAll(multiTagResult.fiducialIDsUsed);

                poseObservations.add(new PoseObservation(
                    result.getTimestampSeconds(),
                    botPose,
                    multiTagResult.estimatedPose.ambiguity,
                    multiTagResult.fiducialIDsUsed.size(),
                    totalTagDistance / result.targets.size()));
            }else if (!result.targets.isEmpty()) {
                var target = result.targets.get(0);

                var tagPose = layout.getTagPose(target.fiducialId);

                if (tagPose.isPresent()) {
                    Transform3d fieldToTarget = new
                    Transform3d(tagPose.get().getTranslation(), tagPose.get().getRotation());

                    Transform3d cameraToTarget = target.bestCameraToTarget;
                    Transform3d fieldToCamera = fieldToTarget.plus(cameraToTarget.inverse());
                    Transform3d fieldToRobot = fieldToCamera.plus(robotToCam.inverse());
                    Pose3d robotPose = new Pose3d(fieldToRobot.getTranslation(), fieldToRobot.getRotation());

                    tagIds.add((short) target.fiducialId);

                    poseObservations.add(new PoseObservation(
                        result.getTimestampSeconds(),
                        robotPose,
                        target.poseAmbiguity,
                        1,
                        cameraToTarget.getTranslation().getNorm()));
                }
            }
        }

        results.lastestObservations = new PoseObservation[poseObservations.size()];

        for (int i = 0; i < poseObservations.size(); i++) {
            results.lastestObservations[i] = poseObservations.get(i);
        }

        results.tagIds = new int[tagIds.size()];
        int i = 0;
        for (int id : tagIds) {
          results.tagIds[i++] = id;
        }
    }

}
