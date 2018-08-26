package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ColourTest {

    @Test
    public void add() {
        Colour c1 = new Colour(0.9, 0.6, 0.75);
        Colour c2 = new Colour(0.7, 0.1, 0.25);
        Colour c3 = c1.add(c2);

        assertTrue(compare(c3.getRed(), 1.6));
        assertTrue(compare(c3.getGreen(), 0.7));
        assertTrue(compare(c3.getBlue(), 1.0));
    }

    @Test
    public void subtract() {
        Colour c1 = new Colour(0.9, 0.6, 0.75);
        Colour c2 = new Colour(0.7, 0.1, 0.25);
        Colour c3 = c1.subtract(c2);

        assertTrue(compare(c3.getRed(), 0.2));
        assertTrue(compare(c3.getGreen(), 0.5));
        assertTrue(compare(c3.getBlue(), 0.5));
    }

    @Test
    public void multiplyScalar() {
        Colour c1 = new Colour(0.9, 0.6, 0.75);
        Colour c2 = c1.multiply(2.0);

        assertTrue(compare(c2.getRed(), 1.8));
        assertTrue(compare(c2.getGreen(), 1.2));
        assertTrue(compare(c2.getBlue(), 1.5));
    }

    @Test
    public void multiplyColurs() {
        Colour c1 = new Colour(1.0, 0.2, 0.4);
        Colour c2 = new Colour(0.9, 1.0, 0.1);
        Colour c3 = c1.multiply(c2);

        assertTrue(compare(c3.getRed(), 0.9));
        assertTrue(compare(c3.getGreen(), 0.2));
        assertTrue(compare(c3.getBlue(), 0.04));
    }

    @Test
    public void hadamardProduct() {
        Colour c1 = new Colour(1.0, 0.5, 0.25);
        Colour c2 = new Colour(.1, 0.2, 0.3);
        Colour c3 = Colour.hadamardProduct(c1, c2);

        assertTrue(compare(c3.getRed(), 0.1));
        assertTrue(compare(c3.getGreen(), 0.1));
        assertTrue(compare(c3.getBlue(), 0.075));
    }
    
    private boolean compare(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }

}
