/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.geometry;

import com.jupiter.ganymede.math.vector.Vector3;
import java.util.Objects;

/**
 *
 * @author Nathan Templon
 */
public class Plane3 {
    
    // Fields
    private final Vector3 normal;
    
    
    // Properties
    /**
     * The unit normal vector of the plane
     * @return 
     */
    public final Vector3 getNormal() {
        return this.normal;
    }
    
    public final int getDimension() {
        return this.normal.getDimension();
    }
    
    
    // Initialization
    public Plane3(Vector3 normal) {
        this.normal = normal.getUnitVector();
    }
    
    public Plane3() {
        this(new Vector3(1, 0, 0));
    }
    
    
    // Public Methods
    @Override
    public boolean equals(Object other) {
        if (other instanceof Plane3) {
            return this.getNormal().equals(((Plane3)other).getNormal());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.normal);
        return hash;
    }
    
}
