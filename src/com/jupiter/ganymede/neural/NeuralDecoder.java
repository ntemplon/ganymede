/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;

/**
 *
 * @author Nathan Templon
 */
public interface NeuralDecoder<T> {
    T decode(Vector output);
}
