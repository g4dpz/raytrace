package com.badgersoft.raytrace.elements;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class ColourTest {

    @Test
    public void add() {
        Colour c1 = new Colour(new BigDecimal("0.9"), new BigDecimal("0.6"), new BigDecimal("0.75"));
        Colour c2 = new Colour(new BigDecimal("0.7"), new BigDecimal("0.1"), new BigDecimal("0.25"));
        Colour c3 = c1.add(c2);

        assertTrue(c3.getRed().compareTo(new BigDecimal("1.6")) == 0);
        assertTrue(c3.getGreen().compareTo(new BigDecimal("0.7")) == 0);
        assertTrue(c3.getBlue().compareTo(new BigDecimal("1.0")) == 0);
    }

    @Test
    public void subtract() {
        Colour c1 = new Colour(new BigDecimal("0.9"), new BigDecimal("0.6"), new BigDecimal("0.75"));
        Colour c2 = new Colour(new BigDecimal("0.7"), new BigDecimal("0.1"), new BigDecimal("0.25"));
        Colour c3 = c1.subtract(c2);

        assertTrue(c3.getRed().compareTo(new BigDecimal("0.2")) == 0);
        assertTrue(c3.getGreen().compareTo(new BigDecimal("0.5")) == 0);
        assertTrue(c3.getBlue().compareTo(new BigDecimal("0.5")) == 0);
    }

    @Test
    public void multiplyScalar() {
        Colour c1 = new Colour(new BigDecimal("0.9"), new BigDecimal("0.6"), new BigDecimal("0.75"));
        Colour c2 = c1.multiply(new BigDecimal("2.0"));

        assertTrue(c2.getRed().compareTo(new BigDecimal("1.8")) == 0);
        assertTrue(c2.getGreen().compareTo(new BigDecimal("1.2")) == 0);
        assertTrue(c2.getBlue().compareTo(new BigDecimal("1.5")) == 0);
    }

    @Test
    public void multiplyColurs() {
        Colour c1 = new Colour(new BigDecimal("1.0"), new BigDecimal("0.2"), new BigDecimal("0.4"));
        Colour c2 = new Colour(new BigDecimal("0.9"), new BigDecimal("1.0"), new BigDecimal("0.1"));
        Colour c3 = c1.multiply(c2);

        assertTrue(c3.getRed().compareTo(new BigDecimal("0.9")) == 0);
        assertTrue(c3.getGreen().compareTo(new BigDecimal("0.2")) == 0);
        assertTrue(c3.getBlue().compareTo(new BigDecimal("0.04")) == 0);
    }

    @Test
    public void hadamardProduct() {
        Colour c1 = new Colour(new BigDecimal("1.0"), new BigDecimal("0.5"), new BigDecimal("0.25"));
        Colour c2 = new Colour(new BigDecimal(".1"), new BigDecimal("0.2"), new BigDecimal("0.3"));
        Colour c3 = Colour.hadamardProduct(c1, c2);

        assertTrue(c3.getRed().compareTo(new BigDecimal("0.1")) == 0);
        assertTrue(c3.getGreen().compareTo(new BigDecimal("0.1")) == 0);
        assertTrue(c3.getBlue().compareTo(new BigDecimal("0.075")) == 0);
    }

}
