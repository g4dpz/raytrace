package com.badgersoft.raytrace.elements;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tuple implements Serializable {

    private static final BigDecimal MINUS_1 = new BigDecimal("-1.0");

    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal z;
    private BigDecimal w;

    public Tuple() {
    }

    public Tuple(BigDecimal x, BigDecimal y, BigDecimal z, BigDecimal w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public boolean equals(Tuple other) {
        boolean result = w.compareTo(other.w) == 0;
        result = result && x.compareTo(other.x) == 0;
        result = result && y.compareTo(other.y) == 0;
        result = result && z.compareTo(other.z) == 0;
        return result;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getZ() {
        return z;
    }

    public void setZ(BigDecimal z) {
        this.z = z;
    }

    public BigDecimal getW() {
        return w;
    }

    public void setW(BigDecimal w) {
        this.w = w;
    }

    public Tuple add(Tuple other) {

        return new Tuple(
                x.add(other.x),
                y.add(other.y),
                z.add(other.z),
                w.add(other.w)
        );
    }

    public Tuple subtract(Tuple other) {

        return new Tuple(
                x.subtract(other.x),
                y.subtract(other.y),
                z.subtract(other.z),
                w.subtract(other.w)
        );
    }

    public Tuple negate() {

        return new Tuple(
                x.multiply(MINUS_1),
                y.multiply(MINUS_1),
                z.multiply(MINUS_1),
                w.multiply(MINUS_1)
        );
    }

    public Tuple multiply(BigDecimal multiplicand) {

        final Tuple tuple = new Tuple(
                x.multiply(multiplicand).setScale(4, RoundingMode.HALF_EVEN),
                y.multiply(multiplicand).setScale(4, RoundingMode.HALF_EVEN),
                z.multiply(multiplicand).setScale(4, RoundingMode.HALF_EVEN),
                w.multiply(multiplicand).setScale(4, RoundingMode.HALF_EVEN)
        );
        return tuple;
    }

    public Tuple divide(BigDecimal divisor) {

        return new Tuple(
                x.divide(divisor,4 , RoundingMode.HALF_EVEN),
                y.divide(divisor,4 , RoundingMode.HALF_EVEN),
                z.divide(divisor,4 , RoundingMode.HALF_EVEN),
                w.divide(divisor,4 , RoundingMode.HALF_EVEN)
        );
    }

}
