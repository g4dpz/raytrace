package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RotationTest {

    @Test
    public void xAxisRotation() {
        Point p1 = new Point(0, 1, 0);
        Matrix halfQuarter = Matrix.rotationX(Math.PI / 4.0);
        Matrix fullQuarter = Matrix.rotationX(Math.PI / 2.0);
        Point p2 = new Point(halfQuarter.multiply(p1));
        Point p3 = new Point(0.0, Math.sqrt(2.0) / 2.0, Math.sqrt(2.0) / 2.0);
        assertTrue(p3.equals(p2));
        Point p4 = new Point(fullQuarter.multiply(p1));
        Point p5 = new Point(0.0, 0.0, 1.0);
        assertTrue(p5.equals(p4));
    }

    @Test
    public void inverseXAxisRotation() {
        Point p1 = new Point(0, 1, 0);
        Matrix halfQuarter = Matrix.rotationX(Math.PI / 4.0);
        Matrix inverse = Matrix.invert(halfQuarter);
        Point p2 = new Point(inverse.multiply(p1));
        assertTrue(new Point(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2).equals(p2));
    }

    @Test
    public void yAxisRotation() {
        Point p1 = new Point(0, 0, 1);
        Matrix halfQuarter = Matrix.rotationY(Math.PI / 4.0);
        Matrix fullQuarter = Matrix.rotationY(Math.PI / 2.0);
        Point p2 = new Point(halfQuarter.multiply(p1));
        Point p3 = new Point( Math.sqrt(2.0) / 2.0, 0.0,Math.sqrt(2.0) / 2.0);
        assertTrue(p3.equals(p2));
        Point p4 = new Point(fullQuarter.multiply(p1));
        Point p5 = new Point(1.0, 0.0, 0.0);
        assertTrue(p5.equals(p4));
    }

    @Test
    public void inverseYAxisRotation() {
        Point p1 = new Point(0, 0, 1);
        Matrix halfQuarter = Matrix.rotationY(Math.PI / 4.0);
        Matrix inverse = Matrix.invert(halfQuarter);
        Point p2 = new Point(inverse.multiply(p1));
        assertTrue(new Point( -Math.sqrt(2.0) / 2.0, 0.0,Math.sqrt(2.0) / 2.0).equals(p2));
    }

    @Test
    public void zAxisRotation() {
        Point p1 = new Point(0, 1, 0);
        Matrix halfQuarter = Matrix.rotationZ(Math.PI / 4.0);
        Matrix fullQuarter = Matrix.rotationZ(Math.PI / 2.0);
        Point p2 = new Point(halfQuarter.multiply(p1));
        Point p3 = new Point( -Math.sqrt(2.0) / 2.0,Math.sqrt(2.0) / 2.0, 0.0);
        assertTrue(p3.equals(p2));
        Point p4 = new Point(fullQuarter.multiply(p1));
        Point p5 = new Point(-1.0, 1.0, 0.0);
        assertTrue(p5.equals(p4));
    }

    @Test
    public void inverseZAxisRotation() {
        Point p1 = new Point(0, 0, 1);
        Matrix halfQuarter = Matrix.rotationZ(Math.PI / 4.0);
        Matrix inverse = Matrix.invert(halfQuarter);
        Point p2 = new Point(inverse.multiply(p1));
        assertTrue(new Point(-1,-1,1).equals(p2));
    }
}
