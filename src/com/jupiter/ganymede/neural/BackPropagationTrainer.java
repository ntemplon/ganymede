/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import com.jupiter.ganymede.neural.Neuron.Synapse;
import java.util.Random;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public class BackPropagationTrainer<T extends FeedForwardNetwork> extends ManagedTrainer<T> {

    // Initialization
    public BackPropagationTrainer(double trainingRate) {
        super(trainingRate);
    }


    // Public Methods
    @Override
    public boolean train(FeedForwardNetwork network, int maxAttempts) {
        // Initialize all weights to small, random numbers
        Random rand = new Random(System.currentTimeMillis());
        network.getLayers().parallelStream().forEach((NeuralNetworkLayer layer) -> {
            layer.getNeurons().parallelStream().forEach((Neuron neuron) -> {
                neuron.getDendriteConnections().parallelStream().forEach((Synapse synapse) -> {
                    synapse.setWeight(rand.nextDouble() * 1e-2 + 1e-4);
                });
            });
        });

        // Standard Training
        int numAttempts = 0;
        boolean trained = false;
        while (!trained && numAttempts <= maxAttempts) {
            trained = !this.trainOnce(network);
            numAttempts++;
        }
        return trained;
    }


    // Private Methods
    private boolean trainOnce(FeedForwardNetwork network) {
        double[] deltas = new double[network.getNeuronCount()];
        boolean weightsChanged = false;

        // Loop through all training pairs
        for(TrainingPair pair : this.getTrainingPairs()) {
            Vector result = network.evaluate(pair.input);
            Vector errorVector = pair.output.minus(result);

            // Calculate error for output neurons
            int i = 1;
            for (Neuron neuron : network.getOutputLayer().getNeurons()) {
                double error = errorVector.getComponent(i);

                deltas[neuron.id.get()] = error * neuron.getValueDerivative();

                i++;
            }

            // Backpropagate error
            for (int layerNumber = network.getLayers().size() - 2; layerNumber >= -1; layerNumber--) {
                NeuralNetworkLayer layer;
                if (layerNumber >= 0) {
                    layer = network.getLayers().get(layerNumber);
                }
                else {
                    layer = network.getInputLayer();
                }

                for(Neuron neuron : layer.getNeurons()) {
                    double delta = 0;
                    for (Synapse synapse : neuron.getAxonConnections()) {
                        delta += synapse.getWeight() * deltas[synapse.getTarget().id.get()];
                    }
                    deltas[neuron.id.get()] = delta;

                    if (delta != 0.0) {
                        for (Synapse synapse : neuron.getAxonConnections()) {
                            synapse.setWeight(synapse.getWeight() - this.getTrainingRate() * delta * synapse.getTarget().getValue());
                        }
                        weightsChanged = true;
                    }
                }
            }
        }

        return weightsChanged;
    }

}
