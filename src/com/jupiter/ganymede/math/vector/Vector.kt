/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.vector

import com.jupiter.ganymede.math.geometry.Angle
import com.jupiter.ganymede.math.matrix.Matrix
import java.util.*

/**

 * @author NathanT
 */
public open class Vector(vararg values: Double) {

    // Fields
    public val dimension: Int
    private val values: DoubleArray


    // Properties
    public fun getComponent(component: Int): Double {
        if (component <= 0 || component > this.dimension) {
            throw IndexOutOfBoundsException()
        }

        return this.values[component - 1]
    }

    public operator fun get(component: Int): Double {
        return this.getComponent(component)
    }

    public open fun getUnitVector(): Vector {
        return this.times(1.0 / this.norm())
    }


    init {
        this.values = values
        this.dimension = this.values.size
    }


    // Public Methods
    public open operator fun plus(other: Vector?): Vector {
        if (other == null || other.dimension != this.dimension) {
            throw IllegalArgumentException()
        }

        val newValues = DoubleArray(this.dimension)
        for (i in 1..this.dimension) {
            newValues[i - 1] = this.getComponent(i) + other.getComponent(i)
        }
        return Vector(*newValues)
    }

    public open operator fun minus(other: Vector): Vector {
        return this.plus(other.times(-1.0))
    }

    public open operator fun times(scalar: Double): Vector {
        val newValues = DoubleArray(this.dimension)
        for (i in 1..this.dimension) {
            newValues[i - 1] = this.getComponent(i) * scalar
        }
        return Vector(*newValues)
    }

    public open operator fun times(matrix: Matrix): Matrix {
        if (this.dimension != matrix.height) {
            throw IllegalArgumentException("Matrix and Vector dimensions not valid for multiplication.")
        }

        val newValues = Array(1, { DoubleArray(matrix.width) })

        for (i in 1..matrix.width) {
            newValues[0][i - 1] = matrix.column(i).dot(this)
        }

        return Matrix(newValues)
    }

    public open fun dot(other: Vector?): Double {
        if (other == null || other.dimension != this.dimension) {
            throw IllegalArgumentException()
        }

        var product = 0.0
        for (i in 1..this.dimension) {
            product += this.getComponent(i) * other.getComponent(i)
        }
        return product
    }

    public fun norm(): Double {
        return Math.sqrt(this.dot(this))
    }

    public fun angleTo(other: Vector): Angle {
        if (other.dimension != this.dimension) {
            throw IllegalArgumentException("The angle between two vectors is only defined for vectors of the same dimension!")
        }

        return Angle(this.dot(other) / (this.norm() * other.norm()), Angle.TrigFunction.COSINE)
    }

    public fun scalarProjectionOnto(other: Vector): Double {
        return this.dot(other.getUnitVector())
    }

    public open fun vectorProjectionOnto(other: Vector): Vector {
        return other.getUnitVector().times(this.scalarProjectionOnto(other))
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is Vector) {
            if (other.dimension == this.dimension) {
                for (i in 1..this.dimension) {
                    if (java.lang.Double.doubleToLongBits(this.getComponent(i)) != java.lang.Double.doubleToLongBits(other.getComponent(i))) {
                        return false
                    }
                }
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = 7
        hash = 47 * hash + Arrays.hashCode(this.values)
        return hash
    }

    override fun toString(): String {
        val out = StringBuilder()

        out.append("[")
        for (i in 1..this.dimension) {
            out.append(this.getComponent(i))

            if (i < this.dimension) {
                out.append(", ")
            }
        }
        out.append("]")

        return out.toString()
    }

}
