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
public class Neuron {
    
    // Fields
    public final NeuralFunction activation;
    public final NeuralFunction derivative;
    
    
    // Initialization
    public Neuron() {
        this((double sum) -> 1, (double sum) -> 0);
    }
    
    public Neuron(NeuralFunction activation) {
        this(activation, (double sum) -> {
            return (activation.evaluate(sum + 1e-4) - activation.evaluate(sum - 1e-4)) / 0.0002;
        });
    }
    
    public Neuron(NeuralFunction activation, NeuralFunction derivative) {
        this.activation = activation;
        this.derivative = derivative;
    }
    
    
    // Nested Interfaces
    @FunctionalInterface
    public interface NeuralFunction {
        double evaluate(double sum);
    }
    
}
