/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public class NeuralNetworkInputLayer {
    
    // Fields
    private final InputNeuron[] inputNeurons;
    private final Set<Neuron> neurons = new LinkedHashSet<>();
    private final Set<Neuron> neuronView = Collections.unmodifiableSet(this.neurons);
    
    
    // Properties
    public Set<Neuron> getNeurons() {
        return this.neuronView;
    }
    
    
    // Initialization
    public NeuralNetworkInputLayer(int dimension) {
        this.inputNeurons = new InputNeuron[dimension];
        
        for (int i = 0; i < dimension; i++) {
            this.inputNeurons[i] = new InputNeuron();
            this.neurons.add(this.inputNeurons[i]);
        }
    }
    
    
    // Public Methods
    public void setValues(Vector input) {
        if (input.getDimension() != this.inputNeurons.length) {
            throw new IllegalArgumentException("Input vectors must match the number of required inputs!");
        }
        
        for(int i = 0; i < input.getDimension(); i++) {
            this.inputNeurons[i].setValue(input.getComponent(i + 1));
        }
    }
    
}
