/*
 * The MIT License
 *
 * Copyright 2014 Nathan Templon.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.jupiter.ganymede.math.geometry

/**
 * A class to represent angles and perform Angle operations

 * @author Nathan Templon
 */
public class Angle {

    // Enumerations
    /**
     * An enumeration specifying the different angle measurement systems
     * supported.
     */
    public enum class AngleType {

        RADIANS,
        DEGREES
    }

    /**
     * An enumeration specifying the different trigonometric functions
     * supported.
     */
    public enum class TrigFunction {

        SINE,
        COSINE,
        TANGENT,
        SECANT,
        COSECANT,
        COTANGENT
    }

    /**
     * An enumeration identifying the different ways to identify an the measure
     * of an Angle
     */
    public enum class MeasureRange {

        /**
         * Between 0 and 2 PI (360 degrees)
         */
        FullCircle,
        /**
         * Between +- 2 PI (+- 180 degrees)
         */
        PlusMinus
    }


    // Fields
    public var measure: Double = 0.toDouble()
        private set

    /**
     * A method that returns the measure of the angle in a specified measurement
     * system, between 0 and 2 PI (360 deg).

     * @param type the AngleType (RADIANS or DEGREES) that the user wants the
     * *             angle reported in
     * *
     * *
     * @return Returns the angle measure in radians or degrees, as specified.
     */
    public fun getMeasure(type: AngleType): Double = when (type) {
        Angle.AngleType.RADIANS -> measure
        Angle.AngleType.DEGREES -> measure * (180.0 / Math.PI)
        else -> measure
    }

    /**
     * A method that returns the measure of the angle in the specified range, in
     * radians.

     * @param range The range to return the measure of the angle in
     * *
     * *
     * @return Returns the measure of the angle in the specified range, in
     * *         radians.
     */
    public fun getMeasure(range: MeasureRange): Double = when (range) {
        Angle.MeasureRange.FullCircle -> this.measure
        Angle.MeasureRange.PlusMinus ->
            if (this.measure < Math.PI) {
                this.measure
            } else {
                (this.measure - (2 * Math.PI))
            }
        else -> this.measure
    }

    /**
     * A method that returns the measure of the angle in the specified range and
     * units

     * @param type  The type of Angle measurement
     * *
     * @param range The range to measure the angle in
     * *
     * *
     * @return Returns the measure of the angle in the specified range and units
     */
    public fun getMeasure(type: AngleType, range: MeasureRange): Double {
        val radVal = this.getMeasure(range)
        return when (type) {
            Angle.AngleType.DEGREES -> radVal * (180.0 / Math.PI)
            Angle.AngleType.RADIANS -> radVal
            else -> radVal
        }
    }

    /**
     * A method to get the sine of the angle.

     * @return Returns the sine of the angle.
     */
    public fun sin(): Double {
        return Math.sin(measure)
    }

    /**
     * A method to get the cosine of the angle.

     * @return Returns the cosine of the angle.
     */
    public fun cos(): Double {
        return Math.cos(measure)
    }

    /**
     * A method to get the tangent of the angle.

     * @return Returns the tangent of the angle.
     */
    public fun tan(): Double {
        return Math.tan(measure)
    }

    /**
     * A method to get the secant of the angle.

     * @return Returns the secant of the angle.
     */
    public fun sec(): Double {
        return 1.0 / Math.cos(measure)
    }

    /**
     * A method to get the cosecant of the angle.

     * @return Returns the cosecant of the angle.
     */
    public fun csc(): Double {
        return 1.0 / Math.sin(measure)
    }

    /**
     * A method to get the cotangent of the angle.

     * @return Returns the cotangent of the angle.
     */
    public fun cot(): Double {
        return Math.cos(measure) / Math.sin(measure)
    }

    /**
     * A method to get the hyperbolic sine of the angle.

     * @return Returns the hyperbolic sine of the angle.
     */
    public fun sinh(): Double {
        return Math.sinh(measure)
    }

    /**
     * A method to get the hyperbolic cosine of the angle.

     * @return Returns the hyperbolic cosine of the angle.
     */
    public fun cosh(): Double {
        return Math.cosh(measure)
    }

    /**
     * A method to get the hyperbolic tangent of the angle.

     * @return Returns the hyperbolic tangent of the angle.
     */
    public fun tanh(): Double {
        return Math.tanh(measure)
    }

    /**
     * A method to get the hyperbolic secant of the angle.

     * @return Returns the hyperbolic secant of the angle.
     */
    public fun sech(): Double {
        return 1.0 / Math.cosh(measure)
    }

    /**
     * A method to get the hyperbolic cosecant of the angle.

     * @return Returns the hyperbolic cosecant of the angle.
     */
    public fun csch(): Double {
        return 1.0 / Math.sinh(measure)
    }

    /**
     * A method to get the hyperbolic cotangent of the angle.

     * @return Returns the hyperbolic cotangent of the angle.
     */
    public fun coth(): Double {
        return Math.cosh(measure) / Math.sinh(measure)
    }


    // Initialization
    /**
     * A default constructor that creates a new Angle of measure 0.
     */
    public constructor() {
        this.measure = 0.0
    }

    /**
     * A constructor that allows the user to specify the angle's measure in
     * radians

     * @param value the measure of the angle to be created, in radians
     */
    public constructor(value: Double) {
        this.measure = value

        correctValueRange()
    }

