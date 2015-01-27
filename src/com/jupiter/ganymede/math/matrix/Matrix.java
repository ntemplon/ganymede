/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.matrix;

import com.jupiter.ganymede.math.geometry.Angle;
import com.jupiter.ganymede.math.vector.Vector;
import com.jupiter.ganymede.property.Property;
import java.awt.Dimension;
import java.util.Arrays;

/**
 *
 * @author NathanT
 */
public class Matrix {

    // Factory Methods
    public static Matrix identity(int dimension) {
        if (dimension <= 0) {
            dimension = 1;
        }

        double[][] values = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i == j) {
                    values[i][j] = 1.0;
                }
                else {
                    values[i][j] = 0.0;
                }
            }
        }

        return new Matrix(values);
    }

    public static Matrix euler3(Angle angle, int component) {
        switch (component) {
            case 1:
                return new Matrix(new double[][]{{1.0, 0.0, 0.0},
                {0.0, angle.cos(), -1.0 * angle.sin()},
                {0.0, angle.sin(), angle.cos()}}
                );
            case 2:
                return new Matrix(new double[][]{{angle.cos(), 0.0, angle.sin()},
                {0.0, 1.0, 0.0},
                {-1.0 * angle.sin(), 0.0, angle.cos()}}
                );
            case 3:
                return new Matrix(new double[][]{{angle.cos(), -1.0 * angle.sin(), 0.0},
                {angle.sin(), angle.cos(), 0.0},
                {0.0, 0.0, 1.0}}
                );
            default:
                return Matrix.identity(3);
        }
    }


    // Private Static Methods
    private static boolean isLegal(double[][] values) {
        if (values.length <= 0) {
            return false;
        }

        int width = values[0].length;

        if (width <= 0) {
            return false;
        }

        for (double[] value : values) {
            if (value.length != width) {
                return false;
            }
        }
        return true;
    }


    // Fields
    private final int height;
    private final int width;
    private final Dimension dimension;

    private final double[][] values;

    private final Property<Double> norm = new Property<>();
    private boolean hasCalculatedInverseRref;
    private Matrix inverse = null;
    private Matrix rrefMatrix = null;


    // Properties
    public double getComponent(int i, int j) {
        if (i <= 0 || i > this.height || j <= 0 || j > this.width) {
            throw new IndexOutOfBoundsException();
        }
        return this.values[i - 1][j - 1];
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final Dimension getDimension() {
        return this.dimension;
    }

    public final boolean isSquare() {
        return this.getWidth() == this.getHeight();
    }

    public final boolean isSingular() {
        if (!this.isSquare()) {
            return false;
        }
        if (!this.hasCalculatedInverseRref) {
            this.computeRrefAndInverse();
        }
        return this.inverse != null;
    }


    // Initialization
    public Matrix(double[][] values) {
        if (!Matrix.isLegal(values)) {
            throw new IllegalArgumentException();
        }

        this.values = values;
        this.height = values.length;
        this.width = values[0].length;
        this.dimension = new Dimension(this.width, this.height);

        this.norm.set(null);
    }


    // Public Methods
    public Matrix times(double scalar) {
        double[][] newValues = new double[this.height][this.width];

        for (int i = 1; i <= this.height; i++) {
            for (int j = 1; j <= this.width; j++) {
                newValues[i - 1][j - 1] = this.getComponent(i, j) * scalar;
            }
        }

        return new Matrix(newValues);
    }

    public Matrix times(Matrix other) {
        if (this.getWidth() != other.getHeight()) {
            throw new IllegalArgumentException("Matrix dimensions not valid for multiplication.");
        }

        double[][] newValues = new double[this.getHeight()][other.getWidth()];

        for (int i = 1; i <= this.getHeight(); i++) {
            for (int j = 1; j <= other.getWidth(); j++) {
                newValues[i - 1][j - 1] = this.row(i).dot(other.column(j));
            }
        }

        return new Matrix(newValues);
    }

    public Vector times(Vector vector) {
        if (vector.getDimension() != this.getWidth()) {
            throw new IllegalArgumentException("Matrix and Vector dimensions not valid for multiplication.");
        }

        double[] newValues = new double[this.getHeight()];

        for (int i = 1; i <= this.getHeight(); i++) {
            newValues[i - 1] = this.row(i).dot(vector);
        }

        return new Vector(newValues);
    }

    public Matrix plus(Matrix other) {
        double[][] newValues = new double[this.height][this.width];

        for (int i = 1; i <= this.height; i++) {
            for (int j = 1; j <= this.width; j++) {
                newValues[i - 1][j - 1] = this.getComponent(i, j) + other.getComponent(i, j);
            }
        }

        return new Matrix(newValues);
    }

    public Vector row(int index) {
        if (index <= 0 || index > this.height) {
            throw new IndexOutOfBoundsException();
        }

        return new Vector(this.values[index - 1]);
    }

    public Vector column(int index) {
        if (index <= 0 || index > this.width) {
            throw new IndexOutOfBoundsException();
        }

        double[] columnValues = new double[this.height];
        for (int i = 1; i <= this.height; i++) {
            columnValues[i - 1] = this.getComponent(i, index);
        }
        return new Vector(columnValues);
    }

    /**
     * Calculates the Frobenius norm of the matrix
     *
     * @return
     */
    public double norm() {
        if (this.norm.get() == null) {
            double nrm = 0.0;
            for (int i = 1; i <= this.getHeight(); i++) {
                for (int j = 1; j <= this.getWidth(); j++) {
                    double component = this.getComponent(i, j);
                    nrm += component * component;
                }
            }
            nrm = Math.sqrt(nrm);
            this.norm.set(nrm);
        }
        return this.norm.get();
    }

    public Matrix inverse() {
        if (this.inverse == null && !this.hasCalculatedInverseRref) {
            this.computeRrefAndInverse();
        }
        return this.inverse;
    }

    public Matrix rref() {
        if (this.rrefMatrix == null && !this.hasCalculatedInverseRref) {
            this.computeRrefAndInverse();
        }
        return this.rrefMatrix;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Matrix) {
            Matrix matrix = (Matrix) other;
            if (matrix.dimension.equals(this.dimension)) {
                for (int i = 1; i <= this.height; i++) {
                    for (int j = 1; j <= this.width; j++) {
                        if (Double.doubleToLongBits(this.getComponent(i, j)) != Double.doubleToLongBits(
                                matrix.getComponent(i, j))) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Arrays.deepHashCode(this.values);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("[");

        for (int i = 1; i <= this.getHeight(); i++) {
            out.append("[");
            for (int j = 1; j <= this.getWidth(); j++) {
                out.append(this.getComponent(i, j));

                if (j < this.getWidth()) {
                    out.append(", ");
                }
            }
            out.append("]");

            if (i < this.getHeight()) {
                out.append(",").append(System.lineSeparator());
            }
        }

        out.append("]");

        return out.toString();
    }

    /**
     * A method that calculates the determinant of the Matrix.
     *
     * @return Returns the determinant of the matrix.
     *
     * @throws InvalidOperationException if the matrix is not square
     */
    public double det() {
        if (!this.isSquare()) {
            throw new IllegalStateException("Cannot take determinant of a non-square matrix.");
        }
        if (this.getHeight() == 1) {
            return this.values[0][0];
        }
        else {
            double det = 0;
            for (int index = 1; index <= this.getHeight(); index++) {
                det += (this.getComponent(1, index) * this.cofactor(1, index));
            }
            return det;
        }
    }

    /**
     * A method that returns the minor of the Matrix about a specified element.
     *
     * @param row the row of the element about which to take the minor
     * @param column the column of the element about which to take the minor
     *
     * @return Returns a new Matrix object that is the minor of this matrix about the specified element.
     */
    public Matrix minor(int row, int column) {
        // The minor will be smaller by one row and column
        double[][] newValues = new double[this.getHeight() - 1][this.getWidth() - 1];

        int microRow = 0;
        int microColumn = 0;

        for (int macroRow = 1; macroRow <= this.getHeight(); macroRow++) {
            if (macroRow != row) {
                microColumn = 0;
                for (int macroColumn = 1; macroColumn <= this.getWidth(); macroColumn++) {
                    if (macroColumn != column) {
                        newValues[microRow][microColumn] = this.getComponent(macroRow, macroColumn);
                        microColumn++;
                    }
                }
                microRow++;
            }
        }

        Matrix output = new Matrix(newValues);
        return output;
    }

    /**
     * A method that calculates the cofactor of a specified element.
     *
     * @param row the row of the element to get the cofactor of
     * @param col the column of the element to get the cofactor of
     *
     * @return Returns the cofactor of the specified element in the matrix
     *
     * @throws InvalidOperationException if the matrix is not square
     */
    public double cofactor(int row, int col) {
        if (!isSquare()) {
            throw new IllegalStateException("Cannot find cofactors for a non-square matrix.");
        }

        return this.minor(row, col).det() * Math.pow(-1, row + col);
    }


    // Private Methods
    private void computeRrefAndInverse() {
        this.hasCalculatedInverseRref = true;

        boolean invertable = true;

        // Get a copy of the values in this matrix
        double[][] rrefValues = new double[this.getHeight()][this.getWidth()];
        double[][] inverseValues = new double[this.getHeight()][this.getWidth()];
        for (int i = 0; i < this.getHeight(); i++) {
            System.arraycopy(this.values[i], 0, rrefValues[i], 0, this.getWidth());
            inverseValues[i][i] = 1.0;
        }

        try {
            // For Each Column
            boolean reachedRref = false;
            for (int j = 0; j < this.getHeight(); j++) {
                int rowOfMax = rowBelowWithLargestValue(rrefValues, j, j);
                if (rowOfMax < 0) {
                    this.inverse = null;
                    this.rrefMatrix = new Matrix(rrefValues);
                    return;
                }
                if (rowOfMax != j) {
                    swapRows(rrefValues, j, rowOfMax);
                    swapRows(inverseValues, j, rowOfMax);
                }

                // Eliminate all columns below
                for (int i = j + 1; i < this.getHeight(); i++) {
                    double scalar = -1 * rrefValues[i][j] / rrefValues[j][j];
                    addMultOfRow(rrefValues, j, i, scalar);
                    addMultOfRow(inverseValues, j, i, scalar);
                }
            }
        }
        catch (ArithmeticException ex) {
            invertable = false;
        }

        // Make leading terms '1'
        for (int i = 0; i < this.getHeight(); i++) {
            if (rrefValues[i][i] != 0.0) {
                double scalar = 1.0 / rrefValues[i][i];
                multiplyRow(rrefValues, i, scalar);
                multiplyRow(inverseValues, i, scalar);
            }
        }

        // Back-Substitute to eliminate terms where possible
        for (int j = this.getHeight() - 1; j >= 0; j--) {
            for (int i = j - 1; i >= 0; i--) {
                if (rrefValues[j][j] != 0.0) {
                    double scalar = -1.0 * rrefValues[i][j] / rrefValues[j][j];
                    addMultOfRow(rrefValues, j, i, scalar);
                    addMultOfRow(inverseValues, j, i, scalar);
                }
            }
        }

        this.rrefMatrix = new Matrix(rrefValues);

        if (this.isSquare() && invertable) {
            this.inverse = new Matrix(inverseValues);
        }
    }

    private static int rowBelowWithLargestValue(double[][] values, int column, int startRow) {
        double largestMagnitude = 0;
        int rowOfLargest = -1;
        for (int i = startRow; i < values.length; i++) {
            if (Math.abs(values[i][column]) > largestMagnitude) {
                largestMagnitude = Math.abs(values[i][column]);
                rowOfLargest = i;
            }
        }
        return rowOfLargest;
    }

    private static void swapRows(double[][] values, int row1, int row2) {
        double[] temp = values[row1];
        values[row1] = values[row2];
        values[row2] = temp;
    }

    private static void addMultOfRow(double[][] values, int row1, int row2, double scalar) {
        for (int j = 0; j < values[0].length; j++) {
            values[row2][j] += values[row1][j] * scalar;
        }
    }

    private static void multiplyRow(double[][] values, int row, double scalar) {
        for (int j = 0; j < values[0].length; j++) {
            values[row][j] = values[row][j] * scalar;
        }
    }

}
