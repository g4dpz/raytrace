package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ShearingTest {

    @Test
    public void shearTransformationMovesXinProportionToY() {

        Matrix shearing = Matrix.shearing(1, 0, 0, 0, 0, 0);
        Point p1 = new Point(2,3,4);
        Point p2 = new Point(shearing.multiply(p1));
        Point p3 = new Point(5,3,4);
        assertTrue(p3.equals(p2));

    }

    @Test
    public void shearTransformationMovesXinProportionToZ() {

        Matrix shearing = Matrix.shearing(0, 1, 0, 0, 0, 0);
        Point p1 = new Point(2,3,4);
        Point p2 = new Point(shearing.multiply(p1));
        Point p3 = new Point(6,3,4);
        assertTrue(p3.equals(p2));

    }

    @Test
    public void shearTransformationMovesYinProportionToX() {

        Matrix shearing = Matrix.shearing(0, 0, 1, 0, 0, 0);
        Point p1 = new Point(2,3,4);
        Point p2 = new Point(shearing.multiply(p1));
        Point p3 = new Point(2,5,4);
        assertTrue(p3.equals(p2));

    }

    @Test
    public void shearTransformationMovesYinProportionToZ() {

        Matrix shearing = Matrix.shearing(0, 0, 0, 1, 0, 0);
        Point p1 = new Point(2,3,4);
        Point p2 = new Point(shearing.multiply(p1));
        Point p3 = new Point(2,7,4);
        assertTrue(p3.equals(p2));

    }

    @Test
    public void shearTransformationMovesZinProportionToX() {

        Matrix shearing = Matrix.shearing(0, 0, 0, 0, 1, 0);
        Point p1 = new Point(2,3,4);
        Point p2 = new Point(shearing.multiply(p1));
        Point p3 = new Point(2,3,6);
        assertTrue(p3.equals(p2));

    }

    @Test
    public void shearTransformationMovesZinProportionToY() {

        Matrix shearing = Matrix.shearing(0, 0, 0, 0, 0, 1);
        Point p1 = new Point(2,3,4);
        Point p2 = new Point(shearing.multiply(p1));
        Point p3 = new Point(2,3,7);
        assertTrue(p3.equals(p2));

    }
}
