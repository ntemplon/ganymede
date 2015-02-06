/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.property;

import com.jupiter.ganymede.event.Listener;
import com.jupiter.ganymede.property.Property.PropertyChangedArgs;

/**
 *
 * @author NathanT
 * @param <T>
 */
public class PropertyBinding<T> implements Listener<PropertyChangedArgs<T>> {
    
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
    public final void handle(PropertyChangedArgs<T> args) {
        this.property.set(this.binding.evaluate());
    }
    
    
    // Protected Methods
    protected final void bind(Property<T> property) {
        this.property.set(this.binding.evaluate());
        for (Property dependency : this.dependencies) {
            dependency.addPropertyChangedListener(this);
        }
    }
    
    protected final void unbind() {
        for (Property dependency : this.dependencies) {
            dependency.removePropertyChangedListener(this);
        }
    }
    
    
    // Lambda Interface
    public static interface EvaluatableBinding<T> {
        T evaluate();
    }
    
}
