/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.event;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public class Event<T> {
    
    // Fields
    private final Set<Listener<T>> listeners = new LinkedHashSet<>();
    
    
    // Initialization
    public Event() {
        
    }
    
    
    // Public Methods
    public boolean addListener(Listener<T> listener) {
        return this.listeners.add(listener);
    }
    
    public boolean removeListener(Listener<T> listener) {
        return this.listeners.remove(listener);
    }
    
    public void dispatch(T event) {
        this.listeners.stream().forEach(
                (Listener<T> listener) -> listener.handle(event)
        );
    }
    
}
