/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.neural;

import com.jupiter.ganymede.math.vector.Vector;
import com.jupiter.ganymede.neural.Neuron.Synapse;
import java.util.Random;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Nathan Templon
 * @param <T>
 */
public class BackPropagationTrainer<T extends FeedForwardNetwork> extends ManagedTrainer<T> {

    // Initialization
    public BackPropagationTrainer(double trainingRate) {
        super(trainingRate);
    }
    
    
    // Public Methods
    @Override
    public boolean train(FeedForwardNetwork network, int maxAttempts) {
        // Initialize all weights to small, random numbers
        Random rand = new Random(System.currentTimeMillis());
        network.getLayers().parallelStream().forEach((NeuralNetworkLayer layer) -> {
            layer.getNeurons().parallelStream().forEach((Neuron neuron) -> {
                neuron.getDendriteConnections().parallelStream().forEach((Synapse synapse) -> {
                    synapse.setWeight(rand.nextDouble() * 1e-2 + 1e-4);
                });
            });
        });
        
        
        // Standard Training
        int numAttempts = 0;
        boolean trained = false;
        while (!trained && numAttempts <= maxAttempts) {
            trained = !this.trainOnce(network);
            numAttempts++;
        }
        return trained;
    }
    
    
    // Private Methods
    private boolean trainOnce(FeedForwardNetwork network) {
        // Loop through all training pairs
        this.getTrainingPairs().stream().forEach((TrainingPair pair) -> {
            Vector result = network.evaluate(pair.input);
            Vector errorVector = pair.output.minus(result);
            
            // Loop through each component of the error and back propagate it.
            int i = 1;
            for (Neuron neuron : network.getOutputLayer().getNeurons()) {
                double error = errorVector.getComponent(i);
                
                
                i++;
                throw new NotImplementedException();
            }
        });
        
        return true;
    }

}
