package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VectorTest {

    @Test
    public void equals() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(1.0, 2.0, 3.0);
        assertTrue(v1.equals(v2));
    }

    @Test
    public void notEquals() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(2.0, 2.0, 3.0);
        assertTrue(!v1.equals(v2));
        Vector v3 = new Vector(1.0, 3.0, 3.0);
        assertTrue(!v1.equals(v3));
        Vector v4 = new Vector(1.0, 2.0, 4.0);
        assertTrue(!v1.equals(v4));
    }

    @Test
    public void vectorAdd() {
        Vector v1 = new Vector(3.0, -2.0, 5.0);
        Vector v2 = new Vector(-2.0, 3.0, 1.0);
        Vector v3 = v1.add(v2);
        assertTrue(compare(v3.getX(), 1.0));
        assertTrue(compare(v3.getY(), 1.0));
        assertTrue(compare(v3.getZ(), 6.0));
        assertTrue(compare(v3.getW(), 0.0));

    }

    @Test
    public void vectorSubtract() {
        Vector v1 = new Vector(0.0, 0.0, 0.0);
        Vector v2 = new Vector(-2.0, 3.0, 1.0);
        Vector v3 = v1.subtract(v2);
        assertTrue(compare(v3.getX(), 2.0));
        assertTrue(compare(v3.getY(), -3.0));
        assertTrue(compare(v3.getZ(), -1.0));
        assertTrue(compare(v3.getW(), 0.0));

    }

    @Test
    public void multiply() {
        Vector t1 = new Vector(3.0, -2.0, 5.0);
        Vector t2 = t1.multiply(-2.1);
        assertTrue(compare(t2.getX(), -6.3000));
        assertTrue(compare(t2.getY(), 4.2000));
        assertTrue(compare(t2.getZ(), -10.5000));
        assertTrue(compare(t2.getW(), 0.0));
    }

    @Test
    public void magnitude() {
        Vector v1 = new Vector(1.0, 0.0, 0.0);
        double m1 = v1.magnitude();
        assertTrue(compare(m1, 1.0));

        Vector v2 = new Vector(0.0, 1.0, 0.0);
        double m2 = v2.magnitude();
        assertTrue(compare(m2, 1.0));

        Vector v3 = new Vector(0.0, 0.0, 1.0);
        double m3 = v3.magnitude();
        assertTrue(compare(m3, 1.0));

        Vector v4 = new Vector(1.0, 2.0, 3.0);
        double m4 = v4.magnitude();
        assertTrue(compare(m4, 3.741657));

        Vector v5 = new Vector(-1.0, -2.0, -3.0);
        double m5 = v5.magnitude();
        assertTrue(compare(m5, 3.741657));
    }

    @Test
    public void normalise() {

        Vector v1 = new Vector(4.0, 0.0, 0.0);
        Vector v2 = v1.normalise();
        assertTrue(compare(v2.getX(), 1.0));
        assertTrue(compare(v2.getY(), 0.0));
        assertTrue(compare(v2.getZ(), 0.0));
        assertTrue(compare(v2.getW(), 0.0));

        v1 = new Vector(1.0, 2.0, 3.0);
        v2 = v1.normalise();
        assertTrue(compare(v2.getX(), 0.267261));
        assertTrue(compare(v2.getY(), 0.534522));
        assertTrue(compare(v2.getZ(), 0.801783));
        assertTrue(compare(v2.getW(), 0.0));
    }

    @Test
    public void dot() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(2.0, 3.0, 4.0);
        final double dot = Vector.dot(v1, v2);
        assertTrue(compare(dot, 20.0));

    }

    @Test
    public void cross() {
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector v2 = new Vector(2.0, 3.0, 4.0);

        Vector cross = Vector.cross(v1, v2);
        assertTrue(compare(cross.getX(), -1.0));
        assertTrue(compare(cross.getY(), 2.0));
        assertTrue(compare(cross.getZ(), -1.0));
        assertTrue(compare(cross.getW(), 0.0));

        cross = Vector.cross(v2, v1);
        assertTrue(compare(cross.getX(), 1.0));
        assertTrue(compare(cross.getY(), -2.0));
        assertTrue(compare(cross.getZ(), 1.0));
        assertTrue(compare(cross.getW(), 0.0));

    }

    private boolean compare(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }
}
