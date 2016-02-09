/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.event

import java.util.EventListener

/**

 * @author Nathan Templon
 * *
 * @param
 */
@FunctionalInterface
public interface Listener<T> : EventListener {

    public fun handle(value: T)

}
