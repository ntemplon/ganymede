/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.vector

import com.jupiter.ganymede.math.geometry.Angle
import com.jupiter.ganymede.math.geometry.Plane3

/**

 * @author Nathan Templon
 */
public class Vector3 @jvmOverloads constructor(public val x: Double = 0.0, public val y: Double = 0.0, public val z: Double = 0.0) : Vector(x, y, z) {


    // Properties
    override fun getUnitVector(): Vector3 {
        val normal = super.getUnitVector()
        return Vector3(normal.getComponent(1), normal.getComponent(2), normal.getComponent(3))
    }


    // Public Methods
    public fun cross(other: Vector3): Vector3 {
        return Vector3(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x)
    }

    override fun plus(other: Vector?): Vector3 {
        if (other!!.dimension == 3) {
            return Vector3(this.x + other.getComponent(1), this.y + other.getComponent(2), this.z + other.getComponent(3))
        }
        throw IllegalArgumentException()
    }

    override fun minus(other: Vector): Vector3 {
        return this.plus(other.times(-1.0))
    }

    override fun times(scalar: Double): Vector3 {
        return Vector3(this.x * scalar, this.y * scalar, this.z * scalar)
    }

    override fun vectorProjectionOnto(other: Vector): Vector3 {
        if (other.dimension == 3) {
            val projection = super.vectorProjectionOnto(other)
            return Vector3(projection.getComponent(1), projection.getComponent(2), projection.getComponent(3))
        }
        throw IllegalArgumentException()
    }

    public fun vectorProjectionOnto(plane: Plane3): Vector3 {
        val normal = plane.normal
        return this.minus(this.vectorProjectionOnto(normal))
    }

    public fun scalarProjectionOnto(plane: Plane3): Double {
        return this.vectorProjectionOnto(plane).norm()
    }

    public fun angleTo(plane: Plane3): Angle {
        return this.angleTo(this.vectorProjectionOnto(plane))
    }

    companion object {

        // Factory Methods
        public fun fromVector(other: Vector): Vector3 {
            if (other.dimension != 3) {
                throw IllegalArgumentException("Vector3 Objects can only be constructed from Vectors with 3 components!")
            }
            return Vector3(other.getComponent(1), other.getComponent(2), other.getComponent(3))
        }
    }

}// Initialization
