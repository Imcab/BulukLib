package lib.NetworkTableUtils.NTSubsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.NetworkTableUtils.ComplexPublishers.AutoPublisherManager;

public abstract class NetworkSubsystem extends SubsystemBase{
    public NetworkSubsystem(){
        AutoPublisherManager.register(this);
    }
}
