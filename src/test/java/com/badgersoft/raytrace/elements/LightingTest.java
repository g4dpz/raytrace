package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class LightingTest {

    private boolean colorEquals(Colour a, Colour b) {
        return Math.abs(a.getRed() - b.getRed()) < 0.0001 &&
               Math.abs(a.getGreen() - b.getGreen()) < 0.0001 &&
               Math.abs(a.getBlue() - b.getBlue()) < 0.0001;
    }

    @Test
    public void testLightingWithEyeBetweenLightAndSurface() {
        Material m = new Material();
        Point position = new Point(0, 0, 0);
        Sphere sphere = new Sphere();
        
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, -10), new Colour(1, 1, 1));
        
        Colour result = Lighting.lighting(m, sphere, light, position, eyev, normalv, false);
        
        assertTrue(colorEquals(new Colour(1.9, 1.9, 1.9), result));
    }

    @Test
    public void testLightingWithEyeBetweenLightAndSurfaceEyeOffset45() {
        Material m = new Material();
        Point position = new Point(0, 0, 0);
        Sphere sphere = new Sphere();
        
        Vector eyev = new Vector(0, Math.sqrt(2)/2, -Math.sqrt(2)/2);
        Vector normalv = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, -10), new Colour(1, 1, 1));
        
        Colour result = Lighting.lighting(m, sphere, light, position, eyev, normalv, false);
        
        assertTrue(colorEquals(new Colour(1.0, 1.0, 1.0), result));
    }

    @Test
    public void testLightingWithEyeOppositeSurfaceLightOffset45() {
        Material m = new Material();
        Point position = new Point(0, 0, 0);
        Sphere sphere = new Sphere();
        
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 10, -10), new Colour(1, 1, 1));
        
        Colour result = Lighting.lighting(m, sphere, light, position, eyev, normalv, false);
        
        assertTrue(colorEquals(new Colour(0.7364, 0.7364, 0.7364), result));
    }

    @Test
    public void testLightingWithEyeInPathOfReflectionVector() {
        Material m = new Material();
        Point position = new Point(0, 0, 0);
        Sphere sphere = new Sphere();
        
        Vector eyev = new Vector(0, -Math.sqrt(2)/2, -Math.sqrt(2)/2);
        Vector normalv = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 10, -10), new Colour(1, 1, 1));
        
        Colour result = Lighting.lighting(m, sphere, light, position, eyev, normalv, false);
        
        assertTrue(colorEquals(new Colour(1.6364, 1.6364, 1.6364), result));
    }

    @Test
    public void testLightingWithLightBehindSurface() {
        Material m = new Material();
        Point position = new Point(0, 0, 0);
        Sphere sphere = new Sphere();
        
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, 10), new Colour(1, 1, 1));
        
        Colour result = Lighting.lighting(m, sphere, light, position, eyev, normalv, false);
        
        assertTrue(colorEquals(new Colour(0.1, 0.1, 0.1), result));
    }

    @Test
    public void testLightingWithSurfaceInShadow() {
        Material m = new Material();
        Point position = new Point(0, 0, 0);
        Sphere sphere = new Sphere();
        
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, -10), new Colour(1, 1, 1));
        boolean inShadow = true;
        
        Colour result = Lighting.lighting(m, sphere, light, position, eyev, normalv, inShadow);
        
        assertTrue(colorEquals(new Colour(0.1, 0.1, 0.1), result));
    }

    @Test
    public void testLightingWithPattern() {
        Material m = new Material();
        m.setPattern(new StripePattern(new Colour(1, 1, 1), new Colour(0, 0, 0)));
        m.setAmbient(1);
        m.setDiffuse(0);
        m.setSpecular(0);
        
        Sphere sphere = new Sphere();
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLight light = new PointLight(new Point(0, 0, -10), new Colour(1, 1, 1));
        
        Colour c1 = Lighting.lighting(m, sphere, light, new Point(0.9, 0, 0), eyev, normalv, false);
        Colour c2 = Lighting.lighting(m, sphere, light, new Point(1.1, 0, 0), eyev, normalv, false);
        
        assertTrue(colorEquals(new Colour(1, 1, 1), c1));
        assertTrue(colorEquals(new Colour(0, 0, 0), c2));
    }
}
