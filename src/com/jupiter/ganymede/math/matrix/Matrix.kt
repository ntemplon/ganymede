/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.matrix

import com.jupiter.ganymede.math.geometry.Angle
import com.jupiter.ganymede.math.vector.Vector
import com.jupiter.ganymede.property.Property
import java.awt.Dimension
import java.util.Arrays

/**

 * @author NathanT
 */
public class Matrix(private val values: Array<DoubleArray>) {


    // Fields
    public val height: Int
    public val width: Int
    public val dimension: Dimension

    private val norm = Property<Double>()
    private var hasCalculatedInverseRref: Boolean = false
    private var inverse: Matrix? = null
    private var rrefMatrix: Matrix? = null


    // Properties
    public fun getComponent(i: Int, j: Int): Double {
        if (i <= 0 || i > this.height || j <= 0 || j > this.width) {
            throw IndexOutOfBoundsException()
        }
        return this.values[i - 1][j - 1]
    }

    public fun isSquare(): Boolean {
        return this.width == this.height
    }

    public fun isSingular(): Boolean {
        if (!this.isSquare()) {
            return false
        }
        if (!this.hasCalculatedInverseRref) {
            this.computeRrefAndInverse()
        }
        return this.inverse != null
    }


    init {
        if (!Matrix.isLegal(values)) {
            throw IllegalArgumentException()
        }
        this.height = values.size()
        this.width = values[0].size()
        this.dimension = Dimension(this.width, this.height)

        this.norm.set(null)
    }


    // Public Methods
    public fun times(scalar: Double): Matrix {
        val newValues = Array(this.height, { DoubleArray(this.width) })

        for (i in 1..this.height) {
            for (j in 1..this.width) {
                newValues[i - 1][j - 1] = this.getComponent(i, j) * scalar
            }
        }

        return Matrix(newValues)
    }

    public fun times(other: Matrix): Matrix {
        if (this.width != other.height) {
            throw IllegalArgumentException("Matrix dimensions not valid for multiplication.")
        }

        val newValues = Array(this.height, { DoubleArray(other.width) })

        for (i in 1..this.height) {
            for (j in 1..other.width) {
                newValues[i - 1][j - 1] = this.row(i).dot(other.column(j))
            }
        }

        return Matrix(newValues)
    }

    public fun times(vector: Vector): Vector {
        if (vector.dimension !== this.width) {
            throw IllegalArgumentException("Matrix and Vector dimensions not valid for multiplication.")
        }

        val newValues = DoubleArray(this.height)

        for (i in 1..this.height) {
            newValues[i - 1] = this.row(i).dot(vector)
        }

        return Vector(*newValues)
    }

    public fun plus(other: Matrix): Matrix {
        val newValues = Array(this.height, { DoubleArray(this.width) })

        for (i in 1..this.height) {
            for (j in 1..this.width) {
                newValues[i - 1][j - 1] = this.getComponent(i, j) + other.getComponent(i, j)
            }
        }

        return Matrix(newValues)
    }

    public fun row(index: Int): Vector {
        if (index <= 0 || index > this.height) {
            throw IndexOutOfBoundsException()
        }

        return Vector(*this.values[index - 1])
    }

    public fun column(index: Int): Vector {
        if (index <= 0 || index > this.width) {
            throw IndexOutOfBoundsException()
        }

        val columnValues = DoubleArray(this.height)
        for (i in 1..this.height) {
            columnValues[i - 1] = this.getComponent(i, index)
        }
        return Vector(*columnValues)
    }

    /**
     * Calculates the Frobenius norm of the matrix

     * @return
     */
    public fun norm(): Double {
        if (this.norm.get() == null) {
            var nrm = 0.0
            for (i in 1..this.height) {
                for (j in 1..this.width) {
                    val component = this.getComponent(i, j)
                    nrm += component * component
                }
            }
            nrm = Math.sqrt(nrm)
            this.norm.set(nrm)
        }
        return this.norm.get()!!
    }

    public fun inverse(): Matrix? {
        if (this.inverse == null && !this.hasCalculatedInverseRref) {
            this.computeRrefAndInverse()
        }
        return this.inverse
    }

