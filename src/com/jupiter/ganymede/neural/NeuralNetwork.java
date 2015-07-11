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
    int getNeuronCount();
    int getLayerCount();
    int getInputCount();
    List<LayerInfo> getLayerInfo();
    List<Neuron> getNeurons();
    double getSum(int neuron);
    double getActivatedValue(int neuron);
    double getWeight(int targetNeuron, int sourceNeuron);
    void setWeight(int targetNeuron, int sourceNeuron, double weight);
    
    Vector evaluate(Vector input);
    
    
    // Nested Classes
    class LayerInfo {

        // Fields
        public final int layer;
        public final int startNeuron;
        public final int endNeuron;
        public final int size;


        // Initialization
        public LayerInfo(int layer, int startNeuron, int size) {
            this.layer = layer;
            this.startNeuron = startNeuron;
            this.size = size;
            this.endNeuron = this.startNeuron + size - 1;
        }

    }
}
