package com.badgersoft.raytrace.elements;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatrixTest {

    @Test
    public void multiply4x4() {
        double[][] data1 = new double[][] {{1,2,3,4},{2,3,4,5},{3,4,5,6},{4,5,6,7}};
        double[][] data2 = new double[][] {{0,1,2,4},{1,2,4,8},{2,4,8,16},{4,8,16,32}};
        Matrix m1 = new Matrix(data1);
        Matrix m2 = new Matrix(data2);
        final Matrix m3 = m1.multiply(m2);

        Matrix expected = new Matrix(new double[][] {{24.0,49.0,98.0,196.0},{31.0,64.0,128.0,256.0},{38.0,79.0,158.0,316.0},{45.0,94.0,188.0,376.0}});

        compareEquals(expected, m3);
    }

    @Test
    public void multiply4x1() {
        double[][] data1 = new double[][] {{1,2,3,4},{2,4,4,2},{8,6,4,1},{0,0,0,1}};
        double[] data2 = new double[] {1,2,3,1};
        Matrix m1 = new Matrix(data1);
        Matrix m2 = new Matrix(data2);
        final Matrix m3 = m1.multiply(m2);

        Matrix expected = new Matrix(new double[] {18.0,24.0,33.0,1.0});

        compareEquals(expected, m3);
    }

    @Test
    public void identityMultiply() {
        double[][] data1 = new double[][] {{1,2,3,4},{2,4,4,2},{8,6,4,1},{0,0,0,1}};
        Matrix m1 = new Matrix(data1);
        Matrix m2 = m1.identity();
        final Matrix m3 = m1.multiply(m2);

        compareEquals(m1, m3);
    }

    @Test
    public void transpose() {
        double[][] data1 = new double[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        Matrix m1 = new Matrix(data1);
        final Matrix m2 = m1.transpose();

        Matrix expected = new Matrix(new double[][] {{1.0,5.0,9.0,13.0},{2.0,6.0,10.0,14.0},{3.0,7.0,11.0,15.0},{4.0,8.0,12.0,16.0}});

        compareEquals(expected, m2);

        compareEquals(m1.identity(), m1.identity().transpose());
    }

    @Test
    public void identity()
    {
        double[][] data1 = new double[][] {{1,2,3,4},{2,4,4,2},{8,6,4,1},{0,0,0,1}};
        Matrix m1 = new Matrix(data1);
        Matrix m2 = m1.identity();
        final Matrix m3 = m1.multiply(m2);

        compareEquals(m1, m3);
    }

    @Test
    public void submatrix()
    {
        double[][] data1 = new double[][] {{1,5,0},{-3,2,7},{0,6,-3}};
        Matrix m1 = new Matrix(data1);
        Matrix m2 = Matrix.submatrix(m1, 0, 2);
        Matrix m3 = new Matrix(new double[][] {{-3,2},{0,6}});

        compareEquals(m2, m3);

        data1 = new double[][] {{-6,1,1,6},{-8,5,8,6},{-1,0,8,2},{-7,1,-1,1}};
        m1 = new Matrix(data1);
        m2 = Matrix.submatrix(m1, 2, 1);
        m3 = new Matrix(new double[][] {{-6,1,6},{-8,8,6},{-7,-1,1}});

        compareEquals(m2, m3);
    }

    @Test
    public void determinant()
    {
        double[][] data1 = new double[][] {{1,2},{3,4}};
        Matrix m1 = new Matrix(data1);
        double d = Matrix.determinant(m1);
        assertTrue(Math.abs(d - -2.0) < 0.000001);
    }

    @Test
    public void minor() {
        double[][] data1 = new double[][] {{3,5,0},{2,-1,-7},{6,-1,5}};
        Matrix m1 = new Matrix(data1);
        double minor = Matrix.minor(m1, 1, 0);
        assertTrue(Math.abs(25.0 - minor) < 0.000001);
    }

    @Test
    public void cofactorTest1() {
        double[][] data1 = new double[][] {{3,5,0},{2,-1,-7},{6,-1,5}};

        Matrix m1 = new Matrix(data1);
        double minor = Matrix.minor(m1, 0, 0);
        assertTrue(Math.abs(-12.0 - minor) < 0.000001);

        double cofactor = Matrix.cofactor(m1, 0, 0);
        assertTrue(Math.abs(-12.0 - cofactor) < 0.000001);

        minor = Matrix.minor(m1, 1, 0);
        assertTrue(Math.abs(25.0 - minor) < 0.000001);

        cofactor = Matrix.cofactor(m1, 1, 0);
        assertTrue(Math.abs(-25.0 - cofactor) < 0.000001);
    }

    @Test
    public void cofactorTest2() {
        double[][] data1 = new double[][] {{1,2,6},{-5,8,-4},{2,6,4}};

        Matrix m1 = new Matrix(data1);

        double cofactor = Matrix.cofactor(m1, 0, 0);
        assertTrue(Math.abs(56 - cofactor) < 0.000001);

        cofactor = Matrix.cofactor(m1, 0, 1);
        assertTrue(Math.abs(12 - cofactor) < 0.000001);

        cofactor = Matrix.cofactor(m1, 0, 2);
        assertTrue(Math.abs(-46.0 - cofactor) < 0.000001);

        final double determinant = Matrix.determinant(m1);
        assertTrue(Math.abs(-196.0 - determinant) < 0.000001);

    }

    @Test
    public void cofactorTest3() {
        double[][] data1 = new double[][] {{-2,-8,3,5},{-3,1,7,3},{1,2,-9,6},{-6,7,7,-9}};

        Matrix m1 = new Matrix(data1);

        double cofactor = Matrix.cofactor(m1, 0, 0);
        assertTrue(Math.abs(690 - cofactor) < 0.000001);

        cofactor = Matrix.cofactor(m1, 0, 1);
        assertTrue(Math.abs(447 - cofactor) < 0.000001);

        cofactor = Matrix.cofactor(m1, 0, 2);
        assertTrue(Math.abs(210 - cofactor) < 0.000001);

        cofactor = Matrix.cofactor(m1, 0, 3);
        assertTrue(Math.abs(51 - cofactor) < 0.000001);


        final double determinant = Matrix.determinant(m1);
        assertTrue(Math.abs(-4071 - determinant) < 0.000001);

    }

    @Test
    public void invertible() {
        double[][] data1 = new double[][] {{6,4,4,4},{5,5,7,6},{ 4,-9, 3,-7},{ 9, 1, 7,-6}};

        Matrix m1 = new Matrix(data1);

        final double determinant = Matrix.determinant(m1);

        assertTrue(Math.abs(-2120 - determinant) < 0.000001);
    }

    @Test
    public void nonInvertible() {
        double[][] data1 = new double[][] {{-4, 2,-2,-3},{9,6,2,6},{ 0,-5, 1,-5},{0,0,0,0}};

        Matrix m1 = new Matrix(data1);

        final double determinant = Matrix.determinant(m1);

        assertTrue(Math.abs(0.0 - determinant) < 0.000001);
    }

    @Test
    public void invertTest() {
        double[][] data1 = new double[][] {{-5, 2, 6,-8},{ 1,-5, 1, 8},{ 7, 7,-6,-7},{ 1,-3, 7, 4}};
        double[][] data2 = new double[][] {{6,4,4,4},{5,5,7,6},{ 4,-9, 3,-7},{ 9, 1, 7,-6}};
        Matrix a = new Matrix(data1);
        Matrix b = new Matrix(data2);
        Matrix c = a.multiply(b);
        compareEquals(a, c.multiply(Matrix.invert(b)));

    }

    @Test
    public void invertIdentity() {
        double[][] data1 = new double[][] {{-5, 2, 6,-8},{ 1,-5, 1, 8},{ 7, 7,-6,-7},{ 1,-3, 7, 4}};
        Matrix a = new Matrix(data1);
        Matrix identity = a.identity();
        Matrix inverse = Matrix.invert(identity);
        compareEquals(identity, inverse);
    }

    @Test
    public void multiplyByInverseToGetIdentity()
    {
        double[][] data1 = new double[][] {{-5, 2, 6,-8},{ 1,-5, 1, 8},{ 7, 7,-6,-7},{ 1,-3, 7, 4}};
        Matrix a = new Matrix(data1);
        Matrix value = a.multiply(Matrix.invert(a));
        compareEquals(a.identity(), value);
    }

    @Test
    public void inverseTransposeAndViceVersaAreTheSame()
    {
        double[][] data1 = new double[][] {{-5, 2, 6,-8},{ 1,-5, 1, 8},{ 7, 7,-6,-7},{ 1,-3, 7, 4}};
        Matrix a = new Matrix(data1);
        Matrix transposed  = a.transpose();
        Matrix inverseTransposed = Matrix.invert(transposed);
        Matrix inverse = Matrix.invert(a);
        Matrix transposeInverted = inverse.transpose();
        compareEquals(inverseTransposed, transposeInverted);
    }

    @Test
    public void multiplyIdentityTuple() {
        double[][] data1 = new double[][] {{-5, 2, 6,-8},{ 1,-5, 1, 8},{ 7, 7,-6,-7},{ 1,-3, 7, 4}};
        Matrix a = new Matrix(data1);
        Tuple t1 = new Tuple(1.0, 2.0, 3.0, 4.0);
        Tuple t2 = a.identity().multiply(t1);
        assertTrue(t1.equals(t2));
    }

    @Test
    public void multiplyByTupleWithChangedIdentity() {
        double[][] data1 = new double[][] {{-5, 2, 6,-8},{ 1,-5, 1, 8},{ 7, 7,-6,-7},{ 1,-3, 7, 4}};
        Matrix a = new Matrix(data1);
        Tuple t1 = new Tuple(1.0, 2.0, 3.0, 4.0);
        final Matrix identity = a.identity();
        identity.setEntry(0,0,0.0);
        Tuple t2 = identity.multiply(t1);
        assertTrue(new Tuple(0,2,3,4).equals(t2));
    }


    @Test
    @Ignore
    public void invertLarge() {
        double[][] data1 = new double[10][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                data1[i][j] = i * 10 + j;
            }
        }
        Matrix m1 = new Matrix(data1);
        Matrix.invert(m1);
    }

    @Test
    public void createTransform() {
        Matrix transformation = Matrix.transform(1, 2, 3);
        Matrix test = new Matrix(new double[][] {{1,0,0,1}, {0,1,0,2},{0,0,1,3}, {0,0,0,1}});
        compareEquals(test, transformation);
    }

    @Test
    public void transformPoint() {
        Point p1 = new Point(-3, 4, 5);
        Point p2 = new Point(2, 1, 7);
        Matrix transformation = Matrix.transform(5, -3, 2);
        final Tuple tuple = transformation.multiply(p1);
        final Point p3 = new Point(tuple);
        assertTrue(p2.equals(p3));
    }

    @Test
    public void inversePoint() {
        Point p1 = new Point(-3, 4, 5);
        Point p2 = new Point(-8, 7, 3);
        Matrix transformation = Matrix.transform(5, -3, 2);
        final Matrix invert = Matrix.invert(transformation);
        final Tuple tuple = invert.multiply(p1);
        final Point p3 = new Point(tuple);
        assertTrue(p2.equals(p3));
    }

    @Test
    public void transformDoesNotChangeVector() {
        Vector v1 = new Vector(-3, 4, 5);
        Matrix transformation = Matrix.transform(5, -3, 2);
        Vector v2 = new Vector(transformation.multiply(v1));
        assertTrue(v1.equals(v2));
    }


    private void compareEquals(Matrix expected, Matrix test) {
        assertEquals(expected.getData()[0].length, test.getData()[0].length);

        int lineCount = 0;

        for (int i = 0 ; i < expected.getRowDimension() ; i++) {
            for (int j = 0 ; j < expected.getColumnDimension() ; j++) {
                assertTrue(Math.abs(expected.getData()[i][j] - test.getData()[i][j]) < 0.000001);
            }
        }
    }
}
