/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.regression

import com.jupiter.ganymede.math.function.LinearFunction
import java.util.function.Function

/**
 * @author Nathan Templon
 */
public class LinearRegressor : Regressor<Double, Double> {

    public override fun bestFit(points: Set <Regressor.Point<Double, Double>>): LinearFunction {
        val firstMean = points.map { it.first }.average()
        val secondMean = points.map { it.second }.average()

        val firstVariance = points.map { (it.first - firstMean) * (it.first - firstMean) }.sum() / (points.size() - 1)
        val secondVariance = points.map { (it.second - secondMean) * (it.second - secondMean) }.sum() / (points.size() - 1)

        val firstStdDev = Math.sqrt(firstVariance);
        val secondStdDev = Math.sqrt(secondVariance);

        val correlation = points.map { it.first * it.second }.sum() / Math.sqrt(
                points.map{it.first * it.first}.sum() * points.map{it.second * it.second}.sum()
        )

        val slope = correlation * (secondStdDev / firstStdDev);
        val yIntercept = secondMean - slope * firstMean;

        if (slope.isNaN() || yIntercept.isNaN()) {
            System.out.println("NaN in Regressor");
        }

        return LinearFunction(slope, yIntercept);
    }
}
