package com.badgersoft.raytrace.elements;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Vector extends Tuple {

    public Vector(BigDecimal x, BigDecimal y, BigDecimal z) {
        super(x, y, z, new BigDecimal("0.0"));
    }

    public Vector add(Vector other) {
        final Tuple tuple = super.add(other);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Vector subtract(Vector other) {
        final Tuple tuple = super.subtract(other);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public BigDecimal magnitude() {
        return new BigDecimal(Math.sqrt(
                Math.pow(getX().doubleValue(), 2) +
                Math.pow(getY().doubleValue(), 2) +
                Math.pow(getZ().doubleValue(), 2))).setScale(4, RoundingMode.HALF_EVEN);
    }

    public Vector normalise() {
        BigDecimal magnitude = magnitude();
        Tuple tuple = divide(magnitude);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public Vector multiply(BigDecimal multiplycand) {
        final Tuple tuple = super.multiply(multiplycand);
        return new Vector(tuple.getX(), tuple.getY(), tuple.getZ());
    }

    public static BigDecimal dot(Vector a, Vector b) {
        final BigDecimal d1 = a.getX().multiply(b.getX());
        final BigDecimal d2 = a.getY().multiply(b.getY());
        final BigDecimal d3 = a.getZ().multiply(b.getZ());
        return d1.add(d2).add(d3);
    }

    public static Vector cross(Vector a, Vector b) {
        final BigDecimal d1 = a.getY().multiply(b.getZ());
        final BigDecimal d2 = a.getZ().multiply(b.getY());
        final BigDecimal d3 = a.getZ().multiply(b.getX());
        final BigDecimal d4 = a.getX().multiply(b.getZ());
        final BigDecimal d5 = a.getX().multiply(b.getY());
        final BigDecimal d6 = a.getY().multiply(b.getX());
        return new Vector(
            d1.subtract(d2),
            d3.subtract(d4),
            d5.subtract(d6)
        );
    }
}
