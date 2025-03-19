package lib.NetworkTableUtils.Tunnable.Raw;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public abstract class NTTunnableBase<T> implements NTTunnable<T>{
    protected final NetworkTableEntry entry;
    protected T value;

    public NTTunnableBase(String TableKey, String key, T defaultValue, boolean rebootPersist){
        this.value = defaultValue;
        NetworkTable table = NetworkTableInstance.getDefault().getTable(TableKey);
        entry = table.getEntry(key);

        setDefaultValue(defaultValue);
        handlePersistence(rebootPersist, defaultValue);
    }

    protected abstract void setDefaultValue(T defaultValue);

    private void handlePersistence(boolean rebootPersist, T defaultValue) {
        if (rebootPersist) {
            entry.setPersistent();
        }

        if (!entry.exists()) {
            set(defaultValue);
        }

        if (!rebootPersist && entry.isPersistent()) {
            entry.clearPersistent();
        }
    }
}
