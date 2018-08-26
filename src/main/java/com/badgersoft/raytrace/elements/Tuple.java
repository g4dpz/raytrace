package com.badgersoft.raytrace.elements;

import java.io.Serializable;

public class Tuple implements Serializable {

    private static final double MINUS_1 = -1.0;

    private double x;
    private double y;
    private double z;
    private double w;

    public Tuple() {
    }

    public Tuple(double[] values) {
        this.x = values[0];
        this.y = values[1];
        this.z = values[2];
        this.w = values[3];
    }

    public Tuple(double x, double y, double z, double w) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Tuple(double[][] data) {
        this.x = data[0][0];
        this.y = data[1][0];
        this.z = data[2][0];
        this.w = data[3][0];
    }

    public boolean equals(Tuple other) {
        boolean result = compare(w, other.w);
        result = result && compare(x, other.x);
        result = result && compare(y, other.y);
        result = result && compare(z, other.z);
        return result;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public Tuple add(Tuple other) {

        return new Tuple(
                x += other.x,
                y += other.y,
                z += other.z,
                w += other.w
        );
    }

    public Tuple subtract(Tuple other) {

        return new Tuple(
                x -= other.x,
                y -= other.y,
                z -= other.z,
                w -= other.w
        );
    }

    public Tuple negate() {

        return new Tuple(
                x  *= MINUS_1,
                y  *= MINUS_1,
                z  *= MINUS_1,
                w  *= MINUS_1
        );
    }

    public Tuple multiply(double multiplicand) {

        final Tuple tuple = new Tuple(
                x  *= multiplicand,
                y  *= multiplicand,
                z  *= multiplicand,
                w  *= multiplicand
        );
        return tuple;
    }

    public Tuple divide(double divisor) {

        return new Tuple(
                x /= divisor,
                y /= divisor,
                z /= divisor,
                w /= divisor
        );
    }

    public double[] getDoubleValues() {
        return new double[] {getX(), getY(), getZ(),getW()};
    }

    public boolean compare(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }
}
