/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

/**
 *
 * @author Nathan Templon
 * @param <TIn>
 * @param <TOut>
 */
public class NeuralInterface<TIn, TOut> {
    
    // Fields
    private final NeuralEncoder<TIn> encoder;
    private final NeuralDecoder<TOut> decoder;
    private final NeuralNetwork network;
    
    
    // Initialization
    public NeuralInterface(NeuralEncoder<TIn> encoder, NeuralDecoder<TOut> decoder, NeuralNetwork network) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.network = network;
    }
    
    
    // Public Methods
    public TOut evaluate(TIn input) {
        return this.decoder.decode(this.network.evaluate(this.encoder.encode(input)));
    }
    
}
