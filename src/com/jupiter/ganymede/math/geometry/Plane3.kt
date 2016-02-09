/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.geometry

import com.jupiter.ganymede.math.vector.Vector3
import java.util.Objects

/**

 * @author Nathan Templon
 */
public class Plane3 @JvmOverloads constructor(normal: Vector3 = Vector3(1.0, 0.0, 0.0)) {

    // Fields
    public val normal: Vector3

    public fun getDimension(): Int {
        return this.normal.dimension
    }


    init {
        this.normal = normal.getUnitVector()
    }


    // Public Methods
    override fun equals(other: Any?): Boolean {
        if (other is Plane3) {
            return this.normal == other.normal
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = 7
        hash = 59 * hash + Objects.hashCode(this.normal)
        return hash
    }

}// Properties
/**
 * The unit normal vector of the plane
 * @return
 */
