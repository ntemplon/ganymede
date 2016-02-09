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
class Property<T>(initialValue: T) {

    // Events
    private val changed = Event<PropertyChangedArgs<T>>()


    // Fields
    private var value: T = initialValue
    private var binding: PropertyBinding<T>? = null


    fun get(): T = this.value

    /**
     * Sets the value of the Property.

     * @param value
     */
    fun set(value: T) {
        val oldValue: T = this.value
        this.value = value

        if ((oldValue != null && oldValue != this.value) || (oldValue == null && this.value != null)) {
            this.changed.dispatch(PropertyChangedArgs(
                    property = this,
                    newValue = value,
                    oldValue = oldValue))
        }
    }

    fun addPropertyChangedListener(listener: (PropertyChangedArgs<T>) -> Unit): Boolean {
        return this.changed.addListener(listener)
    }

    fun removePropertyChangedListener(listener: (PropertyChangedArgs<T>) -> Unit): Boolean {
        return this.changed.removeListener(listener)
    }

    /**
     * Binds this property to another so that it will reflect the other Property's value.

     * @param other
     */
    fun bind(other: Property<T>) {
        this.bind(PropertyBinding({ other.get() }))
    }

    /**
     * Binds this property to an arbitrary expression.

     * @param binding
     */
    fun bind(binding: PropertyBinding<T>) {
        this.unbind()
        this.binding = binding
        this.binding?.bind(this)
    }

    /**
     * Removes the current binding from the property.
     */
    fun unbind() {
        if (this.binding != null) {
            this.binding?.unbind()
            this.binding = null
        }
    }


    // Inner Classes
    data class PropertyChangedArgs<T>(val property: Property<T>, val oldValue: T, val newValue: T)

    interface PropertyListener<T> : Listener<PropertyChangedArgs<T>>
}