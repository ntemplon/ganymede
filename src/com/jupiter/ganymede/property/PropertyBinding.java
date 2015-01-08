/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.property;

/**
 *
 * @author NathanT
 * @param <T>
 */
public class PropertyBinding<T> implements PropertyChangeListener {
    
    // Fields
    private Property property;
    private final Property[] dependencies;
    private final EvaluatableBinding<T> binding;
    
    
    // Initialization
    public PropertyBinding(EvaluatableBinding<T> binding, Property... dependencies) {
        this.binding = binding;
        this.dependencies = dependencies;
    }
    
    
    // Public Methods
    @Override
    public final void changed(Property property, Object oldValue, Object newValue) {
        this.property.set(this.binding.evaluate());
    }
    
    
    // Protected Methods
    protected final void bind(Property<T> property) {
        this.property.set(this.binding.evaluate());
        for (Property dependency : this.dependencies) {
            dependency.addPropertyChangeListener(this);
        }
    }
    
    protected final void unbind() {
        for (Property dependency : this.dependencies) {
            dependency.removePropertyChangeListener(this);
        }
    }
    
    
    // Lambda Interface
    public static interface EvaluatableBinding<T> {
        T evaluate();
    }
    
}
