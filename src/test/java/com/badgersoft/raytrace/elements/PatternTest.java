package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class PatternTest {

    @Test
    public void testStripePattern() {
        StripePattern pattern = new StripePattern(new Colour(1, 1, 1), new Colour(0, 0, 0));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 1, 0)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 2, 0)));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 1)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 2)));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(0, 0, 0), pattern.patternAt(new Point(1, 0, 0)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(2, 0, 0)));
    }

    @Test
    public void testGradientPattern() {
        GradientPattern pattern = new GradientPattern(new Colour(1, 1, 1), new Colour(0, 0, 0));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(0.75, 0.75, 0.75), pattern.patternAt(new Point(0.25, 0, 0)));
        assertEquals(new Colour(0.5, 0.5, 0.5), pattern.patternAt(new Point(0.5, 0, 0)));
        assertEquals(new Colour(0.25, 0.25, 0.25), pattern.patternAt(new Point(0.75, 0, 0)));
    }

    @Test
    public void testRingPattern() {
        RingPattern pattern = new RingPattern(new Colour(1, 1, 1), new Colour(0, 0, 0));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(0, 0, 0), pattern.patternAt(new Point(1, 0, 0)));
        assertEquals(new Colour(0, 0, 0), pattern.patternAt(new Point(0, 0, 1)));
        assertEquals(new Colour(0, 0, 0), pattern.patternAt(new Point(0.708, 0, 0.708)));
    }

    @Test
    public void testCheckerPattern() {
        CheckerPattern pattern = new CheckerPattern(new Colour(1, 1, 1), new Colour(0, 0, 0));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0.99, 0, 0)));
        assertEquals(new Colour(0, 0, 0), pattern.patternAt(new Point(1.01, 0, 0)));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0.99, 0)));
        assertEquals(new Colour(0, 0, 0), pattern.patternAt(new Point(0, 1.01, 0)));
        
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0)));
        assertEquals(new Colour(1, 1, 1), pattern.patternAt(new Point(0, 0, 0.99)));
        assertEquals(new Colour(0, 0, 0), pattern.patternAt(new Point(0, 0, 1.01)));
    }

    @Test
    public void testPatternWithObjectTransformation() {
        Sphere sphere = new Sphere();
        sphere.setTransform(Matrix.scale(2, 2, 2));
        StripePattern pattern = new StripePattern(new Colour(1, 1, 1), new Colour(0, 0, 0));
        
        Colour c = pattern.patternAtShape(sphere, new Point(1.5, 0, 0));
        
        assertEquals(new Colour(1, 1, 1), c);
    }

    @Test
    public void testPatternWithPatternTransformation() {
        Sphere sphere = new Sphere();
        StripePattern pattern = new StripePattern(new Colour(1, 1, 1), new Colour(0, 0, 0));
        pattern.setTransform(Matrix.scale(2, 2, 2));
        
        Colour c = pattern.patternAtShape(sphere, new Point(1.5, 0, 0));
        
        assertEquals(new Colour(1, 1, 1), c);
    }
}
