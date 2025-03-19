package lib.Math;

import java.util.function.DoubleSupplier;

public class SmoothDoubleSupplier {

    private double exponent;
    
    public SmoothDoubleSupplier(double kJ){
        this.exponent = kJ;
    }
    
    public DoubleSupplier filter(DoubleSupplier input){
        return ()-> Math.signum(input.getAsDouble()) * Math.pow(Math.abs(input.getAsDouble()), exponent);
    }
    
}

