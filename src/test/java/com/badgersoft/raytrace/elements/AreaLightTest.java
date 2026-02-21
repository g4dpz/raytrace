package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class AreaLightTest {

    @Test
    public void testAreaLightCreation() {
        AreaLight light = new AreaLight(
            new Point(0, 0, 0),
            new Vector(2, 0, 0), 4,
            new Vector(0, 0, 1), 2,
            new Colour(1, 1, 1)
        );
        
        assertEquals(4, light.getUsteps());
        assertEquals(2, light.getVsteps());
        assertEquals(8, light.getSamples().size());
    }

    @Test
    public void testPointOnLight() {
        AreaLight light = new AreaLight(
            new Point(0, 0, 0),
            new Vector(2, 0, 0), 4,
            new Vector(0, 0, 1), 2,
            new Colour(1, 1, 1)
        );
        
        Point p = light.pointOnLight(0, 0);
        assertEquals(0.25, p.getX(), 0.01);
        assertEquals(0.0, p.getY(), 0.01);
        assertEquals(0.25, p.getZ(), 0.01);
        
        Point p2 = light.pointOnLight(1, 0);
        assertEquals(0.75, p2.getX(), 0.01);
        assertEquals(0.0, p2.getY(), 0.01);
        assertEquals(0.25, p2.getZ(), 0.01);
    }

    @Test
    public void testAreaLightUvecVvec() {
        AreaLight light = new AreaLight(
            new Point(0, 0, 0),
            new Vector(2, 0, 0), 4,
            new Vector(0, 0, 1), 2,
            new Colour(1, 1, 1)
        );
        
        assertEquals(0.5, light.getUvec().getX(), 0.01);
        assertEquals(0.5, light.getVvec().getZ(), 0.01);
    }
}
