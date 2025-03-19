package lib.NetworkTableUtils.ComplexPublishers;

import lib.NetworkTableUtils.NTSubsystem.Interfaces.AutoNetworkPublisher;
import lib.NetworkTableUtils.NormalPublishers.NTData;
import lib.NetworkTableUtils.NormalPublishers.NTArrayData;
import lib.NetworkTableUtils.SupplierPublishers.NTSupplierBoolean;
import lib.NetworkTableUtils.SupplierPublishers.NTSupplierBooleanArray;
import lib.NetworkTableUtils.SupplierPublishers.NTSupplierDouble;
import lib.NetworkTableUtils.SupplierPublishers.NTSupplierDoubleArray;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class AutoPublisherInit {

    public static void registerAnnotatedPublishers(Object subsystem) {
        for (Method method : subsystem.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(AutoNetworkPublisher.class)) {
                String key = method.getAnnotation(AutoNetworkPublisher.class).key();
                method.setAccessible(true);

                Supplier<?> supplier = () -> {
                    try {
                        return method.invoke(subsystem);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                };

                Class<?> returnType = method.getReturnType();

                if (returnType == Pose3d.class) {
                    NTData<Pose3d> ntPose3D = new NTData<>(key, Pose3d.struct);
                    AutoPublisher.register(() -> ntPose3D.sendData((Pose3d) supplier.get()));
                }
                
                else if (returnType == Pose2d.class) {
                    NTData<Pose2d> ntPose2D = new NTData<>(key, Pose2d.struct);
                    AutoPublisher.register(() -> ntPose2D.sendData((Pose2d) supplier.get()));
                }

                else if (returnType == Rotation2d.class) {
                    NTData<Rotation2d> ntRotation = new NTData<>(key, Rotation2d.struct);
                    AutoPublisher.register(() -> ntRotation.sendData((Rotation2d) supplier.get()));
                }

                else if (returnType == Rotation3d.class) {
                    NTData<Rotation3d> ntRotation = new NTData<>(key, Rotation3d.struct);
                    AutoPublisher.register(() -> ntRotation.sendData((Rotation3d) supplier.get()));
                }

                else if (returnType == Translation2d.class) {
                    NTData<Translation2d> ntXY = new NTData<>(key, Translation2d.struct);
                    AutoPublisher.register(() -> ntXY.sendData((Translation2d) supplier.get()));
                }

                else if (returnType == Translation3d.class) {
                    NTData<Translation3d> ntXY = new NTData<>(key, Translation3d.struct);
                    AutoPublisher.register(() -> ntXY.sendData((Translation3d) supplier.get()));
                }

                else if (returnType == ChassisSpeeds.class) {
                    NTData<ChassisSpeeds> ntChassisSpeeds = new NTData<>(key, ChassisSpeeds.struct);
                    AutoPublisher.register(() -> ntChassisSpeeds.sendData((ChassisSpeeds) supplier.get()));
                }

                else if (returnType == SwerveModuleState[].class) {
                    NTArrayData<SwerveModuleState> moduleStates = new NTArrayData<>(key, SwerveModuleState.struct);
                    AutoPublisher.register(()-> moduleStates.sendData((SwerveModuleState[]) supplier.get()));
                }

                else if (returnType == Double[].class || returnType == double[].class) {
                    Supplier<double[]> doubleSupplier = () -> (double[]) supplier.get();
                    NTSupplierDoubleArray ntDouble = new NTSupplierDoubleArray(key, doubleSupplier);
                    AutoPublisher.register(ntDouble::update);
                }
                
                else if (returnType == Double.class || returnType == double.class) {
                    Supplier<Double> doubleSupplier = () -> (Double) supplier.get();
                    NTSupplierDouble ntDouble = new NTSupplierDouble(key, doubleSupplier);
                    AutoPublisher.register(ntDouble::update);
                }
                
                else if (returnType == Boolean.class || returnType == boolean.class) {
                    Supplier<Boolean> booleanSupplier = ()-> (Boolean) supplier.get();
                    NTSupplierBoolean ntBoolean = new NTSupplierBoolean(key, booleanSupplier);
                    AutoPublisher.register(ntBoolean::update);
                }

                else if (returnType == Boolean[].class || returnType == boolean[].class) {
                    Supplier<Boolean[]> booleanSupplier = ()-> (Boolean[]) supplier.get();
                    NTSupplierBooleanArray ntBoolean = new NTSupplierBooleanArray(key, booleanSupplier);
                    AutoPublisher.register(ntBoolean::update);
                }
                
                else{
                    System.err.println("[AutoNetworkPublisher] Not Supported Data type!" + returnType.getSimpleName());
                }
            }
        }
    }
}
