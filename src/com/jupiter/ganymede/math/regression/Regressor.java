/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.regression;

import java.util.Set;
import java.util.function.Function;

/**
 *
 * @author Nathan Templon
 * @param <T>
 * @param <F>
 */
@FunctionalInterface
public interface Regressor<T extends Number, F extends Number> {
    
    Function<T, F> bestFit(Set<Point<? extends T, ? extends F>> points);
    
    
    // Nested Classes
    public final static class Point<G extends Number, H extends Number> {
        
        // Fields
        public final G first;
        public final H second;
        
        
        // Intialization
        public Point(G first, H second) {
            this.first = first;
            this.second = second;
        }
        
    }
    
}
