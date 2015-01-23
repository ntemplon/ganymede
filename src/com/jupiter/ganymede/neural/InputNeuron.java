/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.event.Event;
import com.jupiter.ganymede.neural.AbstractNeuron.NeuronFiredArgs;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public class InputNeuron implements Neuron {

    // Events
    public final Event<NeuronFiredArgs> fired = new Event<>();


    // Fields
    private final Set<Synapse> axonConnections = new LinkedHashSet<>();
    private final Set<Synapse> axonViewer = Collections.unmodifiableSet(this.axonConnections);
    private final Set<Synapse> dendriteViewer = Collections.unmodifiableSet(new HashSet<>());
    
    private double value;


    // Properties
    @Override
    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
        
        this.fired.dispatch(new NeuronFiredArgs(this, this.value));
    }
    
    @Override
    public final Set<Synapse> getDendriteConnections() {
        return this.dendriteViewer;
    }
    
    @Override
    public final Set<Synapse> getAxonConnections() {
        return this.axonViewer;
    }


    // Initialization
    public InputNeuron() {

    }


    // Public Methods
    @Override
    public Event<AbstractNeuron.NeuronFiredArgs> getFiredEvent() {
        return this.fired;
    }

    @Override
    public void evaluate() {
        
    }

    @Override
    public void addDendriteConnection(Synapse dendrite) {
        
    }
    
    @Override
    public void addAxonConnection(Synapse axon) {
        this.axonConnections.add(axon);
    }

}
