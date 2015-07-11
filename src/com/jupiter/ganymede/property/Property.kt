/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.property

import com.jupiter.ganymede.event.Event
import com.jupiter.ganymede.event.Listener

/**

 * @author NathanT
 * *
 * @param
 */
public class Property<T>
/**
 * Initializes a property with the given initial value.

 * @param initialValue
 */
@jvmOverloads constructor(initialValue: T? = null) {

    // Events
    private val changed = Event<PropertyChangedArgs<T>>()


    // Fields
    private var value: T? = null
    private var binding: PropertyBinding<T>? = null

    init {
        this.value = initialValue
    }


    public fun get(): T? = this.value

    /**
     * Sets the value of the Property.

     * @param value
     */
    public fun set(value: T?) {
        val oldValue: T? = this.value
        this.value = value

        if ((oldValue != null && oldValue != this.value) || (oldValue == null && this.value != null))
        {
            this.changed.dispatch(PropertyChangedArgs(this, oldValue, this.value))
        }
    }

    public fun addPropertyChangedListener(listener: Listener<PropertyChangedArgs<T>>): Boolean {
        return this.changed.addListener(listener)
    }

    public fun removePropertyChangedListener(listener: Listener<PropertyChangedArgs<T>>): Boolean {
        return this.changed.removeListener(listener)
    }

    /**
     * Binds this property to another so that it will reflect the other Property's value.

     * @param other
     */
    public fun bind(other: Property<T>) {
        this.bind(PropertyBinding({ -> other.get()}))
    }

    /**
     * Binds this property to an arbitrary expression.

     * @param binding
     */
    public fun bind(binding: PropertyBinding<T>) {
        this.unbind()
        this.binding = binding
        this.binding?.bind(this)
    }

    /**
     * Removes the current binding from the property.
     */
    public fun unbind() {
        if (this.binding != null) {
            this.binding?.unbind()
            this.binding = null
        }
    }


    // Inner Classes
    data public class PropertyChangedArgs<T>(public val property: Property<T>, public val oldValue: T?, public val newValue: T?)
}