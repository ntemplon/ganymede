/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.event.Event;
import com.jupiter.ganymede.event.Listener;
import com.jupiter.ganymede.property.Property;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public abstract class Neuron {
    
    // Fields
    public final Property<Integer> id = new Property<>();
    
    private final Event<ValueChangedArgs> valueChanged = new Event<>();
    
    private double sum;
    private double value;
    private double derivative;
    private final Set<Synapse> dendrites = new HashSet<>();
    private final Set<Synapse> dendriteViewer = Collections.unmodifiableSet(this.dendrites);
    private final Set<Synapse> axonConnections = new HashSet<>();
    private final Set<Synapse> axonViewer = Collections.unmodifiableSet(this.axonConnections);
    

    // Properties
    public double getValue() {
        return this.value;
    }
    
    protected void setValue(double value) {
        double old = this.value;
        this.value = value;
        
        this.valueChanged.dispatch(new ValueChangedArgs(this, old, this.value));
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
    
    public boolean addValueChangedListener(Listener<ValueChangedArgs> listener) {
        return this.valueChanged.addListener(listener);
    }
    
    public boolean removeValueChangedListener(Listener<ValueChangedArgs> listener) {
        return this.valueChanged.removeListener(listener);
    }


    // Inner Classes
    public static class ValueChangedArgs {
        
        // Fields
        public final double oldValue;
        public final double newValue;
        public final Neuron sender;
        
        
        // Initialization
        public ValueChangedArgs(Neuron sender, double oldValue, double newValue) {
            this.sender = sender;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
        
    }
    
    public static class Synapse {

        // Fields
        public final Event<WeightChangedArgs> weightChanged = new Event<>();
        
        private final Neuron source;
        private final Neuron target;
        private double weight;

        private double unweightedValue;
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

        
        // Private Methods
        private void connect() {
            this.source.addValueChangedListener((ValueChangedArgs args) -> {
                Synapse.this.unweightedValue = args.newValue;
                Synapse.this.value = args.newValue * Synapse.this.getWeight();
            });
            this.source.addAxonConnection(this);
            this.target.addDendriteConnection(this);
        }
        
        private void updateWeightChanged(double oldWeight, double newWeight) {
            this.weightChanged.dispatch(new WeightChangedArgs(this, oldWeight, newWeight));
        }
        
        
        // Nested Classes
        public static class WeightChangedArgs {
            
            // Fields
            public final Synapse sender;
            public final double oldWeight;
            public final double newWeight;
            
            
            // Initialization
            public WeightChangedArgs(Synapse sender, double oldWeight, double newWeight) {
                this.sender = sender;
                this.oldWeight = oldWeight;
                this.newWeight = newWeight;
            }
            
        }

    }

}
