package com.badgersoft.raytrace.elements;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class TorusTest {

    @Test
    public void testTorusCreation() {
        Torus torus = new Torus();
        assertEquals(1.0, torus.getMajorRadius(), 0.0001);
        assertEquals(0.25, torus.getMinorRadius(), 0.0001);
    }

    @Test
    public void testTorusWithCustomRadii() {
        Torus torus = new Torus(2.0, 0.5);
        assertEquals(2.0, torus.getMajorRadius(), 0.0001);
        assertEquals(0.5, torus.getMinorRadius(), 0.0001);
    }

    @Test
    public void testRayMissesTorus() {
        Torus torus = new Torus(1.0, 0.25);
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 1, 0));
        List<Intersection> xs = torus.intersect(ray);
        assertEquals(0, xs.size());
    }

    @Test
    public void testRayIntersectsTorus() {
        Torus torus = new Torus(1.0, 0.25);
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        List<Intersection> xs = torus.intersect(ray);
        assertTrue(xs.size() > 0);
    }

    @Test
    public void testTorusNormal() {
        Torus torus = new Torus(1.0, 0.25);
        // Point on outer edge of torus
        Point point = new Point(1.25, 0, 0);
        Vector normal = torus.normalAt(point);
        
        // Normal should point outward
        assertTrue(normal.getX() > 0);
        
        // Normal should be normalized
        assertEquals(1.0, normal.magnitude(), 0.0001);
    }

    @Test
    public void testTorusBoundingBox() {
        Torus torus = new Torus(1.0, 0.25);
        BoundingBox bounds = torus.getBounds();
        
        // Outer radius is 1.25 (major + minor)
        assertEquals(-1.25, bounds.getMin().getX(), 0.0001);
        assertEquals(1.25, bounds.getMax().getX(), 0.0001);
        
        // Height is 2 * minor radius
        assertEquals(-0.25, bounds.getMin().getY(), 0.0001);
        assertEquals(0.25, bounds.getMax().getY(), 0.0001);
    }

    @Test
    public void testTransformedTorus() {
        Torus torus = new Torus(1.0, 0.25);
        torus.setTransform(Matrix.scale(2, 2, 2));
        
        Ray ray = new Ray(new Point(0, 0, -10), new Vector(0, 0, 1));
        List<Intersection> xs = torus.intersect(ray);
        
        // Should still intersect after scaling
        assertTrue(xs.size() > 0);
    }
}
