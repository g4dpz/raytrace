package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SphereTest {

    @Test
    public void intersect() {
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        double[] intersects = Sphere.intersects(s, r);
        assertEquals(2, intersects.length);
        compare(4.0, intersects[0]);
        compare(6.0, intersects[0]);
    }

    @Test
    public void intersectAtTangent() {
        Ray r = new Ray(new Point(0, 1, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        double[] intersects = Sphere.intersects(s, r);
        assertEquals(2, intersects.length);
        compare(5.0, intersects[0]);
        compare(5.0, intersects[0]);
    }

    @Test
    public void noIntersect() {
        Ray r = new Ray(new Point(0, 2, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        double[] intersects = Sphere.intersects(s, r);
        assertEquals(0, intersects.length);
    }

    @Test
    public void rayStartsInsideSphere() {
        Ray r = new Ray(new Point(0,0,0), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        double[] intersects = Sphere.intersects(s, r);
        assertEquals(2, intersects.length);
        compare(-1.0, intersects[0]);
        compare(1.0, intersects[0]);
    }

    @Test
    public void sphareBehindRay() {
        Ray r = new Ray(new Point(0,0,5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        double[] intersects = Sphere.intersects(s, r);
        assertEquals(2, intersects.length);
        compare(-6.0, intersects[0]);
        compare(4.0, intersects[0]);
    }

    private boolean compare(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }

}
