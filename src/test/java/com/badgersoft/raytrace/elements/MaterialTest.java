package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class MaterialTest {

    @Test
    public void testDefaultMaterial() {
        Material m = new Material();
        
        assertEquals(new Colour(1, 1, 1), m.getColor());
        assertEquals(0.1, m.getAmbient(), 0.0001);
        assertEquals(0.9, m.getDiffuse(), 0.0001);
        assertEquals(0.9, m.getSpecular(), 0.0001);
        assertEquals(200.0, m.getShininess(), 0.0001);
        assertEquals(0.0, m.getReflective(), 0.0001);
        assertEquals(0.0, m.getTransparency(), 0.0001);
        assertEquals(1.0, m.getRefractiveIndex(), 0.0001);
    }

    @Test
    public void testMaterialProperties() {
        Material m = new Material();
        m.setAmbient(0.2);
        m.setDiffuse(0.7);
        m.setSpecular(0.5);
        m.setShininess(100.0);
        
        assertEquals(0.2, m.getAmbient(), 0.0001);
        assertEquals(0.7, m.getDiffuse(), 0.0001);
        assertEquals(0.5, m.getSpecular(), 0.0001);
        assertEquals(100.0, m.getShininess(), 0.0001);
    }

    @Test
    public void testMaterialWithPattern() {
        Material m = new Material();
        Pattern pattern = new StripePattern(new Colour(1, 1, 1), new Colour(0, 0, 0));
        m.setPattern(pattern);
        
        assertNotNull(m.getPattern());
        assertEquals(pattern, m.getPattern());
    }

    @Test
    public void testReflectiveAndTransparentProperties() {
        Material m = new Material();
        m.setReflective(0.5);
        m.setTransparency(0.8);
        m.setRefractiveIndex(1.5);
        
        assertEquals(0.5, m.getReflective(), 0.0001);
        assertEquals(0.8, m.getTransparency(), 0.0001);
        assertEquals(1.5, m.getRefractiveIndex(), 0.0001);
    }
}
