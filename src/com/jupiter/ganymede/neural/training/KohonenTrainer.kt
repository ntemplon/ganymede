package com.jupiter.ganymede.neural.training

import com.jupiter.ganymede.math.vector.Vector
import com.jupiter.ganymede.neural.KohonenMap
import java.util.*

/**
 * Created by nathan on 7/11/15.
 */
public class KohonenTrainer(private val network: KohonenMap, public val learningRate: (Int) -> Double, public val trainingVectors: Collection<Vector>) {

    // Properties
    public var epochsTrained: Int = 0
        private set


    // Public Methods
    public fun train(epochs: Int) {
        for (i in 1..epochs) {
            this.trainEpoch()
        }
    }


    // Private Methods
    private fun trainEpoch() {
        this.epochsTrained++
        val learnRate = this.learningRate(this.epochsTrained)

        val trainingOrder = this.trainingVectors.toMutableList()
        Collections.shuffle(trainingOrder)
        for (vector in trainingOrder) {
            this.trainVector(vector, learnRate)
        }
    }

    private fun trainVector(vector: Vector, learningRate: Double) {
        val closest = this.network.categorize(vector)
        for (exemplar in this.network.exemplars) {
            if (exemplar === closest) {
                exemplar.vector = exemplar.vector * (1 - learningRate) + vector * learningRate
            } else {
                exemplar.vector = exemplar.vector * (1 + learningRate) - vector * learningRate
            }
        }
    }
}