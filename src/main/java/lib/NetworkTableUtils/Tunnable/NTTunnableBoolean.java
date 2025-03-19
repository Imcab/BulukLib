package lib.NetworkTableUtils.Tunnable;

import lib.NetworkTableUtils.Tunnable.Raw.NTTunnableBase;

public class NTTunnableBoolean extends NTTunnableBase<Boolean>{

    public NTTunnableBoolean(String key, boolean defaultValue, boolean rebootPersist){
        super("TunnableBooleans", key, defaultValue, rebootPersist);
    }

    @Override
    protected void setDefaultValue(Boolean defaultValue) {
        entry.setDefaultBoolean(defaultValue);
    }

    @Override
    public Boolean get() {
        value = entry.getBoolean(value);
        return value;
    }

    @Override
    public void set(Boolean newValue) {
        value = newValue;
        entry.setBoolean(newValue);
    }

    
}
