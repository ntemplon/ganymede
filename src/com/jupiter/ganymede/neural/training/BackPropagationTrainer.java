/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

import com.jupiter.ganymede.math.vector.Vector;
import com.jupiter.ganymede.neural.FeedForwardNetwork;
import com.jupiter.ganymede.neural.NeuralNetwork.LayerInfo;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public class BackPropagationTrainer<T extends FeedForwardNetwork> extends ManagedTrainer<T> {

    // Fields
    private final TrainingRateFormulation trainingRate;


    // Properties
    public final double getTrainingRate(int epoch) {
        return this.trainingRate.getTrainingRate(epoch);
    }


    // Initialization
    public BackPropagationTrainer(final double trainingRate) {
        this.trainingRate = (int epoch) -> trainingRate;
    }

    public BackPropagationTrainer(TrainingRateFormulation trainingRate) {
        this.trainingRate = trainingRate;
    }


    // Public Methods
    @Override
    public TrainingResults trainEpoch(T network, int epoch) {
        boolean weightsChanged = true;

        this.getTrainingSet().getTrainingPairs().stream().forEach((TrainingPair pair) -> {
            try {
                Vector output = network.evaluate(pair.input);
                Vector error = pair.target.minus(output);

                double[] deltas = new double[network.getNeuronCount()];
                // Assign output deltas
                LayerInfo outputInfo = network.getLayerInfo().get(network.getLayerCount());
                for (int neuronNumber = outputInfo.startNeuron; neuronNumber <= outputInfo.endNeuron; neuronNumber++) {
                    int outputNumber = neuronNumber - outputInfo.startNeuron + 1;
                    deltas[neuronNumber] = error.getComponent(outputNumber) * network.getNeurons().get(neuronNumber).derivative.evaluate(network.getSum(
                            neuronNumber));
                }

                // Propagate Deltas
                for (int layerNumber = network.getLayerCount() - 1; layerNumber >= 0; layerNumber--) {
                    LayerInfo info = network.getLayerInfo().get(layerNumber);
                    LayerInfo backpropLayerInfo = network.getLayerInfo().get(layerNumber + 1);
                    for (int neuronNumber = info.startNeuron; neuronNumber <= info.endNeuron; neuronNumber++) {
                        for (int backpropNeuronNumber = backpropLayerInfo.startNeuron; backpropNeuronNumber <= backpropLayerInfo.endNeuron;
                                backpropNeuronNumber++) {
                            deltas[neuronNumber] += deltas[backpropNeuronNumber] * network.getWeight(backpropNeuronNumber, neuronNumber);
                        }
                        deltas[neuronNumber] *= network.getNeurons().get(neuronNumber).derivative.evaluate(network.getSum(neuronNumber));
                    }
                }

                // Adjust Weights
                for (int layerNumber = 0; layerNumber < network.getLayerCount() - 1; layerNumber++) {
                    LayerInfo info = network.getLayerInfo().get(layerNumber);
                    LayerInfo nextLayerInfo = network.getLayerInfo().get(layerNumber + 1);
                    for (int neuronNumber = info.startNeuron; neuronNumber <= info.endNeuron; neuronNumber++) {
                        for (int nextNeuronNumber = nextLayerInfo.startNeuron; nextNeuronNumber <= nextLayerInfo.endNeuron; nextNeuronNumber++) {
                            double newWeight = network.getWeight(nextNeuronNumber, neuronNumber) - this.getTrainingRate(epoch) * deltas[nextNeuronNumber]
                                    * network.getActivatedValue(neuronNumber);
                            network.setWeight(nextNeuronNumber, neuronNumber, newWeight);
                        }
                    }
                }
                
                // Adjust Biases
                for (int neuronNumber = 1; neuronNumber <= network.getNeuronCount(); neuronNumber++) {
                    double newWeight = network.getWeight(neuronNumber, 0) - this.getTrainingRate(epoch) * deltas[neuronNumber] * network.getActivatedValue(0);
                    network.setWeight(neuronNumber, 0, newWeight);
                }
            }
            catch (Exception ex) {

            }
        });

        return new TrainingResults(weightsChanged);
    }


    // Inner Interfaces
    @FunctionalInterface
    public static interface TrainingRateFormulation {

        double getTrainingRate(int epoch);
    }

}
