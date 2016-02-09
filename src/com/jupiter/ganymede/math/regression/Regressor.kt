/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.regression

import java.util.function.Function

/**

 * @author Nathan Templon
 * *
 * @param
 * *
 * @param
 */
@FunctionalInterface
public interface Regressor<T : Number, F : Number> {

    public fun bestFit(points: Set<Point<T, F>>): Function<T, F>


    // Nested Classes
    data public class Point<G : Number, H : Number>(public val first: G, public val second: H)

}
