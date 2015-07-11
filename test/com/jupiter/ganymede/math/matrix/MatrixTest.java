/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jupiter.ganymede.math.matrix;

import com.jupiter.ganymede.math.geometry.Angle;
import com.jupiter.ganymede.math.geometry.Angle.AngleType;
import com.jupiter.ganymede.math.vector.Vector;
import org.junit.*;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author NathanT
 */
public class MatrixTest {

    public MatrixTest() {
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
     * Test of getComponent method, of class Matrix.
     */
    @Test
    public void testGetComponent() {
        System.out.println("getComponent");
        int i = 1;
        int j = 2;
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}});
        double expResult = 3.0;
        double result = instance.getComponent(i, j);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getHeight method, of class Matrix.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        int expResult = 3;
        int result = instance.getHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWidth method, of class Matrix.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        int expResult = 2;
        int result = instance.getWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDimension method, of class Matrix.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        Dimension expResult = new Dimension(2, 3);
        Dimension result = instance.getDimension();
        assertEquals(expResult, result);
    }

    /**
     * Test of plus method, of class Matrix.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        Matrix other = new Matrix(new double[][] {{2, -1}, {-1, 4}, {3, -4}});
        Matrix instance = new Matrix(new double[][] {{1, 3}, {-2, 4}, {5, 9}});
        Matrix expResult = new Matrix(new double[][] {{3, 2}, {-3, 8}, {8, 5}});
        Matrix result = instance.plus(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of row method, of class Matrix.
     */
    @Test
    public void testRow() {
        System.out.println("row");
        int index = 2;
        Matrix instance = new Matrix(new double[][] {{1, 3}, {-2, 4}, {5, 9}});
        Vector expResult = new Vector(-2, 4);
        Vector result = instance.row(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of column method, of class Matrix.
     */
    @Test
    public void testColumn() {
        System.out.println("column");
        int index = 2;
        Matrix instance = new Matrix(new double[][] {{1, 3}, {-2, 4}, {5, 9}});
        Vector expResult = new Vector(3, 4, 9);
        Vector result = instance.column(index);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Matrix.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object other = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        boolean expResult = true;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Matrix.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        String expResult = "[[1.0, 3.0]," + System.lineSeparator() + "[-2.0, 4.0]," + System.lineSeparator() + "[5.0, 9.0]]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of times method, of class Matrix.
     */
    @Test
    public void testTimes_double() {
        System.out.println("times");
        double scalar = 2.0;
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        Matrix expResult = new Matrix(new double[][]{{2, 6}, {-4, 8}, {10, 18}});
        Matrix result = instance.times(scalar);
        assertEquals(expResult, result);
    }

    /**
     * Test of times method, of class Matrix.
     */
    @Test
    public void testTimes_Matrix() {
        System.out.println("times");
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        Matrix other = new Matrix(new double[][]{{1, 5, -2, 9}, {3, -4, -5, -2}});
        Matrix expResult = new Matrix(new double[][]{{10, -7, -17, 3}, {10, -26, -16, -26}, {32, -11, -55, 27}});
        Matrix result = instance.times(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Matrix.
     */
    @Test
    public void testHashCode() {
        
    }

    /**
     * Test of times method, of class Matrix.
     */
    @Test
    public void testTimes_Vector() {
        System.out.println("times");
        Vector vector = new Vector(1, 6);
        Matrix instance = new Matrix(new double[][]{{1, 3}, {-2, 4}, {5, 9}});
        Vector expResult = new Vector(19, 22, 59);
        Vector result = instance.times(vector);
        assertEquals(expResult, result);
    }

    /**
     * Test of identity method, of class Matrix.
     */
    @Test
    public void testIdentity() {
        System.out.println("identity");
        int dimension = 3;
        Matrix expResult = new Matrix(new double[][]
                {{1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}}
        );
        Matrix result = Matrix.Companion.identity(dimension);
        assertEquals(expResult, result);
    }

    /**
     * Test of euler3 method, of class Matrix.
     */
    @Test
    public void testEuler3() {
        System.out.println("euler3");
        Angle angle = new Angle(30, AngleType.DEGREES);
        
        Matrix expResult1 = new Matrix(new double[][] {
            {1.0, 0.0, 0.0},
            {0.0, Math.sqrt(3.0) / 2.0, -0.5},
            {0.0, 0.5, Math.sqrt(3.0) / 2.0}
        });
        Matrix result1 = Matrix.Companion.euler3(angle, 1);
        assertMatrixEquals(expResult1, result1);
        
        Matrix expResult2 = new Matrix(new double[][] {
            {Math.sqrt(3.0) / 2.0, 0.0, 0.5},
            {0.0, 1.0, 0.0},
            {-0.5, 0.0, Math.sqrt(3.0) / 2.0}
        });
        Matrix result2 = Matrix.Companion.euler3(angle, 2);
        assertMatrixEquals(expResult2, result2);
        
        Matrix expResult3 = new Matrix(new double[][] {
            {Math.sqrt(3.0) / 2.0, -0.5, 0.0},
            {0.5, Math.sqrt(3.0) / 2.0, 0.0},
            {0.0, 0.0, 1.0}
        });
        Matrix result3 = Matrix.Companion.euler3(angle, 3);
        assertMatrixEquals(expResult3, result3);
    }
    
    
    // Utility methods
    private void assertMatrixEquals(Matrix matrix1, Matrix matrix2) {
        if (!matrix1.getDimension().equals(matrix2.getDimension())) {
            assert(false);
        }
        for(int i = 1; i <= matrix1.getHeight(); i++) {
            for (int j = 1; j <= matrix1.getWidth(); j++) {
                assertEquals(matrix1.getComponent(i, j), matrix2.getComponent(i, j), 1e-6);
            }
        }
    }

    /**
     * Test of isSquare method, of class Matrix.
     */
    @Test
    public void testIsSquare() {
        System.out.println("isSquare");
        Matrix instance = new Matrix(new double[][] {
            {0, 0},
            {0, 0}
        });
        boolean expResult = true;
        boolean result = instance.isSquare();
        assertEquals(expResult, result);
        
        Matrix instance2 = new Matrix(new double[][] {
            {1, 0}
        });
        boolean expResult2 = false;
        boolean result2 = instance2.isSquare();
        assertEquals(expResult2, result2);
    }

    /**
     * Test of norm method, of class Matrix.
     */
    @Test
    public void testNorm() {
        System.out.println("norm");
        Matrix instance = new Matrix(new double[][] {
            {1, 3},
            {5, 7}
        });
        double expResult = Math.sqrt(1 + 9 + 25 + 49);
        double result = instance.norm();
        assertEquals(expResult, result, 1e-9);
    }

    /**
     * Test of inverse method, of class Matrix.
     */
    @Test
    public void testInverse() {
        System.out.println("inverse");
        Matrix instance = new Matrix(new double[][] {
            {1, 3, -2, 7},
            {3, 5, 1, -3},
            {9, -9, 2, 3},
            {1, 3, -2, 10}
        });
        Matrix expResult = new Matrix(new double[][] {
            {193.0, 36.0, 39.0, -136.0},
            {-51.0, 60.0, -21.0, 60.0},
            {-840.0, 108.0, -12.0, 624.0},
            {-172.0, 0.0, 0.0, 172.0}
        }).times(1.0 / 516.0);
        Matrix result = instance.inverse();
        
        assert(result.plus(expResult.times(-1.0)).norm() < 1e-6);
    }

    /**
     * Test of det method, of class Matrix.
     */
    @Test
    public void testDet() {
        System.out.println("det");
        Matrix instance = new Matrix(new double[][] {
            {1, -2, 5},
            {6, 7, 2},
            {-3, 4, 8}
        });
        double expResult = 381.0;
        double result = instance.det();
        assertEquals(expResult, result, 1e-6);
    }

    /**
     * Test of minor method, of class Matrix.
     */
    @Test
    public void testMinor() {
        System.out.println("minor");
        int row = 1;
        int column = 2;
        Matrix instance = new Matrix(new double[][] {
            {1, 3},
            {5, 7}
        });
        Matrix expResult = new Matrix(new double[][] {
            {5}
        });
        Matrix result = instance.minor(row, column);
        assertEquals(expResult, result);
    }

    /**
     * Test of cofactor method, of class Matrix.
     */
    @Test
    public void testCofactor() {
        System.out.println("cofactor");
        int row = 2;
        int col = 1;
        Matrix instance = new Matrix(new double[][] {
            {1, 3},
            {5, 7}
        });
        double expResult = -3.0;
        double result = instance.cofactor(row, col);
        assertEquals(expResult, result, 1e-9);
    }

    /**
     * Test of rref method, of class Matrix.
     */
    @Test
    public void testRref() {
        System.out.println("rref");
        Matrix instance = new Matrix(new double[][] {
            {1, 3, -2, 7},
            {3, 5, 1, -3},
            {9, -9, 2, 3}
        });
        Matrix expResult = new Matrix(new double[][] {
            {1.0, 0.0, 0.0, 34.0 / 43.0},
            {0.0, 1.0, 0.0, -15.0 / 43.0},
            {0.0, 0.0, 1.0, -156.0 / 43.0}
        });
        Matrix result = instance.rref();
        assert(result.plus(expResult.times(-1.0)).norm() < 1e-6);
    }

}