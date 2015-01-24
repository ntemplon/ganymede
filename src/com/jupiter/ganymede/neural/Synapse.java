/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.event.Event;

/**
 *
 * @author Nathan Templon
 */
public final class Synapse {

    // Fields
    public final Event<WeightChangedArgs> weightChanged = new Event<>();

    private final Neuron source;
    private final Neuron target;
    private double weight;
    
    private double value;


    // Properties
    public Neuron getSource() {
        return this.source;
    }

    public Neuron getTarget() {
        return this.target;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        if (this.weight != weight) {
            double old = this.weight;
            this.weight = weight;
            this.weightChanged.dispatch(new WeightChangedArgs(old, this.weight));
        }
    }


    // Initialization
    public Synapse(Neuron source, Neuron target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
    
    
    // Public Methods
    public double getValue() {
        return this.value;
    }
    
    public void connect() {
        this.source.getFiredEvent().addListener(
                (AbstractNeuron.NeuronFiredArgs args) -> Synapse.this.value = args.value * Synapse.this.getWeight()
        );
        this.source.addAxonConnection(this);
        this.target.addDendriteConnection(this);
    }
    
    
    // Inner Classes
    public static class WeightChangedArgs {
        
        // Fields
        public final double oldWeight;
        public final double newWeight;
        
        
        // Initialization
        public WeightChangedArgs(double oldWeight, double newWeight) {
            this.oldWeight = oldWeight;
            this.newWeight = newWeight;
        }
        
    }

}
