/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

/**
 *
 * @author Nathan Templon
 */
public class InputNeuron extends Neuron {
    
    // Initialization
    public InputNeuron() {
        super((double sum) -> sum, (double sum) -> 0);
    }
    
}
