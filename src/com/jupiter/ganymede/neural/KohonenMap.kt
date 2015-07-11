package com.jupiter.ganymede.neural

import com.jupiter.ganymede.math.vector.Vector
import java.util.*

/**
 * Created by nathan on 7/10/15.
 */
public class KohonenMap(private val distance: (Vector, Vector) -> Double, exemplars: Collection<Vector>) : SelfOrganizingMap<KohonenMap.KohonenExemplar> {

    // Properties
    override val dimension: Int
    override val exemplars: List<KohonenExemplar>


    // Initialization
    init {
        this.dimension = commonDimension(exemplars)
        this.exemplars = exemplars.toList().withIndex().map { vector ->
            KohonenExemplar(vector.value, vector.index)
        }.toList()
    }


    // Public Methods
    override fun categorize(vector: Vector): KohonenExemplar {
        return this.exemplars.map { exemplar ->
            DistancedExemplar(exemplar, this.distance.invoke(exemplar.vector, vector))
        }.reduce { first: DistancedExemplar, second: DistancedExemplar ->
            if (first.distance < second.distance) {
                first
            } else {
                second
            }
        }.exemplar
    }


    // Static Methods
    companion object {
        private fun commonDimension(vectors: Collection<Vector>): Int {
            var dim: Int = 0
            var first = true
            var conflicted = false
            for (vector in vectors) {
                if (!conflicted) {
                    if (first) {
                        dim = vector.dimension
                    } else if (dim != vector.dimension) {
                        conflicted = true
                    }
                }
            }

            return if (!conflicted) {
                dim
            } else {
                throw IllegalArgumentException("All provided exemplars must be of the same dimension.")
            }
        }
    }


    // Inner Classes
    public data class KohonenExemplar(vector: Vector, public val coordinate: Int) : Exemplar {
        // Properties
        override var vector: Vector = vector
            internal set

        // Public Methods
        override fun toString(): String {
            return "CohonenExemplar(vector=" + this.vector.toString() + ", coordinate=" + this.coordinate.toString() + ")"
        }
    }


    public inner class KohonenTrainer(public val learningRate: (Int) -> Double, public val trainingVectors: Collection<Vector>) {
        // Properties
        public var epochsTrained: Int = 0
            private set


        // Initialization
        public constructor(learningRate: Double, trainingVectors: Collection<Vector>) : this({ epoch -> learningRate }, trainingVectors)


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

            val trainingOrder = this.trainingVectors.toArrayList()
            Collections.shuffle(trainingOrder)
            for (vector in trainingOrder) {
                this.trainVector(vector, learnRate)
            }
        }

        private fun trainVector(vector: Vector, learningRate: Double) {
            val closest = this@KohonenMap.categorize(vector)
            for (exemplar in this@KohonenMap.exemplars) {
                if (exemplar === closest) {
                    exemplar.vector = exemplar.vector * (1 - learningRate) + vector * learningRate
                } else {
                    exemplar.vector = exemplar.vector * (1 + learningRate) - vector * learningRate
                }
            }
        }
    }


    private data class DistancedExemplar(public val exemplar: KohonenExemplar, public val distance: Double)
}