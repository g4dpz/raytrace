package com.badgersoft.raytrace.elements;

public class Vector extends Tuple {

    public Vector(double x, double y, double z) {
        super(x, y, z, 0.0);
    }

    public Vector(Tuple tuple) {
        super(tuple.getX(), tuple.getY(), tuple.getZ(), 0.0);
    }

    public Vector add(Vector other) {
        final Tuple tuple = super.add(other);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Vector subtract(Vector other) {
        final Tuple tuple = super.subtract(other);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public double magnitude() {
        return Math.sqrt(
                Math.pow(getX(), 2) +
                Math.pow(getY(), 2) +
                Math.pow(getZ(), 2));
    }

    public Vector normalise() {
        double magnitude = magnitude();
        Tuple tuple = divide(magnitude);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Vector multiply(double multiplycand) {
        final Tuple tuple = super.multiply(multiplycand);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public static double dot(Vector a, Vector b) {
        final double d1 = a.getX() * b.getX();
        final double d2 = a.getY() * b.getY();
        final double d3 = a.getZ() * b.getZ();
        return d1 + d2 + d3;
    }

    public static Vector cross(Vector a, Vector b) {
        final double d1 = a.getY() * b.getZ();
        final double d2 = a.getZ() * b.getY();
        final double d3 = a.getZ() * b.getX();
        final double d4 = a.getX() * b.getZ();
        final double d5 = a.getX() * b.getY();
        final double d6 = a.getY() * b.getX();
        return new Vector(
            d1 - d2,
            d3 - d4,
            d5 - d6
        );
    }
}
