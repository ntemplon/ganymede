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
@FunctionalInterface
public interface ExitCriteria<T extends FeedForwardNetwork> {
    boolean shouldExit(TrainingResults results, int epoch);
}
