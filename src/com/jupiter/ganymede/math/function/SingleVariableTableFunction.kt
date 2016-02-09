/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.function

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections

/**

 * @author Nathan Templon
 */
public class SingleVariableTableFunction : SingleVariableRealFunction {

    private fun comparePoints(p1: FuncPoint, p2: FuncPoint): Int {
        return java.lang.Double.compare(p1.x, p2.x)
    }


    // Properties
    public val points: List<FuncPoint>
    public val hasFiniteDomain: Boolean
    public val domainMin: Double
    public val domainMax: Double


    // Initialization
    public constructor() {
        this.hasFiniteDomain = false
        this.domainMin = 0.0
        this.domainMax = 0.0

        this.points = ArrayList<FuncPoint>()
    }

    public constructor(points: Array<FuncPoint>) {
        this.hasFiniteDomain = false
        this.domainMin = 0.0
        this.domainMax = 0.0

        this.points = points.toList()
    }

    public constructor(domainMin: Double, domainMax: Double, points: Array<FuncPoint>) {
        this.hasFiniteDomain = true
        this.domainMin = domainMin
        this.domainMax = domainMax

        this.points = points.toList()
    }

    public constructor(points: List<FuncPoint>) {
        this.hasFiniteDomain = false
        this.domainMin = 0.0
        this.domainMax = 0.0

        this.points = points.toList() // Extra call to get a new reference
    }

    public constructor(domainMin: Double, domainMax: Double, points: List<FuncPoint>) {
        this.hasFiniteDomain = true
        this.domainMin = domainMin
        this.domainMax = domainMax

        this.points = points.toList() // Extra call to get new reference
    }


    // Public Members
    override fun apply(value: Double): Double {

        // If there are no points, return 0
        if (this.points.isEmpty()) {
            return 0.0
        } // If there is one point, that is the value
        else if (this.points.size == 1) {
            return this.points.get(0).y
        }

        if (value >= this.domainMax) {
            val penultPoint = this.points.get(this.points.size - 2)
            val finalPoint = this.points.get(this.points.size - 1)
            val finalSlope = (finalPoint.y - penultPoint.y) / (finalPoint.x - penultPoint.x)
            val change = finalSlope * (value - finalPoint.x)
            return (finalPoint.y + change)
        } else if (value <= this.domainMin) {
            val firstPoint = this.points.get(0)
            val secondPoint = this.points.get(1)
            val initialSlope = (secondPoint.y - firstPoint.y) / (secondPoint.x - firstPoint.x)
            val change = initialSlope * (value - firstPoint.x)
            return (firstPoint.y + change)
        }

        val lowerIndex = indexOfPointSmallerThan(value)

        val leftPoint = points.get(lowerIndex)
        val rightPoint = points.get(lowerIndex + 1)

        val interpolationR = (value - leftPoint.x) / (rightPoint.x - leftPoint.x)
        val change = (rightPoint.y - leftPoint.y)

        return interpolationR * change + leftPoint.y
    }

    public fun indexOfPointSmallerThan(value: Double): Int {
        var upperBound = points.size - 1
        var lowerBound = 0
        var guess = (upperBound + lowerBound) / 2

        var startGuess: Int = 0
        var guessRepeated = false

        while (!((points.get(guess).x < value) && (points.get(guess + 1).x > value)) && !guessRepeated) {
            startGuess = guess
            if (points.get(guess + 1).x < value) {
                lowerBound = guess
                guess = (upperBound + lowerBound) / 2
            } else if (points.get(guess).x > value) {
                upperBound = guess
                guess = (upperBound + lowerBound) / 2
            }
            guessRepeated = (startGuess == guess)
        }

        return guess
    }

}
