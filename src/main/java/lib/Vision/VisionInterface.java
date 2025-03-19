package lib.Vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;

public interface VisionInterface{

    public static record TagObservation(Rotation2d tx, Rotation2d ty, double ta){}

    public static record PoseObservation(double timestamps, Pose3d pose, double ambiguity, int tagCount, double averageTagDistance) {}

    public static class CameraResults {
        public boolean isConnected = false;
        public TagObservation lastestTagObservation = new TagObservation(new Rotation2d(), new Rotation2d(), 0);
        public PoseObservation[] lastestObservations = new PoseObservation[0];
        public int[] tagIds = new int[0];   
    }

    public default void updateResults(CameraResults results){}
   
}
