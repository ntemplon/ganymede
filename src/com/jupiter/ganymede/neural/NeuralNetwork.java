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
import java.util.Random;

/**
 *
 * @author Nathan Templon
 */
public class NeuralNetwork {

    // Constants
    private static final Random random = new Random(System.currentTimeMillis());


    // Static Methods
    private double getRandomWeight(double mean, double standardDeviation) {
        return random.nextGaussian() * standardDeviation + mean;
    }


    // Fields
    private final Neuron[] neurons;
    private final double[][] weights;
    private final int neuronCount;
    private final int layerCount;
    private final int inputCount;
    private final LayerInfo[] layerInfo;

    private final double[] sums;
    private final double[] activatedValues;


    // Properties
    /**
     * Neuron count, including bias neuron
     *
     * @return
     */
    public final int getNeuronCount() {
        return this.neuronCount;
    }

    public final int getLayerCount() {
        return this.layerCount;
    }

    public final int getInputCount() {
        return this.inputCount;
    }

    public final List<LayerInfo> getLayerInfo() {
        return Collections.unmodifiableList(Arrays.asList(this.layerInfo));
    }

    public final double getSum(int neuron) {
        return this.sums[neuron];
    }
    
    public final double getActivatedValue(int neuron) {
        return this.activatedValues[neuron];
    }
    
    public final double getWeight(int targetNeuron, int sourceNeuron) {
        return this.weights[targetNeuron][sourceNeuron];
    }
    
    public final void setWeight(int targetNeuron, int sourceNeuron, double weight) {
        this.weights[targetNeuron][sourceNeuron] = weight;
    }


    // Initialization
    public NeuralNetwork(int inputs, DefaultWeightSettings weightSettings, NeuralNetLayer... layers) {
        this.inputCount = inputs;

        this.layerCount = layers.length;
        this.layerInfo = new LayerInfo[layers.length + 1]; // +1 for input layer

        // Input Layer
        this.layerInfo[0] = new LayerInfo(0, 1, inputs);

        int layerNumber = 1;
        int numNeurons = inputs + 1; // +1 for bias
        for (NeuralNetLayer layer : layers) {
            this.layerInfo[layerNumber] = new LayerInfo(layerNumber, numNeurons, layer.getNeuronCount());
            numNeurons += layer.getNeuronCount();
            layerNumber++;
        }
        this.neuronCount = numNeurons;
        this.neurons = new Neuron[numNeurons];

        // Assign Neurons
        Neuron bias = new Neuron((sum) -> 1, (sum) -> 0);
        this.neurons[0] = bias;
        int neuronNumber = 1;
        for (int i = 1; i <= inputs; i++) {
            this.neurons[i] = new InputNeuron();
            neuronNumber++;
        }
        for (NeuralNetLayer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                this.neurons[neuronNumber] = neuron;
                neuronNumber++;
            }
        }

        this.weights = new double[numNeurons][numNeurons];

        // Set default biases
        for (double[] weight : this.weights) {
            weight[0] = getRandomWeight(weightSettings.mean, weightSettings.standardDeviation);
        }

        // Set default weights
        for (int layerNum = 1; layerNum <= this.getLayerCount(); layerNum++) {
            LayerInfo info = this.layerInfo[layerNum];
            LayerInfo previousInfo = this.layerInfo[layerNum - 1];
            for (int nodeNumber = info.startNeuron; nodeNumber <= info.endNeuron; nodeNumber++) {
                for (int previousNodeNumber = previousInfo.startNeuron; previousNodeNumber <= previousInfo.endNeuron; previousNodeNumber++) {
                    this.weights[nodeNumber][previousNodeNumber] = getRandomWeight(weightSettings.mean, weightSettings.standardDeviation);
                }
            }
        }

        // Create sums and weights
        this.sums = new double[this.getNeuronCount()];
        this.activatedValues = new double[this.getNeuronCount()];
    }


    // Public Methods
    public Vector evaluate(Vector input) {
        if (input.getDimension() != this.getInputCount()) {
            throw new IllegalArgumentException("");
        }

        Arrays.fill(this.sums, 0.0);
        Arrays.fill(this.activatedValues, 0.0);

        // Assign Inputs
        activatedValues[0] = 1.0; // Bias
        for (int inputNum = 1; inputNum <= input.getDimension(); inputNum++) {
            sums[inputNum] = input.getComponent(inputNum);
            activatedValues[inputNum] = this.neurons[inputNum].activation.evaluate(sums[inputNum]);
        }

        // Forward Propagation
        for (int layerNumber = 1; layerNumber <= this.getLayerCount(); layerNumber++) {
            LayerInfo info = this.layerInfo[layerNumber];
            LayerInfo previousInfo = this.layerInfo[layerNumber - 1];
            for (int nodeNumber = info.startNeuron; nodeNumber <= info.endNeuron; nodeNumber++) {
                sums[nodeNumber] += activatedValues[0] * this.weights[nodeNumber][0]; // bias
                for (int previousNodeNumber = previousInfo.startNeuron; previousNodeNumber <= previousInfo.endNeuron; previousNodeNumber++) {
                    sums[nodeNumber] += activatedValues[previousNodeNumber] * this.weights[nodeNumber][previousNodeNumber];
                }
                activatedValues[nodeNumber] = this.neurons[nodeNumber].activation.evaluate(sums[nodeNumber]);
            }
        }

        // Get Outputs
        LayerInfo outInfo = this.layerInfo[this.layerInfo.length - 1];
        double[] result = new double[outInfo.size];
        for (int i = outInfo.startNeuron; i <= outInfo.endNeuron; i++) {
            result[i - outInfo.startNeuron] = activatedValues[i];
        }

        return new Vector(result);
    }


    // Static Classes
    public static class DefaultWeightSettings {

        // Fields
        public final double mean;
        public final double standardDeviation;


        // Initialization
        public DefaultWeightSettings(double mean, double standardDeviation) {
            this.mean = mean;
            this.standardDeviation = standardDeviation;
        }

    }

    public static class LayerInfo {

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
