package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class UVPatternTest {

    @Test
    public void testSphericalMapping() {
        double[] uv1 = UVPattern.sphericalMap(new Point(0, 0, -1));
        assertEquals(0.0, uv1[0], 0.01);
        assertEquals(0.5, uv1[1], 0.01);
        
        double[] uv2 = UVPattern.sphericalMap(new Point(1, 0, 0));
        assertEquals(0.25, uv2[0], 0.01);
        assertEquals(0.5, uv2[1], 0.01);
        
        double[] uv3 = UVPattern.sphericalMap(new Point(0, 1, 0));
        // v should be at pole (either 0 or 1)
        assertTrue(uv3[1] < 0.1 || uv3[1] > 0.9);
    }

    @Test
    public void testPlanarMapping() {
        double[] uv1 = UVPattern.planarMap(new Point(0.25, 0, 0.5));
        assertEquals(0.25, uv1[0], 0.01);
        assertEquals(0.5, uv1[1], 0.01);
        
        double[] uv2 = UVPattern.planarMap(new Point(0.25, 0, -0.25));
        assertEquals(0.25, uv2[0], 0.01);
        assertEquals(0.75, uv2[1], 0.01);
    }

    @Test
    public void testCylindricalMapping() {
        double[] uv1 = UVPattern.cylindricalMap(new Point(0, 0, -1));
        assertEquals(0.0, uv1[0], 0.01);
        
        double[] uv2 = UVPattern.cylindricalMap(new Point(0, 0.5, -1));
        assertEquals(0.0, uv2[0], 0.01);
        assertEquals(0.5, uv2[1], 0.01);
    }

    @Test
    public void testCheckersUVPattern() {
        CheckersUV pattern = new CheckersUV(2, 2, 
            new Colour(1, 1, 1), 
            new Colour(0, 0, 0));
        
        Colour c1 = pattern.uvPatternAt(0.0, 0.0);
        assertEquals(new Colour(1, 1, 1), c1);
        
        Colour c2 = pattern.uvPatternAt(0.5, 0.0);
        assertEquals(new Colour(0, 0, 0), c2);
        
        Colour c3 = pattern.uvPatternAt(0.0, 0.5);
        assertEquals(new Colour(0, 0, 0), c3);
        
        Colour c4 = pattern.uvPatternAt(0.5, 0.5);
        assertEquals(new Colour(1, 1, 1), c4);
    }

    @Test
    public void testTextureMapWithSphericalMapping() {
        CheckersUV uvPattern = new CheckersUV(16, 8,
            new Colour(1, 1, 1),
            new Colour(0, 0, 0));
        TextureMap texture = new TextureMap(uvPattern, "spherical");
        
        // Test a point on the sphere
        Colour c = texture.patternAt(new Point(0, 0, -1));
        assertNotNull(c);
    }
}
