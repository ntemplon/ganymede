/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.event

import java.util.LinkedHashSet

/**

 * @author Nathan Templon
 * *
 * @param
 */
public class Event<T> {

    // Fields
    private val listeners = LinkedHashSet<Listener<T>>()


    // Public Methods
    public fun addListener(listener: Listener<T>): Boolean {
        return this.listeners.add(listener)
    }

    public fun removeListener(listener: Listener<T>): Boolean {
        return this.listeners.remove(listener)
    }

    public fun dispatch(event: T) {
        this.listeners.iterator().asSequence().forEach {
            listener -> listener.handle(event)
        }
    }

}// Initialization
