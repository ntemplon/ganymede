/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.List;

/**
 *
 * @author Nathan Templon
 */
public interface NeuralNetwork {
    int getInputCount();
    int getOutputCount();
    int getNeuronCount();
    Neuron getNeuron(int id);
    
    List<? extends NeuralNetworkLayer> getLayers();
    NeuralNetworkInputLayer getInputLayer();
    NeuralNetworkLayer getOutputLayer();
    
    Vector evaluate(Vector input);
}
