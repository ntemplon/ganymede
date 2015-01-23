/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.vector;

import com.jupiter.ganymede.math.geometry.Angle;
import com.jupiter.ganymede.math.geometry.Angle.AngleType;
import com.jupiter.ganymede.math.geometry.Plane3;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nathan Templon
 */
public class Vector3Test {
    
    public Vector3Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getUnitVector method, of class Vector3.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        Vector3 instance = new Vector3(1, 1, 1);
        Vector3 expResult = new Vector3(1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0), 1.0 / Math.sqrt(3.0));
        Vector3 result = instance.getUnitVector();
        Vector3 difference = result.minus(expResult);
        assert(difference.norm() < 1e-6);
    }

    /**
     * Test of cross method, of class Vector3.
     */
    @Test
    public void testCross() {
        System.out.println("cross");
        Vector3 other = new Vector3(5, -8, 9);
        Vector3 instance = new Vector3(1, 0, 1);
        Vector3 expResult = new Vector3(8, -4, -8);
        Vector3 result = instance.cross(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of plus method, of class Vector3.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        Vector other = new Vector3(-3, -2, -1);
        Vector3 instance = new Vector3(1, 2, 3);
        Vector3 expResult = new Vector3(-2, 0, 2);
        Vector3 result = instance.plus(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of minus method, of class Vector3.
     */
    @Test
    public void testMinus() {
        System.out.println("minus");
        Vector other = new Vector3(-3, 2, -1);
        Vector3 instance = new Vector3(1, 2, -3);
        Vector3 expResult = new Vector3(4, 0, -2);
        Vector3 result = instance.minus(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of times method, of class Vector3.
     */
    @Test
    public void testTimes() {
        System.out.println("times");
        double scalar = 5.0;
        Vector3 instance = new Vector3(1, 2, 3);
        Vector3 expResult = new Vector3(5, 10, 15);
        Vector3 result = instance.times(scalar);
        assertEquals(expResult, result);
    }

    /**
     * Test of vectorProjectionOnto method, of class Vector3.
     */
    @Test
    public void testVectorProjectionOnto_Vector() {
        System.out.println("vectorProjectionOnto");
        Vector other = new Vector3(1, 1, 0);
        Vector3 instance = new Vector3(1, 0, 0);
        Vector3 expResult = new Vector3(0.5, 0.5, 0);
        Vector3 result = instance.vectorProjectionOnto(other);
        assert(result.minus(expResult).norm() < 1e-6);
    }

    /**
     * Test of vectorProjectionOnto method, of class Vector3.
     */
    @Test
    public void testVectorProjectionOnto_Plane3() {
        System.out.println("vectorProjectionOnto");
        Plane3 plane = new Plane3(new Vector3(0, 0, 1));
        Vector3 instance = new Vector3(1, 2, 1);
        Vector3 expResult = new Vector3(1, 2, 0);
        Vector3 result = instance.vectorProjectionOnto(plane);
        assertEquals(expResult, result);
    }

    /**
     * Test of angleTo method, of class Vector3.
     */
    @Test
    public void testAngleTo() {
        System.out.println("angleTo");
        Plane3 plane = new Plane3(new Vector3(0, 0, 1));
        Vector3 instance = new Vector3(1, 1, 1);
        Angle expResult = new Angle(45, AngleType.DEGREES);
        Angle result = instance.angleTo(plane);
        assert(result.plus(expResult.times(-1.0)).getMeasure(Angle.MeasureRange.PlusMinus180) < 1e-6);
    }

    /**
     * Test of scalarProjectionOnto method, of class Vector3.
     */
    @Test
    public void testScalarProjectionOnto() {
        System.out.println("scalarProjectionOnto");
        Plane3 plane = new Plane3(new Vector3(0, 0, 1));
        Vector3 instance = new Vector3(1, 2, 1);
        double expResult = Math.sqrt(5);
        double result = instance.scalarProjectionOnto(plane);
        assert(result - expResult < 1e-6);
    }
    
}