    public fun rref(): Matrix? {
        if (this.rrefMatrix == null && !this.hasCalculatedInverseRref) {
            this.computeRrefAndInverse()
        }
        return this.rrefMatrix
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is Matrix) {
            if (other.dimension == this.dimension) {
                for (i in 1..this.height) {
                    for (j in 1..this.width) {
                        if (java.lang.Double.doubleToLongBits(this.getComponent(i, j)) != java.lang.Double.doubleToLongBits(other.getComponent(i, j))) {
                            return false
                        }
                    }
                }
                return true
            }
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = 5
        hash = 13 * hash + Arrays.deepHashCode(this.values)
        return hash
    }

    override fun toString(): String {
        val out = StringBuilder()
        out.append("[")

        for (i in 1..this.height) {
            out.append("[")
            for (j in 1..this.width) {
                out.append(this.getComponent(i, j))

                if (j < this.width) {
                    out.append(", ")
                }
            }
            out.append("]")

            if (i < this.height) {
                out.append(",").append(System.lineSeparator())
            }
        }

        out.append("]")

        return out.toString()
    }

    /**
     * A method that calculates the determinant of the Matrix.

     * @return Returns the determinant of the matrix.
     * *
     * *
     * @throws InvalidOperationException if the matrix is not square
     */
    public fun det(): Double {
        if (!this.isSquare()) {
            throw IllegalStateException("Cannot take determinant of a non-square matrix.")
        }
        if (this.height == 1) {
            return this.values[0][0]
        } else {
            var det = 0.0
            for (index in 1..this.height) {
                det += (this.getComponent(1, index) * this.cofactor(1, index))
            }
            return det
        }
    }

    /**
     * A method that returns the minor of the Matrix about a specified element.

     * @param row the row of the element about which to take the minor
     * *
     * @param column the column of the element about which to take the minor
     * *
     * *
     * @return Returns a new Matrix object that is the minor of this matrix about the specified element.
     */
    public fun minor(row: Int, column: Int): Matrix {
        // The minor will be smaller by one row and column
        val newValues = Array(this.height - 1, { DoubleArray(this.width - 1) })

        var microRow = 0
        var microColumn = 0

        for (macroRow in 1..this.height) {
            if (macroRow != row) {
                microColumn = 0
                for (macroColumn in 1..this.width) {
                    if (macroColumn != column) {
                        newValues[microRow][microColumn] = this.getComponent(macroRow, macroColumn)
                        microColumn++
                    }
                }
                microRow++
            }
        }

        val output = Matrix(newValues)
        return output
    }

    /**
     * A method that calculates the cofactor of a specified element.

     * @param row the row of the element to get the cofactor of
     * *
     * @param col the column of the element to get the cofactor of
     * *
     * *
     * @return Returns the cofactor of the specified element in the matrix
     * *
     * *
     * @throws InvalidOperationException if the matrix is not square
     */
    public fun cofactor(row: Int, col: Int): Double {
        if (!isSquare()) {
            throw IllegalStateException("Cannot find cofactors for a non-square matrix.")
        }

        return this.minor(row, col).det() * Math.pow(-1.0, (row + col).toDouble())
    }


