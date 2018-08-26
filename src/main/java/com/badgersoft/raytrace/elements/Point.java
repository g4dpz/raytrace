package com.badgersoft.raytrace.elements;

import java.math.BigDecimal;

public class Point extends Tuple {

    public Point(BigDecimal x, BigDecimal y, BigDecimal z) {
        super(x, y, z, new BigDecimal("1.0"));
    }

    public Point(double x, double y, double z) {
        super(new BigDecimal(x), new BigDecimal(y), new BigDecimal(z), new BigDecimal("1.0"));
    }

    public Point(Tuple tuple) {
        super(tuple.getX(), tuple.getY(), tuple.getZ(), new BigDecimal("1.0"));
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
}
