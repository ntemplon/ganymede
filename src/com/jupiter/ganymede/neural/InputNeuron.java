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
public class InputNeuron extends Neuron {

    // Initialization
    public InputNeuron() {

    }


    // Public Methods
    @Override
    public void evaluate() {
        
    }
    
    @Override
    public void addDendriteConnection(Synapse axon) {
        
    }
    
    @Override
    public double getFunctionValue(double sum) {
        return sum;
    }
    
    @Override
    public double getFunctionDerivative(double sum) {
        return 0.0;
    }

}
