/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ganymede.property;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author NathanT
 * @param <T>
 */
public class Property<T> {
    
    // Fields
    private T value;
    private final Set<PropertyChangeListener<T>> listeners = new LinkedHashSet<>();
    
    
    // Initialization
    public Property() {
        this.value = null;
    }
    
    public Property(T initialValue) {
        this.value = initialValue;
    }
    
    
    // Public Methods
    public T get() {
        return this.value;
    }
    
    public void set(T value) {
        T oldValue = this.value;
        this.value = value;
        
        this.listeners.stream().forEach((PropertyChangeListener<T> listener) -> {
            listener.changed(oldValue, this.value);
        });
    }
    
    public void addPropertyChangeListener(PropertyChangeListener<T> listener) {
        this.listeners.add(listener);
    }
    
    public boolean removePropertyChangeListener(PropertyChangeListener<T> listener) {
        return this.listeners.remove(listener);
    }
    
}
