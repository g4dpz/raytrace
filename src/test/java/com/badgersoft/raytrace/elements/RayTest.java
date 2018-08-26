package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RayTest {

    @Test
    public void position() {
        Ray r1 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p1 = Ray.position(r1, 0);
        assertTrue(new Point(2, 3, 4).equals(p1));
        Ray r2 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p2 = Ray.position(r2, 1);
        assertTrue(new Point(3, 3, 4).equals(p2));
        Ray r3 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p3 = Ray.position(r3, -1);
        assertTrue(new Point(1, 3, 4).equals(p3));
        Ray r4 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p4 = Ray.position(r4, 2.5);
        assertTrue(new Point(4.5, 3, 4).equals(p4));
    }
}