    /**
     * A constructor that allows the user to specify the angle's measure and the
     * system in which it was measured.

     * @param value the measure of the angle to be created, in the measurement
     * *              system specified
     * *
     * @param type  the angle measurement system to be used, either
     * *              AngleType.RADIANS or AngleType.DEGREES
     */
    public constructor(value: Double, type: Angle.AngleType) {
        when (type) {
            Angle.AngleType.RADIANS -> this.measure = value

            Angle.AngleType.DEGREES -> this.measure = value * (Math.PI / 180.0)
        }

        correctValueRange()
    }

    /**
     * A constructor that allows the user to specify an angle by the value of
     * one of its trigonometric functions

     * @param funcVal  the value of the specified trigonometric function for the
     * *                 angle
     * *
     * @param function the trigonometric function whose value is given (from the
     * *                 Angle.TrigFunction enum)
     */
    public constructor(funcVal: Double, function: Angle.TrigFunction) {
        var inputFuncVal = funcVal

        // Correct for double precision errors
        if (java.lang.Double.doubleToLongBits(inputFuncVal) == java.lang.Double.doubleToLongBits(1.0)) {
            inputFuncVal = 1.0
        } else if (java.lang.Double.doubleToLongBits(inputFuncVal) == java.lang.Double.doubleToLongBits(-1.0)) {
            inputFuncVal = -1.0
        } else if (java.lang.Double.doubleToLongBits(inputFuncVal) == java.lang.Double.doubleToLongBits(0.0)) {
            inputFuncVal = 0.0
        }

        when (function) {
            Angle.TrigFunction.COSECANT -> {
                inputFuncVal = 1.0 / inputFuncVal
                this.measure = Math.asin(Math.max(-1.0, Math.min(1.0, inputFuncVal)))
            }
            Angle.TrigFunction.SINE -> this.measure = Math.asin(Math.max(-1.0, Math.min(1.0, inputFuncVal)))

            Angle.TrigFunction.SECANT -> {
                inputFuncVal = 1.0 / inputFuncVal
                this.measure = Math.acos(Math.max(-1.0, Math.min(1.0, inputFuncVal)))
            }
            Angle.TrigFunction.COSINE -> this.measure = Math.acos(Math.max(-1.0, Math.min(1.0, inputFuncVal)))

            Angle.TrigFunction.COTANGENT -> {
                if (inputFuncVal == 0.0) {
                    this.measure = Math.PI
                } else {
                    inputFuncVal = 1.0 / inputFuncVal
                    this.measure = Math.atan(inputFuncVal)
                }
            }
            Angle.TrigFunction.TANGENT -> this.measure = Math.atan(inputFuncVal)
        }

        correctValueRange()
    }


    // Public Methods
    /**
     * A method that facilitates the addition of two Angle objects.

     * @param other the angle to be added to this one.
     * *
     * *
     * @return Returns a new Angle object, equal in size to the sum of the two
     * *         angles.
     */
    public fun plus(other: Angle): Angle {
        return Angle(this.measure + other.measure)
    }

    public fun minus(other: Angle): Angle {
        return this.plus(other.times(-1.0))
    }

    /**
     * A method that multiplies the Angle object by a scalar multiplier

     * @param scalar the scalar to multiply the angle by
     * *
     * *
     * @return Returns an angle that is the result of multiplying this angle by
     * *         a given scalar.
     */
    public fun times(scalar: Double): Angle {
        return Angle(this.measure * scalar)
    }

    /**
     * A method that tests this angle for equality against another Angle object.

     * @param other the Angle to be compared to this one for equality.
     * *
     * *
     * @return Returns a boolean indicating if the two angles are equal.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is Angle) {
            return false
        }
        return java.lang.Double.doubleToLongBits(other.measure) == java.lang.Double.doubleToLongBits(this.measure)
    }

    /**
     * Generates an integer representation of the Angle

     * @return Returns an integer representation of the Angle
     */
    override fun hashCode(): Int {
        var hash = 7
        hash *= 53
        hash += (java.lang.Double.doubleToLongBits(this.measure) xor (java.lang.Double.doubleToLongBits(this.measure).ushr(32))).toInt()
        return hash
    }

    /**
     * A method to return the value of the angle in radians as a String

     * @return Returns a String of the magnitude of the angle in radians.
     */
    override fun toString(): String {
        return this.getMeasure(AngleType.RADIANS).toString() + " radians (" + this.getMeasure(AngleType.DEGREES) + " degrees)"
    }


    // Private Methods
    /**
     * Changes the angles measure to be between 0 and 2 PI, inclusive.
     */
    private fun correctValueRange() {
        //        final double twoPI = 2.0 * Math.PI;
        //        if (this.measure > twoPI) {
        //            int revsAbove = (int) Math.floor((this.measure - twoPI) / twoPI);
        //            this.measure -= revsAbove * twoPI;
        //        }
        //        if (this.measure < twoPI) {
        //            int revsBelow = (int) Math.ceil((this.measure + twoPI) / twoPI);
        //            this.measure += revsBelow * twoPI;
        //        }

        while (measure < 0.0) {
            measure += (2 * Math.PI)
        }
        while (measure > (2.0 * Math.PI)) {
            measure -= (2.0 * Math.PI)
        }

        // Correct 2 PI to 0
        //        if (doubleEquals(measure, 2.0 * Math.PI)) {
        //            measure = 0.0;
        //        }
        //        double frac2Pi = this.measure / (2 * Math.PI);
        //        this.measure -= ((int) Math.floor(frac2Pi)) * (2 * Math.PI);
    }
}// Properties
/**
 * A method that returns the measure of the angle in radians, between 0 and
 * 2 PI.

 * @return Returns the angle measure in radians.
 */
