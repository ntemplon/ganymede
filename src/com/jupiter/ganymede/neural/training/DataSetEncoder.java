/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural.training;

import com.jupiter.ganymede.neural.NeuralEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 * @author Nathan Templon
 * @param <TIn>
 * @param <TOut>
 */
public class DataSetEncoder<TIn, TOut> {
    
    // Fields
    private final NeuralEncoder<? super TIn> inputEncode;
    private final NeuralEncoder<? super TOut> outputEncode;
    private final DataSet data = new DataSet();
    
    
    // Initialization
    public DataSetEncoder(NeuralEncoder<? super TIn> inputEncode, NeuralEncoder<? super TOut> inputDecode) {
        this.inputEncode = inputEncode;
        this.outputEncode = inputDecode;
    }
    
    
    // Public Methods
    public final boolean add(TIn input, TOut output) {
        return this.data.add(new TrainingPair(this.inputEncode.encode(input), this.outputEncode.encode(output)));
    }
    
    public final boolean remove(TIn input, TOut output) {
        return this.data.remove(new TrainingPair(this.inputEncode.encode(input), this.outputEncode.encode(output)));
    }
    
    public final boolean add(EncodedTrainingPair<? extends TIn, ? extends TOut> pair) {
        return this.data.add(new TrainingPair(this.inputEncode.encode(pair.input), this.outputEncode.encode(pair.output)));
    }
    
    public final boolean remove(EncodedTrainingPair<? extends TIn, ? extends TOut> pair) {
        return this.data.remove(new TrainingPair(this.inputEncode.encode(pair.input), this.outputEncode.encode(pair.output)));
    }
    
    public final boolean addAll(Collection<? extends EncodedTrainingPair<? extends TIn, ? extends TOut>> pairs) {
        return this.data.addAll(pairs.stream().map((EncodedTrainingPair<? extends TIn, ? extends TOut> pair) ->
            new TrainingPair(this.inputEncode.encode(pair.input), this.outputEncode.encode(pair.output))
        ).collect(Collectors.toSet()));
    }
    
    public final boolean removeAll(Collection<? extends EncodedTrainingPair<? extends TIn, ? extends TOut>> pairs) {
        return this.data.removeAll(pairs.stream().map((EncodedTrainingPair<? extends TIn, ? extends TOut> pair) ->
            new TrainingPair(this.inputEncode.encode(pair.input), this.outputEncode.encode(pair.output))
        ).collect(Collectors.toSet()));
    }
    
    public final boolean addAll(EncodedTrainingPair<? extends TIn, ? extends TOut>[] pairs) {
        return this.data.addAll(Arrays.asList(pairs).stream().map((EncodedTrainingPair<? extends TIn, ? extends TOut> pair) ->
            new TrainingPair(this.inputEncode.encode(pair.input), this.outputEncode.encode(pair.output))
        ).collect(Collectors.toSet()));
    }
    
    public final boolean removeAll(EncodedTrainingPair<? extends TIn, ? extends TOut>[] pairs) {
        return this.data.removeAll(Arrays.asList(pairs).stream().map((EncodedTrainingPair<? extends TIn, ? extends TOut> pair) ->
            new TrainingPair(this.inputEncode.encode(pair.input), this.outputEncode.encode(pair.output))
        ).collect(Collectors.toSet()));
    }
    
    public final DataSet getEncodedData() {
        return new DataSet(this.data.getTrainingPairs());
    }
    
    
    // Nested Classes
    public static class EncodedTrainingPair<TIn, TOut> {
        
        // Fields
        public final TIn input;
        public final TOut output;
        
        
        // Initialization
        public EncodedTrainingPair(TIn input, TOut output) {
            this.input = input;
            this.output = output;
        }
        
    }
    
}
