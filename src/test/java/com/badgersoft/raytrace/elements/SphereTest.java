package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SphereTest {

    @Test
    public void defaultTransform() {
        Sphere s = new Sphere();
        Matrix m1 = s.getTransform();
        Matrix m2 = new Matrix(new double[][]{{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}});
        assertTrue(m2.equals(m1));
    }

    @Test
    public void setTransform() {
        Sphere s = new Sphere();
        Matrix t = Matrix.translation(2, 3, 4);
        s.setTransform(t);
        assertTrue(t.equals(s.getTransform()));
    }
}
