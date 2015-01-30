/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import com.jupiter.ganymede.neural.Neuron.Synapse;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public class PerceptronTrainer<T extends Perceptron> extends ManagedTrainer<T> {

    // Initialization
    public PerceptronTrainer(double trainingRate) {
        super(trainingRate);
    }


    // Public Methods
    @Override
    public boolean train(T network, int maxAttempts) {
        int numAttempts = 0;
        boolean trained = false;
        while (!trained && numAttempts <= maxAttempts) {
            trained = !this.trainOnce(network);
            numAttempts++;
        }
        return trained;
    }


    // Private Methods
    private boolean trainOnce(Perceptron network) {
        boolean changed = false;

        for (TrainingPair pair : this.getTrainingPairs()) {
            Vector result = network.evaluate(pair.input);
            Vector error = pair.output.minus(result);
            int neuralNumber = 1;
            for (Neuron out : network.getOutputLayer().getNeurons()) {
                double specificError = error.getComponent(neuralNumber);

                for (Synapse dendrite : out.getDendriteConnections()) {
                    if (Math.abs(specificError) > Math.abs(dendrite.getWeight() * 1e-6)) {
                        dendrite.setWeight(dendrite.getWeight() + this.getTrainingRate() * specificError * dendrite.getUnweightedValue());
                        changed = true;
                    }
                }

                neuralNumber++;
            }
        }

        return changed;
    }

}
