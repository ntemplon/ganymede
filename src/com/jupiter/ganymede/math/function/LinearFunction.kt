/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.function

/**

 * @author Nathan Templon
 */
public class LinearFunction(public val slope: Double, public val intercept: Double) : SingleVariableRealFunction {

    override fun apply(x: Double?): Double? {
        return intercept + slope * x!!
    }

}
