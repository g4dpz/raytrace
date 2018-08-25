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

    public Tuple(double[] values) {
        this.x = new BigDecimal(values[0]);
        this.y = new BigDecimal(values[1]);
        this.z = new BigDecimal(values[2]);
        this.w = new BigDecimal(values[3]);
    }

    public Tuple(double x, double y, double z, double w) {

        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.z = new BigDecimal(z);
        this.w = new BigDecimal(w);
    }

    public Tuple(double[][] data) {
        this.x = new BigDecimal(data[0][0]);
        this.y = new BigDecimal(data[1][0]);
        this.z = new BigDecimal(data[2][0]);
        this.w = new BigDecimal(data[3][0]);
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

    public double[] getDoubleValues() {
        return new double[] {getX().doubleValue(), getY().doubleValue(), getZ().doubleValue(),getW().doubleValue()};
    }

}
