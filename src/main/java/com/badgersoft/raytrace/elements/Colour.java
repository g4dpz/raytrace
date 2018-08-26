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

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public Colour add(Colour other) {

        return new Colour(
                red += other.red,
                green += other.green,
                blue += other.blue);
    }

    public Colour subtract(Colour other) {

        return new Colour(
                red -= other.red,
                green -= other.green,
                blue-= other.blue
        );
    }

    public Colour multiply(double multiplicand) {

        return new Colour(
                red *= multiplicand,
                green *= multiplicand,
                blue *= multiplicand
        );
    }

    public Colour multiply(Colour other) {

        return new Colour(
                red *= other.red,
                green *= other.green,
                blue *= other.blue
        );
    }

    public static Colour hadamardProduct(Colour c1, Colour c2) {
        double red = c1.red *= c2.red;
        double green = c1.green *= c2.green;
        double blue = c1.blue *= c2.blue;
        return new Colour(red, green, blue);
    }
}
