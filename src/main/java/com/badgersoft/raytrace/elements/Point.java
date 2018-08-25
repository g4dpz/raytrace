package com.badgersoft.raytrace.elements;

import java.math.BigDecimal;

public class Point extends Tuple {

    public Point(BigDecimal x, BigDecimal y, BigDecimal z) {
        super(x, y, z, new BigDecimal("1.0"));
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
