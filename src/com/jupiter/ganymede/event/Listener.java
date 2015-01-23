/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.event;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
@FunctionalInterface
public interface Listener<T> {
    
    void handle(T value);
    
}
