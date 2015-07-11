package com.jupiter.ganymede.neural

import com.jupiter.ganymede.math.vector.Vector

/**
 * Created by nathan on 7/10/15.
 */
public class CohonenMap(private val distance: (Vector, Vector) -> Double, exemplars: Collection<Vector>) : SelfOrganizingMap<CohonenMap.CohonenExemplar> {

    // Properties
    override val dimension: Int
    override val exemplars: List<CohonenExemplar>


    // Initialization
    init {
        this.dimension = commonDimension(exemplars)
        this.exemplars = exemplars.toList().withIndex().map { vector ->
            CohonenExemplar(vector.value, vector.index)
        }.toList()
    }


    // Public Methods
    override fun categorize(vector: Vector): CohonenExemplar {
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
    public inner class CohonenExemplar(vector: Vector, public val coordinate: Int) : Exemplar {
        // Properties
        override var vector: Vector = vector
            private set
    }

    private class DistancedExemplar(public val exemplar: CohonenExemplar, public val distance: Double)
}