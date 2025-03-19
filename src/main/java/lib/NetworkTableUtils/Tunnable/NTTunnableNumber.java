package lib.NetworkTableUtils.Tunnable;

import lib.NetworkTableUtils.Tunnable.Raw.NTTunnableBase;

public class NTTunnableNumber extends NTTunnableBase<Double>{

    public NTTunnableNumber(String key, double defaultValue, boolean rebootPersist){
        super("TunnableNumbers", key, defaultValue, rebootPersist);
    }

    @Override
    protected void setDefaultValue(Double defaultValue) {
        entry.setDefaultDouble(defaultValue);
    }

    @Override
    public Double get() {
        value = entry.getDouble(value);
        return value;
    }

    @Override
    public void set(Double newValue) {
        value = newValue;
        entry.setDouble(newValue);
    }

}
