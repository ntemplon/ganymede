/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author Nathan Templon
 */
public class NeuralNetworkLayer {
    
    // Fields
    private final List<Neuron> neurons;
    private final List<Neuron> neuronsView;
    
    private Vector valueVector;
    
    
    // Properties
    public final List<Neuron> getNeurons() {
        return this.neuronsView;
    }
    
    public final Vector getValueVector() {
        return this.valueVector;
    }
    
    
    // Initialization
    public NeuralNetworkLayer(Neuron... neurons) {
        this.neurons = new ArrayList<>(new LinkedHashSet<>(Arrays.asList(neurons))); // Removes duplicates
        this.neuronsView = Collections.unmodifiableList(this.neurons);
    }
    
    
    // Public Methods
    public void fire() {
        this.neurons.parallelStream().forEach(
                (Neuron neuron) -> neuron.evaluate()
        );
        
        double[] values = new double[this.neurons.size()];
        int i = 0;
        for(Neuron neuron : this.neurons) {
            values[i] = neuron.getValue();
            i++;
        }
        
        this.valueVector = new Vector(values);
    }
    
}
