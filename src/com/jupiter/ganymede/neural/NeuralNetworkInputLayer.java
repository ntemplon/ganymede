/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;

/**
 *
 * @author Nathan Templon
 */
public class NeuralNetworkInputLayer extends NeuralNetworkLayer {
    
    // Static Methods
    private static InputNeuron[] getInputNeurons(int dimension) {
        InputNeuron[] neurons = new InputNeuron[dimension];
        for(int i = 0; i < neurons.length; i++) {
            neurons[i] = new InputNeuron();
        }
        return neurons;
    }
    
    
    // Initialization
    public NeuralNetworkInputLayer(int dimension) {
        super(getInputNeurons(dimension));
    }
    
    
    // Public Methods
    public void setValues(Vector input) {
        if (input.getDimension() != this.getNeurons().size()) {
            throw new IllegalArgumentException("Input vectors must match the number of required inputs!");
        }
        
        for(int i = 0; i < input.getDimension(); i++) {
            this.getNeurons().get(i).setValue(input.getComponent(i + 1));
        }
    }
    
}
