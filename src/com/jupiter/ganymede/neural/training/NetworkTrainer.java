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
public interface NetworkTrainer<T extends FeedForwardNetwork> {
    void train(T network, ExitCriteria<T> criteria);
    TrainingResults trainEpoch(T network, int epoch);
}
