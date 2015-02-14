/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

import com.jupiter.ganymede.math.vector.Vector;
import com.jupiter.ganymede.neural.FeedForwardNetwork;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public abstract class ManagedTrainer<T extends FeedForwardNetwork> implements NetworkTrainer<T> {
    
    // Fields
    private final Set<TrainingPair> trainingPairs = new HashSet<>();
    private final Set<TrainingPair> trainingPairsAccess = Collections.unmodifiableSet(this.trainingPairs);
    
    
    // Properties
    public final Set<TrainingPair> getTrainingPairs() {
        return this.trainingPairsAccess;
    }
    
    
    // Public Methods
    public final boolean addTrainingPair(TrainingPair pair) {
        return this.trainingPairs.add(pair);
    }
    
    public final boolean removeTrainingPair(TrainingPair pair) {
        return this.trainingPairs.remove(pair);
    }
    
    public final void addTrainingPairs(TrainingPair[] pairs) {
        for (TrainingPair pair : pairs) {
            this.addTrainingPair(pair);
        }
    }
    
    public final void addTrainingPairs(Collection<TrainingPair> pairs) {
        pairs.stream().forEach((TrainingPair pair) -> {
            this.addTrainingPair(pair);
        });
    }
    
    @Override
    public void train(T network, ExitCriteria<T> criteria) {
        int epoch = 0;
        TrainingResults results;
        do {
            epoch++;
            results = this.trainEpoch(network, epoch);
        }
        while (!criteria.shouldExit(results, epoch));
    }
    
    
    // Nested Classes
    public static class TrainingPair {
        
        // Fields
        public final Vector input;
        public final Vector target;
        
        
        // Initialization
        public TrainingPair(Vector input, Vector output) {
            this.input = input;
            this.target = output;
        }
        
    }
    
}
