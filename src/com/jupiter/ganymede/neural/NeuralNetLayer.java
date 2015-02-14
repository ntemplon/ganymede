/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nathan Templon
 */
public class NeuralNetLayer {
    
    // Fields
    private final List<Neuron> neurons;
    private final List<Neuron> neuronAccess;
    
    
    // Properties
    public final int getNeuronCount() {
        return this.neurons.size();
    }
    
    public final List<Neuron> getNeurons() {
        return neuronAccess;
    }
    
    
    // Initialization
    public NeuralNetLayer() {
        this(new Neuron());
    }
    
    public NeuralNetLayer(Neuron... neurons) {
        this.neurons = Arrays.asList(neurons);
        this.neuronAccess = Collections.unmodifiableList(this.neurons);
    }
    
    public NeuralNetLayer(int size, Neuron template) {
        this.neurons = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            this.neurons.add(template);
        }
        this.neuronAccess = Collections.unmodifiableList(this.neurons);
    }
    
}
