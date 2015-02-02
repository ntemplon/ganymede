/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.property.Property;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public abstract class Neuron {
    
    // Fields
    public final Property<Integer> id = new Property<>();
    
    private double sum;
    private double value;
    private double derivative;
    private final Set<Synapse> dendrites = new HashSet<>();
    private final Set<Synapse> dendriteViewer = Collections.unmodifiableSet(this.dendrites);
    private final Set<Synapse> axonConnections = new HashSet<>();
    private final Set<Synapse> axonViewer = Collections.unmodifiableSet(this.axonConnections);
    
    private final Set<ValueChangeListener> listeners = new LinkedHashSet<>();
    

    // Properties
    public double getValue() {
        return this.value;
    }
    
    protected void setValue(double value) {
        double old = this.value;
        this.value = value;
        this.listeners.stream().forEach((ValueChangeListener listener) -> listener.valueChanged(old, value));
    }

    public double getValueDerivative() {
        return this.derivative;
    }

    public Set<Synapse> getAxonConnections() {
        return this.axonViewer;
    }

    public Set<Synapse> getDendriteConnections() {
        return this.dendriteViewer;
    }


    // Public Methods
    public void evaluate() {
        this.sum = 0;
        
        this.dendrites.stream().forEach((Synapse dendrite) -> {
            this.sum += dendrite.getValue();
        });
        
        this.value = this.getFunctionValue(this.sum);
        this.derivative = this.getFunctionDerivative(this.sum);
    }

    public void addDendriteConnection(Synapse dendrite) {
        if (dendrite != null) {
            this.dendrites.add(dendrite);
        }
    }

    public void addAxonConnection(Synapse axon) {
        this.axonConnections.add(axon);
    }
    
    public abstract double getFunctionValue(double totalInput);
    
    public abstract double getFunctionDerivative(double totalInput);
    
    public final Synapse connectTo(Neuron other, double weight) {
        Synapse synapse = new Synapse(this, other, weight);
        synapse.connect();
        return synapse;
    }
    
    public final boolean addValueChangeListener(ValueChangeListener listener) {
        if (listener == null) {
            return false;
        }
        return this.listeners.add(listener);
    }
    
    public final boolean removeValueChangeListener(ValueChangeListener listener) {
        if (listener == null) {
            return false;
        }
        return this.listeners.remove(listener);
    }
    
    
    // Inner Interfaces
    @FunctionalInterface
    public static interface ValueChangeListener {
        void valueChanged(double oldValue, double newValue);
    }


    // Inner Classes
    public static class Synapse {

        // Fields
        private final Neuron source;
        private final Neuron target;
        private double weight;

        private double unweightedValue;
        private double value;
        
        private final Set<WeightChangeListener> listeners = new LinkedHashSet<>();


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
                this.updateWeightChanged(old, weight);
            }
        }

        public double getValue() {
            return this.value;
        }

        public double getUnweightedValue() {
            return this.unweightedValue;
        }


        // Initialization
        private Synapse(Neuron source, Neuron target, double weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }
        
        
        // Public Methods
        public boolean addWeightChangeListener(WeightChangeListener listener) {
            if (listener == null) {
            return false;
        }
            return this.listeners.add(listener);
        }
        
        public boolean removeWeightChangeListener(WeightChangeListener listener) {
            if (listener == null) {
            return false;
        }
            return this.listeners.remove(listener);
        }


        // Private Methods
        private void connect() {
            this.source.addValueChangeListener((double oldValue, double newValue) -> {
                Synapse.this.unweightedValue = newValue;
                Synapse.this.value = newValue * Synapse.this.getWeight();
            });
            this.source.addAxonConnection(this);
            this.target.addDendriteConnection(this);
        }
        
        private void updateWeightChanged(double oldWeight, double newWeight) {
            this.listeners.stream().forEach((WeightChangeListener listener) -> listener.weightChanged(oldWeight, newWeight));
        }
        
        
        // Inner Interfaces
        @FunctionalInterface
        public static interface WeightChangeListener extends EventListener {
            public void weightChanged(double oldWeight, double newWeight);
        }

    }

}
