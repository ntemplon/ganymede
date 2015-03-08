/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.function;

public class FuncPoint implements Comparable {

    // Constants
    public static String VALUE_SEPARATION_STRING = ",";


    // Static Methods
    public static FuncPoint fromString(String line) {
        String value = line.trim();
        String[] parts = value.split(VALUE_SEPARATION_STRING);
        try {
            double x = Double.parseDouble(parts[0].trim());
            double y = Double.parseDouble(parts[1].trim());
            return new FuncPoint(x, y);
        }
        catch (NumberFormatException ex) {
            // Pass on and return a null value
        }
        return null;
    }


    // Private Members
    public final double x;
    public final double y;

    // Initialization
    public FuncPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }


    // Public Methods
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof FuncPoint)) {
            return 0;
        }
        FuncPoint other = (FuncPoint) o;
        return Double.compare(this.x, other.x);
    }
}
