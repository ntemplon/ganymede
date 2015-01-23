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
 */
public class PerceptronTrainer implements NetworkTrainer {
    
    // Fields
    private final double trainingRate;
    private final Set<TrainingPair> trainingPairs = new LinkedHashSet<>();
    
    
    // Initialization
    public PerceptronTrainer(double trainingRate) {
        this.trainingRate = trainingRate;
    }
    
    
    // Public Methods
    @Override
    public boolean train(NeuralNetwork network) {
        // Do voodoo here
        return false;
    }
    
    public boolean addTrainingPair(TrainingPair pair) {
        return this.trainingPairs.add(pair);
    }
    
    public boolean removeTrainingPair(TrainingPair pair) {
        return this.trainingPairs.remove(pair);
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
