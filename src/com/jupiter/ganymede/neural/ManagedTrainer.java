/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public abstract class ManagedTrainer<T extends FeedForwardNetwork> implements NetworkTrainer<T>
{
    // Fields
    private final double trainingRate;
    private final Set<TrainingPair> trainingPairs = new LinkedHashSet<>();
    private final Set<TrainingPair> trainingPairViewer = Collections.unmodifiableSet(this.trainingPairs);
    
    
    // Properties
    public final double getTrainingRate() {
        return this.trainingRate;
    }
    
    public final Set<TrainingPair> getTrainingPairs() {
        return this.trainingPairs;
    }
    
    
    // Initialization
    public ManagedTrainer(double trainingRate) {
        this.trainingRate = trainingRate;
    }
    
    
    // Public Methods
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
        public final Vector output;
        
        
        // Initialization
        public TrainingPair(Vector input, Vector expectedOutput) {
            this.input = input;
            this.output = expectedOutput;
        }
        
    }
}
