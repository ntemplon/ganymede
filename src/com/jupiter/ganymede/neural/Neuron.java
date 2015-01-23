/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.event.Event;
import com.jupiter.ganymede.neural.AbstractNeuron.NeuronFiredArgs;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public interface Neuron {
    
    // Properties
    double getValue();
    Event<NeuronFiredArgs> getFiredEvent();
    
    Set<Synapse> getDendriteConnections();
    Set<Synapse> getAxonConnections();
    
    
    // Public Methods
    void evaluate();
    void addDendriteConnection(Synapse dendrite);
    void addAxonConnection(Synapse axon);
    
}
