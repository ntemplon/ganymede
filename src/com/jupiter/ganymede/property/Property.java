/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.property;

import com.jupiter.ganymede.property.PropertyBinding.EvaluatableBinding;
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

        this.listeners.stream().forEach((PropertyChangeListener<T> listener) -> {
            listener.changed(this, oldValue, this.value);
        });
    }

    /**
     * Adds a property change listener, which will fire a chanced event whenever the set method is called (even if the
     * value is not changed).
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener<T> listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes a property change listener from the list of listeners, if present.
     *
     * @param listener
     * @return Returns true if the listener was successfully removed, false if it was not.
     */
    public boolean removePropertyChangeListener(PropertyChangeListener<T> listener) {
        return this.listeners.remove(listener);
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

}
