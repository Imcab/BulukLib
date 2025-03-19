package lib.NetworkTableUtils.ComplexPublishers;

import java.util.ArrayList;
import java.util.List;

public class AutoPublisherManager {
    private static final List<Object> pendingSubsystems = new ArrayList<>();
    private static boolean initialized = false;

    public static void register(Object subsystem) {
        if (initialized) {
            AutoPublisherInit.registerAnnotatedPublishers(subsystem);
        } else {
            pendingSubsystems.add(subsystem);
        }
    }

    public static void initializeAll() {
        initialized = true;
        for (Object subsystem : pendingSubsystems) {
            AutoPublisherInit.registerAnnotatedPublishers(subsystem);
        }
        pendingSubsystems.clear();
    }
}
