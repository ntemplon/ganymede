/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.vector;

import com.jupiter.ganymede.math.geometry.Angle;
import com.jupiter.ganymede.math.geometry.Angle.AngleType;
import com.jupiter.ganymede.math.matrix.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author NathanT
 */
public class VectorTest {
    
    public VectorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of getComponent method, of class Vector.
     */
    @Test
    public void testGetComponent() {
        System.out.println("getComponent");
        int component = 2;
        Vector instance = new Vector(1, 2, 3);
        double expResult = 2;
        double result = instance.getComponent(component);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of plus method, of class Vector.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        Vector other = new Vector(1, 2, 3);
        Vector instance = new Vector(7, -6, 4);
        Vector expResult = new Vector(8, -4, 7);
        Vector result = instance.plus(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of dot method, of class Vector.
     */
    @Test
    public void testDot() {
        System.out.println("dot");
        Vector other = new Vector(1, 2, 3);
        Vector instance = new Vector(-1, 4, -6);
        double expResult = -11.0;
        double result = instance.dot(other);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of equals method, of class Vector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object other = new Vector(1, 2, 3);
        Vector instance = new Vector(1, 2, 3);
        boolean expResult = true;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);

        Object other2 = new Vector(-1, -2, -3);
        boolean expResult2 = false;
        boolean result2 = instance.equals(other2);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of getDimension method, of class Vector.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        Vector instance = new Vector(1, 2, 3);
        int expResult = 3;
        int result = instance.getDimension();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Vector.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Vector instance = new Vector(1, -2, 3);
        String expResult = "[1.0, -2.0, 3.0]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of times method, of class Vector.
     */
    @Test
    public void testTimes_double() {
        System.out.println("times");
        double scalar = -5.5;
        Vector instance = new Vector(1, 2, -3);
        Vector expResult = new Vector(-5.5, -11, 16.5);
        Vector result = instance.times(scalar);
        assertEquals(expResult, result);
    }

    /**
     * Test of times method, of class Vector.
     */
    @Test
    public void testTimes_Matrix() {
        System.out.println("times");
        Matrix matrix = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        Vector instance = new Vector(1, 5, -2);
        Matrix expResult = new Matrix(new double[][] {{-19, 5}});
        Matrix result = instance.times(matrix);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Vector.
     */
    @Test
    public void testHashCode() {
        
    }

    /**
     * Test of getUnitVector method, of class Vector.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        Vector instance = new Vector(1, -2, 3);
        Vector expResult = new Vector(1.0 / Math.sqrt(14), -2.0 / Math.sqrt(14), 3.0 / Math.sqrt(14));
        Vector result = instance.getUnitVector();
        assertEquals(expResult, result);
    }

    /**
     * Test of minus method, of class Vector.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        Vector other = new Vector(1, 2, 3);
        Vector instance = new Vector(4, -6, 3);
        Vector expResult = new Vector(3, -8, 0);
        Vector result = instance.minus(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of norm method, of class Vector.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        Vector instance = new Vector(1, 2, 3);
        double expResult = Math.sqrt(14);
        double result = instance.norm();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of angleTo method, of class Vector.
     */
    @Test
    public void testAngleTo() {
        System.out.println("angleTo");
        Vector other = new Vector(1, 0, 0);
        Vector instance = new Vector(1, 1, 0);
        Angle expResult = new Angle(45, AngleType.DEGREES);
        Angle result = instance.angleTo(other);
        Angle difference = result.plus(expResult.times(-1.0));
        assert(difference.getMeasure(Angle.MeasureRange.PlusMinus) < 1e-6);
    }

    /**
     * Test of scalarProjectionOnto method, of class Vector.
     */
    @Test
    public void testScalarProjectionOnto() {
        System.out.println("scalarProjectionOnto");
        Vector other = new Vector(1, 1, 0);
        Vector instance = new Vector(1, 0, 0);
        double expResult = Math.sqrt(0.5);
        double result = instance.scalarProjectionOnto(other);
        assertEquals(expResult, result, 1e-6);
    }

    /**
     * Test of vectorProjectionOnto method, of class Vector.
     */
    @Test
    public void testVectorProjectionOnto() {
        System.out.println("vectorProjectionOnto");
        Vector other = new Vector(1, 1, 0);
        Vector instance = new Vector(1, 0, 0);
        Vector expResult = new Vector(0.5, 0.5, 0);
        Vector result = instance.vectorProjectionOnto(other);
        Vector difference = result.minus(expResult);
        assert(difference.norm() < 1e-6);
    }
    
}
