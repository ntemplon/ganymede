/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

/**
 *
 * @author Nathan Templon
 */
public class TrainingResults {
    
    // Fields
    public final boolean weightsChanged;
    
    
    // Initialization
    public TrainingResults(boolean weightsChanged) {
        this.weightsChanged = weightsChanged;
    }
    
}
