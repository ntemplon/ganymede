/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public class NeuralNetworkLayer {
    
    // Fields
    private final Set<Neuron> neurons = new LinkedHashSet<>();
    private final Set<Neuron> neuronsView = Collections.unmodifiableSet(this.neurons);
    
    private Vector valueVector;
    
    
    // Properties
    public final Set<Neuron> getNeurons() {
        return this.neuronsView;
    }
    
    public final Vector getValueVector() {
        return this.valueVector;
    }
    
    
    // Initialization
    public NeuralNetworkLayer(Neuron... neurons) {
        this.neurons.addAll(Arrays.asList(neurons));
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
