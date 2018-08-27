package com.badgersoft.raytrace.elements;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RayTest {

    @Test
    public void position() {
        Ray r1 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p1 = r1.position(0);
        assertTrue(new Point(2, 3, 4).equals(p1));

        Ray r2 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p2 = r2.position(1);
        assertTrue(new Point(3, 3, 4).equals(p2));

        Ray r3 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p3 = r3.position(-1);
        assertTrue(new Point(1, 3, 4).equals(p3));

        Ray r4 = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        Point p4 = r4.position(2.5);
        assertTrue(new Point(4.5, 3, 4).equals(p4));
    }

    @Test
    public void intersect() {
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        List<Intersection> intersects = r.intersect(s);
        assertEquals(2, intersects.size());
        assertTrue(compare(4.0, intersects.get(0).getT()));
        assertEquals(s, intersects.get(0).getScreenObject());
        assertTrue(compare(6.0, intersects.get(1).getT()));
        assertEquals(s, intersects.get(1).getScreenObject());
    }

    @Test
    public void intersectAtTangent() {
        Ray r = new Ray(new Point(0, 1, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        List<Intersection> intersects = r.intersect(s);
        assertEquals(2, intersects.size());
        assertTrue(compare(5.0, intersects.get(0).getT()));
        assertEquals(s, intersects.get(0).getScreenObject());
        assertTrue(compare(5.0, intersects.get(1).getT()));
        assertEquals(s, intersects.get(1).getScreenObject());
    }

    @Test
    public void noIntersect() {
        Ray r = new Ray(new Point(0, 2, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        List<Intersection> intersects = r.intersect(s);
        assertEquals(0, intersects.size());
    }

    @Test
    public void rayStartsInsideSphere() {
        Ray r = new Ray(new Point(0,0,0), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        List<Intersection> intersects = r.intersect(s);
        assertEquals(2, intersects.size());
        assertTrue(compare(-1.0, intersects.get(0).getT()));
        assertEquals(s, intersects.get(0).getScreenObject());
        assertTrue(compare(1.0, intersects.get(1).getT()));
        assertEquals(s, intersects.get(1).getScreenObject());
    }

    @Test
    public void sphereBehindRay() {
        Ray r = new Ray(new Point(0,0,5), new Vector(0, 0, 1));
        Sphere s = new Sphere();

        List<Intersection> intersects = r.intersect(s);
        assertEquals(2, intersects.size());
        assertTrue(compare(-6.0, intersects.get(0).getT()));
        assertEquals(s, intersects.get(0).getScreenObject());
        assertTrue(compare(-4.0, intersects.get(1).getT()));
        assertEquals(s, intersects.get(1).getScreenObject());
    }

    @Test
    public void translate() {
        Ray r1 = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        Matrix m = Matrix.translation(3,4,5);
        Ray r2 = r1.transform(m);
        assertTrue(new Point(4, 6, 8).equals(r2.getOrigin()));
        assertTrue(new Vector(0, 1, 0).equals(r2.getDirection()));
    }

    @Test
    public void scale() {
        Ray r1 = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        Matrix m = Matrix.scale(2, 3, 4);
        Ray r2 = r1.transform(m);
        assertTrue(new Point(2, 6, 12).equals(r2.getOrigin()));
        assertTrue(new Vector(0, 3, 0).equals(r2.getDirection()));
    }

    @Test
    public void intersectingScaledSphereWithRay() {
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(Matrix.scale(2, 2, 2));
        final List<Intersection> intersects = r.intersect(s);
        assertEquals(2, intersects.size());
        assertTrue(compare(3.0, intersects.get(0).getT()));
        assertTrue(compare(7.0, intersects.get(1).getT()));
    }

    @Test
    public void intersectingTranslatedSphereWithRay() {
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(Matrix.translation(5, 0, 0));
        final List<Intersection> intersects = r.intersect(s);
        assertEquals(0, intersects.size());
    }

    private boolean compare(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }
}
