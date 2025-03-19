package lib.NetworkTableUtils.ComplexPublishers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ArrayList;
import java.util.List;

public class AutoPublisher extends SubsystemBase {
    private static final List<Runnable> registeredPublishers = new ArrayList<>();
    private static final AutoPublisher instance = new AutoPublisher();

    private AutoPublisher() {}

    public static void register(Runnable publisher) {
        registeredPublishers.add(publisher);
    }

    @Override
    public void periodic() {
        for (Runnable publisher : registeredPublishers) {
            publisher.run();
        }
    }
}
