/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public class PerceptronTrainer<T extends Perceptron> implements NetworkTrainer<T> {
    
    // Fields
    private final double trainingRate;
    private final Set<TrainingPair> trainingPairs = new LinkedHashSet<>();
    
    
    // Initialization
    public PerceptronTrainer(double trainingRate) {
        this.trainingRate = trainingRate;
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
    
    public boolean addTrainingPair(TrainingPair pair) {
        return this.trainingPairs.add(pair);
    }
    
    public boolean removeTrainingPair(TrainingPair pair) {
        return this.trainingPairs.remove(pair);
    }
    
    
    // Private Methods
    private boolean trainOnce(Perceptron network) {
        boolean changed = false;
        
        for(TrainingPair pair : this.trainingPairs) {
            Vector output = network.evaluate(pair.input);
            Vector error = pair.expectedOutput.minus(output);
            int neuralNumber = 1;
            for(Neuron out : network.outputLayer.getNeurons()) {
                double specificError = error.getComponent(neuralNumber);
                
                for (Synapse dendrite : out.getDendriteConnections()) {
                    if (Math.abs(specificError) > Math.abs(dendrite.getWeight() * 1e-6)) {
                        dendrite.setWeight(dendrite.getWeight() + this.trainingRate * specificError * dendrite.getUnweightedValue());
                        changed = true;
                    }
                }
                
                neuralNumber++;
            }
        }
        
        return changed;
    }
    
    
    // Inner Classes
    public static class TrainingPair {
        
        // Fields
        public final Vector input;
        public final Vector expectedOutput;
        
        
        // Initialization
        public TrainingPair(Vector input, Vector expectedOutput) {
            this.input = input;
            this.expectedOutput = expectedOutput;
        }
        
    }
    
}
