package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointLightTest {

    @Test
    public void testPointLightCreation() {
        Colour intensity = new Colour(1, 1, 1);
        Point position = new Point(0, 0, 0);
        
        PointLight light = new PointLight(position, intensity);
        
        assertEquals(position, light.getPosition());
        assertEquals(intensity, light.getIntensity());
    }

    @Test
    public void testPointLightEquality() {
        Point position = new Point(0, 0, 0);
        Colour intensity = new Colour(1, 1, 1);
        
        PointLight light1 = new PointLight(position, intensity);
        PointLight light2 = new PointLight(position, intensity);
        
        assertEquals(light1, light2);
    }
}
