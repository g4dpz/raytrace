package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PointTest {

    @Test
    public void equals() {
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(1,2,3);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void notEquals() {
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(2.0, 2.0, 3.0);
        assertTrue(!p1.equals(p2));
        Point p3 = new Point(1.0, 3.0, 3.0);
        assertTrue(!p1.equals(p3));
        Point p4 = new Point(1.0, 2.0, 4.0);
        assertTrue(!p1.equals(p4));

        Vector v = new Vector(1.0, 2.0, 3.0);
        assertTrue(!p1.equals(v));
    }

    @Test
    public void addPoint() {
        Point p1 = new Point(3.0, -2.0, 5.0);
        Point p2 = new Point(-2.0, 3.0, 1.0);
        Vector v = p1.add(p2);
        assertTrue(compare(v.getX(), 1.0));
        assertTrue(compare(v.getY(), 1.0));
        assertTrue(compare(v.getZ(), 6.0));
        assertTrue(compare(v.getW(), 0.0));
    }

    @Test
    public void subtractPoint() {
        Point p1 = new Point(3.0, -2.0, 5.0);
        Point p2 = new Point(-2.0, 3.0, 1.0);
        Vector v = p1.subtract(p2);
        assertTrue(compare(v.getX(), 5.0));
        assertTrue(compare(v.getY(), -5.0));
        assertTrue(compare(v.getZ(), 4.0));
        assertTrue(compare(v.getW(), 0.0));
    }

    @Test
    public void addVector() {
        Point p1 = new Point(3.0, -2.0, 5.0);
        Vector v = new Vector(1.1, 2.22, 3.333);
        Point p2 = p1.add(v);
        assertTrue(compare(p2.getX(), 4.1));
        assertTrue(compare(p2.getY(), 0.22));
        assertTrue(compare(p2.getZ(), 8.333));
        assertTrue(compare(p2.getW(), 1.0));
    }

    @Test
    public void subtractVector() {
        Point p1 = new Point(3.0, -2.0, 5.0);
        Vector v = new Vector(1.1, 2.22, 3.333);
        Point p2 = p1.subtract(v);
        assertTrue(compare(p2.getX(), 1.9));
        assertTrue(compare(p2.getY(), -4.22));
        assertTrue(compare(p2.getZ(), 1.667));
        assertTrue(compare(p2.getW(), 1.0));
    }

    @Test
    public void translation() {

        Point p1 = new Point(-3, 4, 5);
        Point p2 = new Point(2, 1, 7);
        Matrix translation = Matrix.translation(5, -3, 2);
        final Tuple multiply = translation.multiply(p1);
        Point p3 = new Point(multiply);
        assertTrue(p2.equals(p3));
    }

    @Test
    public void transformationSequance() {
        Point p1 = new Point(1, 0, 1);
        Matrix a = Matrix.rotationX(Math.PI / 2);
        Matrix b = Matrix.scale(5, 5, 5);
        Matrix c = Matrix.translation(10, 5, 7);

        // rotate
        Point p2 = new Point(a.multiply(p1));
        assertTrue(new Point(1, -1, 0).equals(p2));

        // scale
        Point p3 = new Point(b.multiply(p2));
        assertTrue(new Point(5, -5, 0).equals(p3));

        // translate
        Point p4 = new Point(c.multiply(p3));
        assertTrue(new Point(15, 0, 7).equals(p4));

    }@Test
    public void chainedSequence() {
        Point p1 = new Point(1, 0, 1);
        Matrix a = Matrix.rotationX(Math.PI / 2);
        Matrix b = Matrix.scale(5, 5, 5);
        Matrix c = Matrix.translation(10, 5, 7);

        Point p2 = new Point(c.multiply(b.multiply(a.multiply(p1))));
        assertTrue(new Point(15, 0, 7).equals(p2));

    }

    private boolean compare(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }
}
