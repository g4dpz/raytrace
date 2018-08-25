package com.badgersoft.raytrace.elements;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Colour {

    private BigDecimal red;
    private BigDecimal green;
    private BigDecimal blue;

    public Colour() {
    }

    public Colour(BigDecimal red, BigDecimal green, BigDecimal blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public BigDecimal getRed() {
        return red;
    }

    public void setRed(BigDecimal red) {
        this.red = red;
    }

    public BigDecimal getGreen() {
        return green;
    }

    public void setGreen(BigDecimal green) {
        this.green = green;
    }

    public BigDecimal getBlue() {
        return blue;
    }

    public void setBlue(BigDecimal blue) {
        this.blue = blue;
    }

    public Colour add(Colour other) {

        return new Colour(
                red.add(other.red),
                green.add(other.green),
                blue.add(other.blue)
        );
    }

    public Colour subtract(Colour other) {

        return new Colour(
                red.subtract(other.red),
                green.subtract(other.green),
                blue.subtract(other.blue)
        );
    }

    public Colour multiply(BigDecimal multiplicand) {

        return new Colour(
                red.multiply(multiplicand).setScale(4, RoundingMode.HALF_EVEN),
                green.multiply(multiplicand).setScale(4, RoundingMode.HALF_EVEN),
                blue.multiply(multiplicand).setScale(4, RoundingMode.HALF_EVEN)
        );
    }

    public Colour multiply(Colour other) {

        return new Colour(
                red.multiply(other.red).setScale(4, RoundingMode.HALF_EVEN),
                green.multiply(other.green).setScale(4, RoundingMode.HALF_EVEN),
                blue.multiply(other.blue).setScale(4, RoundingMode.HALF_EVEN)
        );
    }

    public static Colour hadamardProduct(Colour c1, Colour c2) {
        BigDecimal red = c1.red.multiply(c2.red);
        BigDecimal green = c1.green.multiply(c2.green);
        BigDecimal blue = c1.blue.multiply(c2.blue);
        return new Colour(red, green, blue);
    }
}
