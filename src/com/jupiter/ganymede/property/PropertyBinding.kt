/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.property

import com.jupiter.ganymede.property.Property.PropertyChangedArgs

/**

 * @author NathanT
 * *
 * @param
 */
public class PropertyBinding<T>(private val binding: () -> T, vararg dependencies: Property<out Any>) {

    // Fields
    private var property: Property<T>? = null
    private val dependencies: Array<out Property<out Any>>
    private val handle: (PropertyChangedArgs<out Any>) -> Unit =
            {
                this.property?.set(this.binding.invoke())
            }


    init {
        this.dependencies = dependencies
    }


    // Protected Methods
    fun bind(property: Property<T>) {
        this.property = property
        this.property?.set(this.binding.invoke())
        for (dependency in this.dependencies) {
            dependency.addPropertyChangedListener(this.handle)
        }
    }

    fun unbind() {
        this.property = null
        for (dependency in this.dependencies) {
            dependency.removePropertyChangedListener(this.handle)
        }
    }

}
