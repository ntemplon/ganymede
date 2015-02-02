/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

/**
 *
 * @author Nathan Templon
 */
public class ConstantValueNeuron extends Neuron {

    // Fields
    private final double value;
    
    
    // Initialization
    public ConstantValueNeuron(double value) {
        this.value = value;
    }
    
    
    // Public Methods
    @Override
    public double getFunctionValue(double totalInput) {
        return this.value;
    }

    @Override
    public double getFunctionDerivative(double totalInput) {
        return 0.0;
    }
    
}
