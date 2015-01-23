/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.vector;

import com.jupiter.ganymede.math.geometry.Angle;
import com.jupiter.ganymede.math.geometry.Plane3;

/**
 *
 * @author Nathan Templon
 */
public class Vector3 extends Vector {
    
    // Factory Methods
    public static Vector3 fromVector(Vector other) {
        if (other.getDimension() != 3) {
            throw new IllegalArgumentException("Vector3 Objects can only be constructed from Vectors with 3 components!");
        }
        return new Vector3(
                other.getComponent(1),
                other.getComponent(2),
                other.getComponent(3)
        );
    }
    

    // Fields
    public final double x;
    public final double y;
    public final double z;


    // Properties
    @Override
    public Vector3 getUnitVector() {
        Vector normal = super.getUnitVector();
        return new Vector3(normal.getComponent(1), normal.getComponent(2), normal.getComponent(3));
    }


    // Initialization
    public Vector3() {
        this(0.0, 0.0, 0.0);
    }

    public Vector3(double x, double y, double z) {
        super(x, y, z);

        this.x = x;
        this.y = y;
        this.z = z;
    }


    // Public Methods
    public Vector3 cross(Vector3 other) {
        return new Vector3(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    @Override
    public Vector3 plus(Vector other) {
        if (other.getDimension() == 3) {
            return new Vector3(
                    this.x + other.getComponent(1),
                    this.y + other.getComponent(2),
                    this.z + other.getComponent(3)
            );
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Vector3 minus(Vector other) {
        return this.plus(other.times(-1.0));
    }

    @Override
    public Vector3 times(double scalar) {
        return new Vector3(
                this.x * scalar,
                this.y * scalar,
                this.z * scalar
        );
    }
    
    @Override
    public Vector3 vectorProjectionOnto(Vector other) {
        if (other.getDimension() == 3) {
            Vector projection = super.vectorProjectionOnto(other);
            return new Vector3(
                    projection.getComponent(1),
                    projection.getComponent(2),
                    projection.getComponent(3)
            );
        }
        throw new IllegalArgumentException();
    }

    public Vector3 vectorProjectionOnto(Plane3 plane) {
        Vector3 normal = plane.getNormal();
        return this.minus(this.vectorProjectionOnto(normal));
    }
    
    public double scalarProjectionOnto(Plane3 plane) {
        return this.vectorProjectionOnto(plane).norm();
    }
    
    public Angle angleTo(Plane3 plane) {
        return this.angleTo(this.vectorProjectionOnto(plane));
    }

}
