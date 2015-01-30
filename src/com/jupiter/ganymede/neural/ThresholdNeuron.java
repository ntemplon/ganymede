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
public class ThresholdNeuron extends Neuron {

    // Fields
    private final double threshold;
    
    
    // Initialization
    public ThresholdNeuron(double threshold) {
        this.threshold = threshold;
    }
    
    
    // Public Methods
    @Override
    public double getFunctionValue(double totalInput) {
        if (totalInput >= this.threshold) {
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public double getFunctionDerivative(double totalInput) {
        return 0.0;
    }
    
}
