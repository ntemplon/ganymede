/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

import com.jupiter.ganymede.math.vector.Vector;
import java.util.Objects;

/**
 *
 * @author Nathan Templon
 */
public class TrainingPair {

    // Fields
    public final Vector input;
    public final Vector target;


    // Initialization
    public TrainingPair(Vector input, Vector output) {
        this.input = input;
        this.target = output;
    }


    // Public Methods
    @Override
    public boolean equals(Object other) {
        if (other instanceof TrainingPair) {
            TrainingPair tp = (TrainingPair) other;
            return this.input.equals(tp.input) && this.target.equals(tp.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.input);
        hash = 17 * hash + Objects.hashCode(this.target);
        return hash;
    }

}
