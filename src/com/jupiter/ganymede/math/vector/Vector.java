/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.vector;

import com.jupiter.ganymede.math.matrix.Matrix;
import java.util.Arrays;

/**
 *
 * @author NathanT
 */
public class Vector {

    // Fields
    private final int dimension;
    private final double[] values;


    // Properties
    public double getComponent(int component) {
        if (component <= 0 || component > this.getDimension()) {
            throw new IndexOutOfBoundsException();
        }

        return this.values[component - 1];
    }
    
    /**
     * @return the dimension
     */
    public int getDimension() {
        return dimension;
    }


    // Initialization
    public Vector(double... values) {
        this.values = values;
        this.dimension = this.values.length;
    }


    // Public Methods
    public Vector plus(Vector other) {
        if (other == null || other.getDimension() != this.getDimension()) {
            throw new IllegalArgumentException();
        }

        double[] newValues = new double[this.getDimension()];
        for (int i = 1; i <= this.getDimension(); i++) {
            newValues[i - 1] = this.getComponent(i) + other.getComponent(i);
        }
        return new Vector(newValues);
    }

    public Vector times(double scalar) {
        double[] newValues = new double[this.getDimension()];
        for (int i = 1; i <= this.getDimension(); i++) {
            newValues[i - 1] = this.getComponent(i) * scalar;
        }
        return new Vector(newValues);
    }
    
    public Matrix times(Matrix matrix) {
        if (this.getDimension() != matrix.getHeight()) {
            throw new IllegalArgumentException("Matrix and Vector dimensions not valid for multiplication.");
        }
        
        double[][] newValues = new double[1][matrix.getWidth()];
        
        for (int i = 1; i <= matrix.getWidth(); i++) {
            newValues[0][i - 1] = matrix.column(i).dot(this);
        }
        
        return new Matrix(newValues);
    }

    public double dot(Vector other) {
        if (other == null || other.getDimension() != this.getDimension()) {
            throw new IllegalArgumentException();
        }

        double product = 0.0;
        for (int i = 1; i <= this.getDimension(); i++) {
            product += this.getComponent(i) * other.getComponent(i);
        }
        return product;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Vector) {
            Vector vec = (Vector) other;
            if (vec.getDimension() == this.getDimension()) {
                for (int i = 1; i <= this.getDimension(); i++) {
                    if (Double.doubleToLongBits(this.getComponent(i)) != Double.doubleToLongBits(vec.getComponent(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Arrays.hashCode(this.values);
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        
        out.append("[");
        for(int i = 1; i <= this.getDimension(); i++) {
            out.append(this.getComponent(i));
            
            if (i < this.getDimension()) {
                out.append(", ");
            }
        }
        out.append("]");
        
        return out.toString();
    }

}
