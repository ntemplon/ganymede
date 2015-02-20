/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

import com.jupiter.ganymede.neural.FeedForwardNetwork;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public abstract class ManagedTrainer<T extends FeedForwardNetwork> implements NetworkTrainer<T> {
    
    // Fields
    private DataSet trainingSet;
    
    
    // Properties
    public final DataSet getTrainingSet() {
        return this.trainingSet;
    }
    
    public final void setTrainingSet(DataSet trainingSet) {
        this.trainingSet = trainingSet;
    }
    
    
    // Initialization
    public ManagedTrainer() {
        this.trainingSet = new DataSet();
    }
    
    
    // Public Methods
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
    
}
