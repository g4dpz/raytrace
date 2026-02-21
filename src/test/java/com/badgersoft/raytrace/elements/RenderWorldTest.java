package com.badgersoft.raytrace.elements;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class RenderWorldTest {

    private boolean colorEquals(Colour a, Colour b) {
        return Math.abs(a.getRed() - b.getRed()) < 0.01 &&
               Math.abs(a.getGreen() - b.getGreen()) < 0.01 &&
               Math.abs(a.getBlue() - b.getBlue()) < 0.01;
    }

    @Test
    public void testCreatingWorld() {
        RenderWorld w = new RenderWorld();
        
        assertEquals(0, w.getObjects().size());
        assertEquals(0, w.getLights().size());
    }

    @Test
    public void testDefaultWorld() {
        PointLight light = new PointLight(new Point(-10, 10, -10), new Colour(1, 1, 1));
        
        Sphere s1 = new Sphere();
        Material m = new Material();
        m.setColor(new Colour(0.8, 1.0, 0.6));
        m.setDiffuse(0.7);
        m.setSpecular(0.2);
        s1.setMaterial(m);
        
        Sphere s2 = new Sphere();
        s2.setTransform(Matrix.scale(1, 1, 1));
        
        RenderWorld w = new RenderWorld();
        w.addLight(light);
        w.addObject(s1);
        w.addObject(s2);
        
        assertEquals(1, w.getLights().size());
        assertEquals(2, w.getObjects().size());
    }

    @Test
    public void testIntersectWorldWithRay() {
        RenderWorld w = createDefaultWorld();
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        
        List<Intersection> xs = w.intersectWorld(r);
        
        assertEquals(4, xs.size());
        assertEquals(4, xs.get(0).getT(), 0.0001);
        assertEquals(4.5, xs.get(1).getT(), 0.0001);
        assertEquals(5.5, xs.get(2).getT(), 0.0001);
        assertEquals(6, xs.get(3).getT(), 0.0001);
    }

    @Test
    public void testShadeHit() {
        RenderWorld w = createDefaultWorld();
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere shape = (Sphere) w.getObjects().get(0);
        Intersection i = new Intersection(4, shape);
        
        Computation comps = w.prepareComputations(i, r, List.of(i));
        Colour c = w.shadeHit(comps, 5);
        
        assertTrue(colorEquals(new Colour(0.38066, 0.47583, 0.2855), c));
    }

    @Test
    public void testColorWhenRayMisses() {
        RenderWorld w = createDefaultWorld();
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 1, 0));
        
        Colour c = w.colorAt(r, 5);
        
        assertTrue(colorEquals(new Colour(0, 0, 0), c));
    }

    @Test
    public void testColorWhenRayHits() {
        RenderWorld w = createDefaultWorld();
        Ray r = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        
        Colour c = w.colorAt(r, 5);
        
        assertTrue(colorEquals(new Colour(0.38066, 0.47583, 0.2855), c));
    }

    @Test
    public void testNoShadowWhenNothingCollinearWithPointAndLight() {
        RenderWorld w = createDefaultWorld();
        Point p = new Point(0, 10, 0);
        
        assertFalse(w.isShadowed(p, w.getLights().get(0)));
    }

    @Test
    public void testShadowWhenObjectBetweenPointAndLight() {
        RenderWorld w = createDefaultWorld();
        Point p = new Point(10, -10, 10);
        
        assertTrue(w.isShadowed(p, w.getLights().get(0)));
    }

    @Test
    public void testNoShadowWhenObjectBehindLight() {
        RenderWorld w = createDefaultWorld();
        Point p = new Point(-20, 20, -20);
        
        assertFalse(w.isShadowed(p, w.getLights().get(0)));
    }

    @Test
    public void testNoShadowWhenObjectBehindPoint() {
        RenderWorld w = createDefaultWorld();
        Point p = new Point(-2, 2, -2);
        
        assertFalse(w.isShadowed(p, w.getLights().get(0)));
    }

    private RenderWorld createDefaultWorld() {
        PointLight light = new PointLight(new Point(-10, 10, -10), new Colour(1, 1, 1));
        
        Sphere s1 = new Sphere();
        Material m = new Material();
        m.setColor(new Colour(0.8, 1.0, 0.6));
        m.setDiffuse(0.7);
        m.setSpecular(0.2);
        s1.setMaterial(m);
        
        Sphere s2 = new Sphere();
        s2.setTransform(Matrix.scale(0.5, 0.5, 0.5));
        
        RenderWorld w = new RenderWorld();
        w.addLight(light);
        w.addObject(s1);
        w.addObject(s2);
        
        return w;
    }
}
