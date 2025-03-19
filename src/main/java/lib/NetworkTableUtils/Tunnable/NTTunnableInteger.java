package lib.NetworkTableUtils.Tunnable;

import lib.NetworkTableUtils.Tunnable.Raw.NTTunnableBase;

public class NTTunnableInteger extends NTTunnableBase<Long>{

    public NTTunnableInteger(String key, Long defaultValue, boolean rebootPersist){
        super("TunnableNumbers", key, defaultValue, rebootPersist);
    }

    @Override
    protected void setDefaultValue(Long defaultValue) {
        entry.setDefaultInteger(defaultValue);
    }

    @Override
    public Long get() {
        value = entry.getInteger(value);
        return value;
    }

    @Override
    public void set(Long newValue) {
        value = newValue;
        entry.setDouble(newValue);
    }
    
}
