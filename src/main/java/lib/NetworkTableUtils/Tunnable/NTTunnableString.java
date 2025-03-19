package lib.NetworkTableUtils.Tunnable;

import lib.NetworkTableUtils.Tunnable.Raw.NTTunnableBase;

public class NTTunnableString extends NTTunnableBase<String>{

    public NTTunnableString(String key, String defaultValue, boolean rebootPersist){
        super("TunnableStrings", key, defaultValue, rebootPersist);
    }

    @Override
    protected void setDefaultValue(String defaultValue) {
        entry.setDefaultString(defaultValue);
    }

    @Override
    public String get() {
        value = entry.getString(value);
        return value;
    }

    @Override
    public void set(String newValue) {
        value = newValue;
        entry.setString(newValue);
    }
    
}
