package com.badgersoft.raytrace.elements;

public class Colour {

    private double red;
    private double green;
    private double blue;

    public Colour() {
    }

    public Colour(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public Colour add(Colour other) {
        return new Colour(
                red + other.red,
                green + other.green,
                blue + other.blue);
    }

    public Colour subtract(Colour other) {
        return new Colour(
                red - other.red,
                green - other.green,
                blue - other.blue
        );
    }

    public Colour multiply(double multiplicand) {
        return new Colour(
                red * multiplicand,
                green * multiplicand,
                blue * multiplicand
        );
    }

    public Colour multiply(Colour other) {
        return new Colour(
                red * other.red,
                green * other.green,
                blue * other.blue
        );
    }

    public static Colour hadamardProduct(Colour c1, Colour c2) {
        double red = c1.red *= c2.red;
        double green = c1.green *= c2.green;
        double blue = c1.blue *= c2.blue;
        return new Colour(red, green, blue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Colour)) return false;
        Colour other = (Colour) obj;
        return Math.abs(red - other.red) < 0.0001 &&
               Math.abs(green - other.green) < 0.0001 &&
               Math.abs(blue - other.blue) < 0.0001;
    }

    @Override
    public int hashCode() {
        long redBits = Double.doubleToLongBits(red);
        long greenBits = Double.doubleToLongBits(green);
        long blueBits = Double.doubleToLongBits(blue);
        return (int)(redBits ^ greenBits ^ blueBits);
    }
}
