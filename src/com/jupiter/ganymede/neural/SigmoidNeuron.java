/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

/**
 *
 * @author Nathan Templon
 */
public class SigmoidNeuron extends Neuron {

    // Initialization
    public SigmoidNeuron() {
        super(
                (double sum) -> {
                    return 1.0 / (1.0 + Math.exp(-1.0 * sum));
                },
                (double sum) -> {
                    double exp = Math.exp(-1.0 * sum);
                    double den = 1.0 + exp;
                    return (-1.0 * exp) / (den * den);
                }
        );
    }

}
