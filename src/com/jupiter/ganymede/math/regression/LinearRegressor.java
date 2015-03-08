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
 */
public class LinearRegressor implements Regressor<Double, Double> {

    @Override
    public Function bestFit(Set<Point<? extends Double, ? extends Double>> points) {
        final double firstMean = points.stream()
                .mapToDouble((Point<? extends Double, ? extends Double> point) -> point.first)
                .average()
                .getAsDouble();

        final double secondMean = points.stream()
                .mapToDouble((Point<? extends Double, ? extends Double> point) -> point.second)
                .average()
                .getAsDouble();

        final double firstVariance = points.stream()
                .mapToDouble((Point<? extends Double, ? extends Double> point) -> (point.first - firstMean) * (point.first - firstMean))
                .sum() / (points.size() - 1);

        final double secondVariance = points.stream()
                .mapToDouble((Point<? extends Double, ? extends Double> point) -> (point.second - secondMean) * (point.second - secondMean))
                .sum() / (points.size() - 1);

        final double firstStdDev = Math.sqrt(firstVariance);
        final double secondStdDev = Math.sqrt(secondVariance);

        final double correlation = points.stream()
                .mapToDouble((Point<? extends Double, ? extends Double> point) -> point.first * point.second)
                .sum()
                / Math.sqrt(
                        points.stream()
                        .mapToDouble((Point<? extends Double, ? extends Double> point) -> point.first * point.first)
                        .sum()
                        * points.stream()
                        .mapToDouble((Point<? extends Double, ? extends Double> point) -> point.second * point.second)
                        .sum()
                );
        
        final double slope = correlation * (secondStdDev / firstStdDev);
        final double yIntercept = secondMean - slope * firstMean;
        
        return (Function<Double, Double>) (Double value) -> yIntercept + value * slope;
    }

}
