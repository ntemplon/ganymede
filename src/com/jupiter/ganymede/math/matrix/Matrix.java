/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.matrix;

import com.jupiter.ganymede.math.geometry.Angle;
import com.jupiter.ganymede.math.vector.Vector;
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
                return new Matrix(new double[][]
                        {{1.0, 0.0, 0.0},
                         {0.0, angle.cos(), -1.0 * angle.sin()},
                         {0.0, angle.sin(), angle.cos()}}
                );
            case 2:
                return new Matrix(new double[][]
                        {{angle.cos(), 0.0, angle.sin()},
                         {0.0, 1.0, 0.0},
                         {-1.0 * angle.sin(), 0.0, angle.cos()}}
                );
            case 3:
                return new Matrix(new double[][]
                        {{angle.cos(), -1.0 * angle.sin(), 0.0},
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


    // Initialization
    public Matrix(double[][] values) {
        if (!Matrix.isLegal(values)) {
            throw new IllegalArgumentException();
        }

        this.values = values;
        this.height = values.length;
        this.width = values[0].length;
        this.dimension = new Dimension(this.width, this.height);
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

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Matrix) {
            Matrix matrix = (Matrix) other;
            if (matrix.dimension.equals(this.dimension)) {
                for (int i = 1; i <= this.height; i++) {
                    for (int j = 1; j <= this.width; j++) {
                        if (Double.doubleToLongBits(this.getComponent(i, j)) != Double.doubleToLongBits(matrix.getComponent(i, j))) {
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

}
