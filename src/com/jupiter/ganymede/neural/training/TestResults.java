/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.Arrays;

/**
 *
 * @author Nathan Templon
 */
public class TestResults {
    
    // Constants
    public static final TestResultMetric<Double> RMS = (TrainingPairResult[] results) -> {
        double sum = Arrays.asList(results).stream().mapToDouble((TrainingPairResult pairResult) -> {
            Vector error = pairResult.target.minus(pairResult.output);
            return error.dot(error);
        }).sum();
        
        return Math.sqrt(sum / results.length);
    };
    
    
    // Fields
    private final TrainingPairResult[] results;
    
    
    // Initialization
    public TestResults(TrainingPairResult[] results) {
        this.results = results;
    }
    
    
    // Public Methods
    public <T> T calculateMetric(TestResultMetric<T> metric) {
        return metric.calculate(this.results);
    }

    
    // Static Interfaces
    public static interface TestResultMetric<T> {
        T calculate(TrainingPairResult[] results);
    }
    
    
    // Nested Classes
    public static class TrainingPairResult {
        
        // Fields
        public final Vector input;
        public final Vector target;
        public final Vector output;
        
        
        // Initialization
        public TrainingPairResult(TrainingPair pair, Vector output) {
            this.input = pair.input;
            this.target = pair.target;
            this.output = output;
        }
        
    }
    
}
