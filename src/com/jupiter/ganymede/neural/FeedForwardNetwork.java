/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nathan Templon
 */
public class FeedForwardNetwork implements NeuralNetwork {

    // Fields
    private final int layerCount;
    private final NeuralNetworkInputLayer inputLayer;
    private final NeuralNetworkLayer outputLayer;
    private final int inputCount;
    private final int outputCount;

    private final NeuralNetworkLayer[] layers;
    private final List<NeuralNetworkLayer> layersAccess;
    private final double defaultWeight;
    
    
    // Properties
    @Override
    public int getInputCount() {
        return this.inputCount;
    }
    
    @Override
    public int getOutputCount() {
        return this.outputCount;
    }
    
    @Override
    public List<NeuralNetworkLayer> getLayers() {
        return this.layersAccess;
    }
    
    @Override
    public NeuralNetworkInputLayer getInputLayer() {
        return this.inputLayer;
    }
    
    @Override
    public NeuralNetworkLayer getOutputLayer() {
        return this.outputLayer;
    }


    // Initialization
    public FeedForwardNetwork(double defaultWeight, NeuralNetworkInputLayer inputLayer, NeuralNetworkLayer... layers) {
        this.defaultWeight = defaultWeight;
        this.inputLayer = inputLayer;
        this.layers = layers;
        this.layerCount = this.layers.length;

        if (this.layerCount < 1) {
            throw new IllegalArgumentException("A neural network must have at least one layer!");
        }

        this.outputLayer = this.layers[this.layers.length - 1];
        
        this.inputCount = this.inputLayer.getNeurons().size();
        this.outputCount = this.outputLayer.getNeurons().size();
        
        this.layersAccess = Collections.unmodifiableList(Arrays.asList(layers));

        this.connectLayers();
    }


    // Public Methods
    @Override
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
                FeedForwardNetwork.this.inputLayer.getNeurons().forEach((Neuron input) -> {
                    input.connectTo(neuron, FeedForwardNetwork.this.defaultWeight);
                });

                for (int j = currentIndex + 1; j < this.layerCount; j++) {
                    // Connect to layer j
                    FeedForwardNetwork.this.layers[j].getNeurons().stream().forEach((Neuron target) -> {
                        neuron.connectTo(target, FeedForwardNetwork.this.defaultWeight);
                    });
                }
            });
        }
    }

}
