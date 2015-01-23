/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.event.Event;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public abstract class AbstractNeuron implements Neuron {
    
    // Events
    public final Event<NeuronFiredArgs> fired = new Event<>();
    
    
    // Fields
    private double sum;
    private double value;
    private final Set<Synapse> dendrites = new HashSet<>();
    private final Set<Synapse> dendriteViewer = Collections.unmodifiableSet(this.dendrites);
    private final Set<Synapse> axonConnections = new HashSet<>();
    private final Set<Synapse> axonViewer = Collections.unmodifiableSet(this.axonConnections);
    
    
    // Properties
    @Override
    public double getValue() {
        return this.value;
    }
    
    @Override
    public Set<Synapse> getAxonConnections() {
        return this.axonViewer;
    }
    
    @Override
    public Set<Synapse> getDendriteConnections() {
        return this.dendriteViewer;
    }
    
    
    // Initialization
    public AbstractNeuron() {
        
    }
    
    
    // Public Methods
    @Override
    public void evaluate() {
        this.sum = 0;
        
        this.dendrites.stream().forEach((Synapse dendrite) -> {
            this.sum += dendrite.getValue();
        });
        
        this.value = this.getFunctionValue(this.sum);
    }
    
    @Override
    public void addDendriteConnection(Synapse dendrite) {
        if (dendrite != null) {
            this.dendrites.add(dendrite);
        }
    }
    
    @Override
    public void addAxonConnection(Synapse axon) {
        this.axonConnections.add(axon);
    }
    
    @Override
    public Event<NeuronFiredArgs> getFiredEvent() {
        return this.fired;
    }
    
    public abstract double getFunctionValue(double totalInput);
    
    
    // Inner Classes
    public static class NeuronFiredArgs {
        
        // Fields
        public final Neuron neuron;
        public final double value;
        
        
        // Initialization
        public NeuronFiredArgs(Neuron neuron, double value) {
            this.neuron = neuron;
            this.value = value;
        }
        
    }
    
}
