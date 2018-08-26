package com.badgersoft.raytrace.elements;


public class Point extends Tuple {

    public Point(double x, double y, double z) {
        super(x, y, z, 1.0);
    }

    public Point(Tuple tuple) {
        super(tuple.getX(), tuple.getY(), tuple.getZ(), 1.0);
    }

    public Vector add(Point other) {
        final Tuple tuple = super.add(other);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Vector subtract(Point other) {
        final Tuple tuple = super.subtract(other);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Point add(Vector other) {
        final Tuple tuple = super.add(other);
        return new Point(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Point subtract(Vector other) {
        final Tuple tuple = super.subtract(other);
        return new Point(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public boolean equals(Point other) {
        boolean equals = compare(getX(), other.getX());
        equals = equals && compare(getY(), other.getY());
        equals = equals && compare(getZ(), other.getZ());
        return equals;
    }
}
