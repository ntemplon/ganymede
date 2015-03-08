/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.function;

/**
 *
 * @author Nathan Templon
 */
public class LinearFunction implements SingleVariableRealFunction {
    
    // Fields
    private final double slope;
    private final double intercept;
    
    
    // Properties
    public final double getSlope() {
        return this.slope;
    }
    
    public final double getIntercept() {
        return this.intercept;
    }
    
    
    // Initialization
    public LinearFunction(double slope, double intercept) {
        this.slope = slope;
        this.intercept = intercept;
    }
    
    
    @Override
    public Double apply(Double x) {
        return intercept + slope * x;
    }
    
}
