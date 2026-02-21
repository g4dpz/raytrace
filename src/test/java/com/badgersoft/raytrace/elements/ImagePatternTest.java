package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class ImagePatternTest {

    @Test
    public void testImagePatternCreation() {
        ImagePattern pattern = new ImagePattern(10, 10);
        assertEquals(10, pattern.getWidth());
        assertEquals(10, pattern.getHeight());
    }

    @Test
    public void testSetAndGetPixel() {
        ImagePattern pattern = new ImagePattern(10, 10);
        Colour color = new Colour(1, 0, 0);
        pattern.setPixel(5, 5, color);
        
        Colour retrieved = pattern.getPixel(5, 5);
        assertEquals(color, retrieved);
    }

    @Test
    public void testUVPatternAt() {
        ImagePattern pattern = new ImagePattern(10, 10);
        pattern.setPixel(0, 0, new Colour(1, 0, 0));
        pattern.setPixel(9, 9, new Colour(0, 1, 0));
        
        Colour c1 = pattern.uvPatternAt(0.0, 1.0);
        assertEquals(1.0, c1.getRed(), 0.1);
        
        Colour c2 = pattern.uvPatternAt(1.0, 0.0);
        assertEquals(1.0, c2.getGreen(), 0.1);
    }

    @Test
    public void testCreateTestPattern() {
        ImagePattern pattern = ImagePattern.createTestPattern(100, 100);
        assertEquals(100, pattern.getWidth());
        assertEquals(100, pattern.getHeight());
        
        // Check that colors vary across the image
        Colour c1 = pattern.getPixel(0, 0);
        Colour c2 = pattern.getPixel(99, 99);
        assertNotEquals(c1.getRed(), c2.getRed(), 0.01);
    }

    @Test
    public void testOutOfBoundsReturnsBlack() {
        ImagePattern pattern = new ImagePattern(10, 10);
        Colour c = pattern.getPixel(-1, -1);
        assertEquals(new Colour(0, 0, 0), c);
        
        Colour c2 = pattern.getPixel(100, 100);
        assertEquals(new Colour(0, 0, 0), c2);
    }
}
