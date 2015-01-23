/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;

/**
 *
 * @author Nathan Templon
 */
public class NeuralNetwork {

    // Fields
    public final int layerCount;
    public final NeuralNetworkInputLayer inputLayer;
    public final NeuralNetworkLayer outputLayer;

    private final NeuralNetworkLayer[] layers;
    private final double defaultWeight;


    // Initialization
    public NeuralNetwork(double defaultWeight, NeuralNetworkInputLayer inputLayer, NeuralNetworkLayer... layers) {
        this.defaultWeight = defaultWeight;
        this.inputLayer = inputLayer;
        this.layers = layers;
        this.layerCount = this.layers.length;

        if (this.layerCount < 1) {
            throw new IllegalArgumentException("A neural network must have at least one layer!");
        }

        this.outputLayer = this.layers[this.layers.length - 1];

        this.connectLayers();
    }


    // Public Methods
    public Vector evaluate(Vector input) {
        this.inputLayer.setValues(input);
        
        for(NeuralNetworkLayer layer : this.layers) {
            layer.fire();
        }
        
        return this.outputLayer.getValueVector();
    }


    // Private Methods
    private void connectLayers() {
        for (int i = 0; i < this.layerCount; i++) {
            NeuralNetworkLayer layer = this.layers[i];

            final int currentIndex = i;

            layer.getNeurons().stream().forEach((Neuron neuron) -> {
                // Connect to input layer
                NeuralNetwork.this.inputLayer.getNeurons().forEach((Neuron input) -> {
                    (new Synapse(input, neuron, NeuralNetwork.this.defaultWeight)).connect();
                });

                for (int j = currentIndex + 1; j < this.layerCount; j++) {
                    // Connect to layer j
                    NeuralNetwork.this.layers[j].getNeurons().stream().forEach((Neuron target) -> {
                        (new Synapse(neuron, target, NeuralNetwork.this.defaultWeight)).connect();
                    });
                }
            });
        }
    }

}
