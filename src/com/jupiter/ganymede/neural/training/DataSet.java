/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Nathan Templon
 */
public class DataSet {
    
    // Fields
    private final Set<TrainingPair> trainingPairs = new LinkedHashSet<>();
    private final Set<TrainingPair> trainingPairAccess = Collections.unmodifiableSet(this.trainingPairs);
    
    
    // Properties
    public final Set<TrainingPair> getTrainingPairs() {
        return this.trainingPairAccess;
    }
    
    
    // Initialization
    public DataSet() {
        
    }
    
    public DataSet(Collection<? extends TrainingPair> pairs) {
        this.addAll(pairs);
    }
    
    public DataSet(TrainingPair[] pairs) {
        this.addAll(pairs);
    }
    
    
    // Public Methods
    public final boolean add(Vector input, Vector output) {
        return this.trainingPairs.add(new TrainingPair(input, output));
    }
    
    public final boolean remove(Vector input, Vector output) {
        return this.trainingPairs.remove(new TrainingPair(input, output));
    }
    
    public final boolean add(TrainingPair pair) {
        return this.trainingPairs.add(pair);
    }
    
    public final boolean remove(TrainingPair pair) {
        return this.trainingPairs.remove(pair);
    }
    
    public final boolean addAll(Collection<? extends TrainingPair> pairs) {
        return this.trainingPairs.addAll(pairs);
    }
    
    public final boolean removeAll(Collection<? extends TrainingPair> pairs) {
        return this.trainingPairs.removeAll(pairs);
    }
    
    public final boolean addAll(TrainingPair[] pairs) {
        return this.trainingPairs.addAll(Arrays.asList(pairs));
    }
    
    public final boolean removeAll(TrainingPair[] pairs) {
        return this.trainingPairs.removeAll(Arrays.asList(pairs));
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof DataSet) {
            return this.getTrainingPairs().equals(((DataSet)other).getTrainingPairs());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.trainingPairs);
        return hash;
    }
    
}
