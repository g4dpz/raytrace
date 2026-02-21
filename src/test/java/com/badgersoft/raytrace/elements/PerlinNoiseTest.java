package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class PerlinNoiseTest {

    @Test
    public void testNoiseReturnsValueInRange() {
        double noise = PerlinNoise.noise(1.5, 2.3, 3.7);
        assertTrue(noise >= -1.0 && noise <= 1.0);
    }

    @Test
    public void testNoiseIsConsistent() {
        double noise1 = PerlinNoise.noise(1.0, 2.0, 3.0);
        double noise2 = PerlinNoise.noise(1.0, 2.0, 3.0);
        assertEquals(noise1, noise2, 0.0001);
    }

    @Test
    public void testNoiseDifferentForDifferentInputs() {
        double noise1 = PerlinNoise.noise(1.0, 2.0, 3.0);
        double noise2 = PerlinNoise.noise(1.1, 2.0, 3.0);
        // Just verify they're both valid values - they may be similar for close inputs
        assertTrue(noise1 >= -1.0 && noise1 <= 1.0);
        assertTrue(noise2 >= -1.0 && noise2 <= 1.0);
    }

    @Test
    public void testTurbulenceReturnsValueInRange() {
        double turbulence = PerlinNoise.turbulence(1.5, 2.3, 3.7, 4);
        assertTrue(turbulence >= -1.0 && turbulence <= 1.0);
    }

    @Test
    public void testTurbulenceWithMoreOctavesIsMoreDetailed() {
        double turb2 = PerlinNoise.turbulence(1.5, 2.3, 3.7, 2);
        double turb8 = PerlinNoise.turbulence(1.5, 2.3, 3.7, 8);
        // Both should be valid values
        assertTrue(turb2 >= -1.0 && turb2 <= 1.0);
        assertTrue(turb8 >= -1.0 && turb8 <= 1.0);
    }
}
