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
public class HyperbolicTangentNeuron extends Neuron {

    // Initialization
    public HyperbolicTangentNeuron() {
        super(
                (double sum) -> {
                    return Math.tanh(sum);
                },
                (double sum) -> {
                    double cosh = Math.cosh(sum);
                    return 1.0 / (cosh * cosh);
                }
        );
    }

}
