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
    private val funcListeners = LinkedHashSet<(T) -> Unit>()


    // Public Methods
    public fun addListener(listener: (T) -> Unit): Boolean = this.funcListeners.add(listener)
    public fun addListener(listener: Listener<T>): Boolean = this.listeners.add(listener)
    public fun removeListener(listener: (T) -> Unit): Boolean = this.funcListeners.remove(listener)
    public fun removeListener(listener: Listener<T>): Boolean = this.listeners.remove(listener)

    public fun dispatch(event: T) {
        this.funcListeners.forEach {listener ->
            listener(event)
        }
        this.listeners.forEach { listener ->
            listener.handle(event)
        }
    }

}

public class EventWrapper<T>(private val event: Event<T>) {

    public fun addListener(listener: (T) -> Unit): Boolean = this.event.addListener(listener)
    public fun addListener(listener: Listener<T>): Boolean = this.event.addListener(listener)
    public fun removeListener(listener: (T) -> Unit): Boolean = this.event.removeListener(listener)
    public fun removeListener(listener: Listener<T>): Boolean = this.event.removeListener(listener)

}