    // Private Methods
    private fun computeRrefAndInverse() {
        this.hasCalculatedInverseRref = true

        var invertable = true

        // Get a copy of the values in this matrix
        val rrefValues = Array(this.height, { DoubleArray(this.width) })
        val inverseValues = Array(this.height, { DoubleArray(this.width) })
        for (i in 0..this.height - 1) {
            System.arraycopy(this.values[i], 0, rrefValues[i], 0, this.width)
            inverseValues[i][i] = 1.0
        }

        try {
            // For Each Column
            for (j in 0..this.height - 1) {
                val rowOfMax = rowBelowWithLargestValue(rrefValues, j, j)
                if (rowOfMax < 0) {
                    this.inverse = null
                    this.rrefMatrix = Matrix(rrefValues)
                    return
                }
                if (rowOfMax != j) {
                    swapRows(rrefValues, j, rowOfMax)
                    swapRows(inverseValues, j, rowOfMax)
                }

                // Eliminate all columns below
                for (i in j + 1..this.height - 1) {
                    val scalar = -1 * rrefValues[i][j] / rrefValues[j][j]
                    addMultOfRow(rrefValues, j, i, scalar)
                    addMultOfRow(inverseValues, j, i, scalar)
                }
            }
        } catch (ex: ArithmeticException) {
            invertable = false
        }


        // Make leading terms '1'
        for (i in 0..this.height - 1) {
            if (rrefValues[i][i] != 0.0) {
                val scalar = 1.0 / rrefValues[i][i]
                multiplyRow(rrefValues, i, scalar)
                multiplyRow(inverseValues, i, scalar)
            }
        }

        // Back-Substitute to eliminate terms where possible
        for (j in this.height - 1 downTo 0) {
            for (i in j - 1 downTo 0) {
                if (rrefValues[j][j] != 0.0) {
                    val scalar = -1.0 * rrefValues[i][j] / rrefValues[j][j]
                    addMultOfRow(rrefValues, j, i, scalar)
                    addMultOfRow(inverseValues, j, i, scalar)
                }
            }
        }

        this.rrefMatrix = Matrix(rrefValues)

        if (this.isSquare() && invertable) {
            this.inverse = Matrix(inverseValues)
        }
    }

    companion object {

        // Factory Methods
        public fun identity(dimension: Int): Matrix {
            var dim = dimension
            if (dim <= 0) {
                dim = 1
            }

            val values = Array(dim, { DoubleArray(dim) })
            for (i in 0..dim - 1) {
                for (j in 0..dim - 1) {
                    if (i == j) {
                        values[i][j] = 1.0
                    } else {
                        values[i][j] = 0.0
                    }
                }
            }

            return Matrix(values)
        }

        public fun euler3(angle: Angle, component: Int): Matrix {
            when (component) {
                1 -> return Matrix(arrayOf(doubleArrayOf(1.0, 0.0, 0.0), doubleArrayOf(0.0, angle.cos(), -1.0 * angle.sin()), doubleArrayOf(0.0, angle.sin(), angle.cos())))
                2 -> return Matrix(arrayOf(doubleArrayOf(angle.cos(), 0.0, angle.sin()), doubleArrayOf(0.0, 1.0, 0.0), doubleArrayOf(-1.0 * angle.sin(), 0.0, angle.cos())))
                3 -> return Matrix(arrayOf(doubleArrayOf(angle.cos(), -1.0 * angle.sin(), 0.0), doubleArrayOf(angle.sin(), angle.cos(), 0.0), doubleArrayOf(0.0, 0.0, 1.0)))
                else -> return Matrix.identity(3)
            }
        }


        // Private Static Methods
        private fun isLegal(values: Array<DoubleArray>): Boolean {
            if (values.size() <= 0) {
                return false
            }

            val width = values[0].size()

            if (width <= 0) {
                return false
            }

            for (value in values) {
                if (value.size() != width) {
                    return false
                }
            }
            return true
        }

        private fun rowBelowWithLargestValue(values: Array<DoubleArray>, column: Int, startRow: Int): Int {
            var largestMagnitude = 0.0
            var rowOfLargest = -1
            for (i in startRow..values.size() - 1) {
                if (Math.abs(values[i][column]) > largestMagnitude) {
                    largestMagnitude = Math.abs(values[i][column])
                    rowOfLargest = i
                }
            }
            return rowOfLargest
        }

        private fun swapRows(values: Array<DoubleArray>, row1: Int, row2: Int) {
            val temp = values[row1]
            values[row1] = values[row2]
            values[row2] = temp
        }

        private fun addMultOfRow(values: Array<DoubleArray>, row1: Int, row2: Int, scalar: Double) {
            for (j in 0..values[0].size() - 1) {
                values[row2][j] += values[row1][j] * scalar
            }
        }

        private fun multiplyRow(values: Array<DoubleArray>, row: Int, scalar: Double) {
            for (j in 0..values[0].size() - 1) {
                values[row][j] = values[row][j] * scalar
            }
        }
    }

}
