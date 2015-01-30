/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

/**
 *
 * @author nathan
 */
public class Perceptron extends FeedForwardNetwork {
    
    // Initialization
    public Perceptron(double defaultWeight, NeuralNetworkInputLayer input, NeuralNetworkLayer output) {
        super(defaultWeight, input, output);
    }
    
}
