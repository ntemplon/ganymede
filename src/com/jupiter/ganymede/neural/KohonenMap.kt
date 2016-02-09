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
            DistancedExemplar(exemplar, this.distance(exemplar.vector, vector))
        }.reduce { first, second ->
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
            var dim = 0
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
    public class KohonenExemplar(override var vector: Vector, public val coordinate: Int) : Exemplar {
        // Public Methods
        override fun toString(): String {
            return "KohonenExemplar(vector=" + this.vector.toString() + ", coordinate=" + this.coordinate.toString() + ")"
        }
    }


    private data class DistancedExemplar(public val exemplar: KohonenExemplar, public val distance: Double)
}