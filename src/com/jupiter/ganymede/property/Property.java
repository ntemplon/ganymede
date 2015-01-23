/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.property;

import com.jupiter.ganymede.event.Event;

/**
 *
 * @author NathanT
 * @param <T>
 */
public class Property<T> {

    // Events
    public final Event<PropertyChangedArgs<T>> changed = new Event<>();
    
    
    // Fields
    private T value;
    private PropertyBinding binding;


    // Initialization
    /**
     * Initializes a Property with initial value null.
     */
    public Property() {
        this(null);
    }

    /**
     * Initializes a property with the given initial value.
     *
     * @param initialValue
     */
    public Property(T initialValue) {
        this.value = initialValue;
    }


    // Public Methods
    /**
     * Fetches the current value of the Property.
     *
     * @return
     */
    public T get() {
        return this.value;
    }

    /**
     * Sets the value of the Property.
     *
     * @param value
     */
    public void set(T value) {
        T oldValue = this.value;
        this.value = value;

        if (!oldValue.equals(this.value)) {
            this.changed.dispatch(new PropertyChangedArgs<>(this, oldValue, this.value));
        }
    }

    /**
     * Binds this property to another so that it will reflect the other Property's value.
     *
     * @param other
     */
    public void bind(Property<T> other) {
        this.bind(new PropertyBinding(() -> other.get(), other));
    }

    /**
     * Binds this property to an arbitrary expression.
     *
     * @param binding
     * @param dependencies The other properties that the binding is dependent on. The binding will be recalculated
     * whenever one of these properties is changed.
     *
     */
    public void bind(PropertyBinding<T> binding) {
        this.unbind();
        this.binding = binding;
        this.binding.bind(this);
    }

    /**
     * Removes the current binding from the property.
     */
    public void unbind() {
        if (this.binding != null) {
            this.binding.unbind();
            this.binding = null;
        }
    }
    
    
    // Inner Classes
    public static class PropertyChangedArgs<T> {
        
        // Fields
        public final Property<T> property;
        public final T oldValue;
        public final T newValue;
        
        
        // Initialization
        public PropertyChangedArgs(Property<T> property, T oldValue, T newValue) {
            this.property = property;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
        
    }

}
