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
public class SigmoidNeuron extends Neuron {

    // Public Methods
    @Override
    public double getFunctionValue(double totalInput) {
        return 1.0 / (1.0 + Math.exp(-1.0 * totalInput));
    }

    @Override
    public double getFunctionDerivative(double totalInput) {
        double exp = Math.exp(-1.0 * totalInput);
        double denTerm = 1.0 + exp;
        return (-1.0 * exp) / (denTerm * denTerm);
    }
    
}
