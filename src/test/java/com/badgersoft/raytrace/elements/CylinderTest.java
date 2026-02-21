package com.badgersoft.raytrace.elements;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class CylinderTest {

    @Test
    public void testRayMissesCylinder() {
        Cylinder cyl = new Cylinder();
        
        Ray r1 = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
        List<Intersection> xs1 = cyl.intersect(r1);
        assertEquals(0, xs1.size());
        
        Ray r2 = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        List<Intersection> xs2 = cyl.intersect(r2);
        assertEquals(0, xs2.size());
        
        Ray r3 = new Ray(new Point(0, 0, -5), new Vector(1, 1, 1));
        List<Intersection> xs3 = cyl.intersect(r3);
        assertEquals(0, xs3.size());
    }

    @Test
    public void testRayStrikesCylinder() {
        Cylinder cyl = new Cylinder();
        
        Ray r = new Ray(new Point(1, 0, -5), new Vector(0, 0, 1));
        List<Intersection> xs = cyl.intersect(r);
        
        assertEquals(2, xs.size());
        assertEquals(5.0, xs.get(0).getT(), 0.0001);
        assertEquals(5.0, xs.get(1).getT(), 0.0001);
    }

    @Test
    public void testNormalOnCylinder() {
        Cylinder cyl = new Cylinder();
        
        Vector n1 = cyl.normalAt(new Point(1, 0, 0));
        assertEquals(1.0, n1.getX(), 0.0001);
        assertEquals(0.0, n1.getY(), 0.0001);
        assertEquals(0.0, n1.getZ(), 0.0001);
        
        Vector n2 = cyl.normalAt(new Point(0, 5, -1));
        assertEquals(0.0, n2.getX(), 0.0001);
        assertEquals(0.0, n2.getY(), 0.0001);
        assertEquals(-1.0, n2.getZ(), 0.0001);
    }

    @Test
    public void testTruncatedCylinder() {
        Cylinder cyl = new Cylinder();
        cyl.setMinimum(1);
        cyl.setMaximum(2);
        
        Ray r1 = new Ray(new Point(0, 1.5, 0), new Vector(0.1, 1, 0));
        List<Intersection> xs1 = cyl.intersect(r1);
        assertEquals(0, xs1.size());
        
        Ray r2 = new Ray(new Point(0, 3, -5), new Vector(0, 0, 1));
        List<Intersection> xs2 = cyl.intersect(r2);
        assertEquals(0, xs2.size());
        
        Ray r3 = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        List<Intersection> xs3 = cyl.intersect(r3);
        assertEquals(0, xs3.size());
        
        Ray r4 = new Ray(new Point(0, 2, -5), new Vector(0, 0, 1));
        List<Intersection> xs4 = cyl.intersect(r4);
        assertEquals(0, xs4.size());
        
        Ray r5 = new Ray(new Point(0, 1, -5), new Vector(0, 0, 1));
        List<Intersection> xs5 = cyl.intersect(r5);
        assertEquals(0, xs5.size());
        
        Ray r6 = new Ray(new Point(0, 1.5, -2), new Vector(0, 0, 1));
        List<Intersection> xs6 = cyl.intersect(r6);
        assertEquals(2, xs6.size());
    }

    @Test
    public void testClosedCylinder() {
        Cylinder cyl = new Cylinder();
        cyl.setMinimum(1);
        cyl.setMaximum(2);
        cyl.setClosed(true);
        
        Ray r1 = new Ray(new Point(0, 3, 0), new Vector(0, -1, 0));
        List<Intersection> xs1 = cyl.intersect(r1);
        assertEquals(2, xs1.size());
        
        Ray r2 = new Ray(new Point(0, 3, -2), new Vector(0, -1, 2));
        List<Intersection> xs2 = cyl.intersect(r2);
        assertEquals(2, xs2.size());
        
        Ray r3 = new Ray(new Point(0, 0, -2), new Vector(0, 1, 2));
        List<Intersection> xs3 = cyl.intersect(r3);
        assertEquals(2, xs3.size());
    }
}
