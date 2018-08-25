package com.badgersoft.raytrace.elements;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class VectorTest {

    @Test
    public void create() {
        Vector v = new Vector(new BigDecimal("1.1"), new BigDecimal("2.22"), new BigDecimal("3.333"));
        assertTrue(v.getX().compareTo(new BigDecimal("1.1")) == 0);
        assertTrue(v.getY().compareTo(new BigDecimal("2.22")) == 0);
        assertTrue(v.getZ().compareTo(new BigDecimal("3.333")) == 0);
        assertTrue(v.getW().compareTo(new BigDecimal("0.0")) == 0);
    }

    @Test
    public void equals() {
        Vector v1 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        Vector v2 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        assertTrue(v1.equals(v2));
    }

    @Test
    public void notEquals() {
        Vector v1 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        Vector v2 = new Vector(new BigDecimal("2.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        assertTrue(!v1.equals(v2));
        Vector v3 = new Vector(new BigDecimal("1.0"), new BigDecimal("3.0"), new BigDecimal("3.0"));
        assertTrue(!v1.equals(v3));
        Vector v4 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("4.0"));
        assertTrue(!v1.equals(v4));
    }

    @Test
    public void vectorAdd() {
        Vector v1 = new Vector(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"));
        Vector v2 = new Vector(new BigDecimal("-2.0"), new BigDecimal("3.0"), new BigDecimal("1.0"));
        Vector v3 = v1.add(v2);
        assertTrue(v3.getX().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(v3.getY().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(v3.getZ().compareTo(new BigDecimal("6.0")) == 0);
        assertTrue(v3.getW().compareTo(new BigDecimal("0.0")) == 0);

    }

    @Test
    public void vectorSubtract() {
        Vector v1 = new Vector(new BigDecimal("0.0"), new BigDecimal("0.0"), new BigDecimal("0.0"));
        Vector v2 = new Vector(new BigDecimal("-2.0"), new BigDecimal("3.0"), new BigDecimal("1.0"));
        Vector v3 = v1.subtract(v2);
        assertTrue(v3.getX().compareTo(new BigDecimal("2.0")) == 0);
        assertTrue(v3.getY().compareTo(new BigDecimal("-3.0")) == 0);
        assertTrue(v3.getZ().compareTo(new BigDecimal("-1.0")) == 0);
        assertTrue(v3.getW().compareTo(new BigDecimal("0.0")) == 0);

    }

    @Test
    public void multiply() {
        Vector t1 = new Vector(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"));
        Vector t2 = t1.multiply(new BigDecimal(-2.1));
        assertTrue(t2.getX().compareTo(new BigDecimal("-6.3000")) == 0);
        assertTrue(t2.getY().compareTo(new BigDecimal("4.2000")) == 0);
        assertTrue(t2.getZ().compareTo(new BigDecimal("-10.5000")) == 0);
        assertTrue(t2.getW().compareTo(new BigDecimal("0.0")) == 0);
    }

    @Test
    public void magnitude() {
        Vector v1 = new Vector(new BigDecimal("1.0"), new BigDecimal("0.0"), new BigDecimal("0.0"));
        BigDecimal m1 = v1.magnitude();
        assertTrue(m1.compareTo(new BigDecimal("1.0")) == 0);

        Vector v2 = new Vector(new BigDecimal("0.0"), new BigDecimal("1.0"), new BigDecimal("0.0"));
        BigDecimal m2 = v2.magnitude();
        assertTrue(m2.compareTo(new BigDecimal("1.0")) == 0);

        Vector v3 = new Vector(new BigDecimal("0.0"), new BigDecimal("0.0"), new BigDecimal("1.0"));
        BigDecimal m3 = v3.magnitude();
        assertTrue(m3.compareTo(new BigDecimal("1.0")) == 0);

        Vector v4 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        BigDecimal m4 = v4.magnitude();
        assertTrue(m4.compareTo(new BigDecimal("3.7417")) == 0);

        Vector v5 = new Vector(new BigDecimal("-1.0"), new BigDecimal("-2.0"), new BigDecimal("-3.0"));
        BigDecimal m5 = v5.magnitude();
        assertTrue(m5.compareTo(new BigDecimal("3.7417")) == 0);
    }

    @Test
    public void normalise() {

        Vector v1 = new Vector(new BigDecimal("4.0"), new BigDecimal("0.0"), new BigDecimal("0.0"));
        Vector v2 = v1.normalise();
        assertTrue(v2.getX().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(v2.getY().compareTo(new BigDecimal("0.0")) == 0);
        assertTrue(v2.getZ().compareTo(new BigDecimal("0.0")) == 0);
        assertTrue(v2.getW().compareTo(new BigDecimal("0.0")) == 0);

        v1 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        v2 = v1.normalise();
        assertTrue(v2.getX().compareTo(new BigDecimal("0.2673")) == 0);
        assertTrue(v2.getY().compareTo(new BigDecimal("0.5345")) == 0);
        assertTrue(v2.getZ().compareTo(new BigDecimal("0.8018")) == 0);
        assertTrue(v2.getW().compareTo(new BigDecimal("0.0")) == 0);
    }

    @Test
    public void dot() {
        Vector v1 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        Vector v2 = new Vector(new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("4.0"));
        final BigDecimal dot = Vector.dot(v1, v2);
        assertTrue(dot.compareTo(new BigDecimal("20.0")) == 0);

    }

    @Test
    public void cross() {
        Vector v1 = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        Vector v2 = new Vector(new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("4.0"));

        Vector cross = Vector.cross(v1, v2);
        assertTrue(cross.getX().compareTo(new BigDecimal("-1.0")) == 0);
        assertTrue(cross.getY().compareTo(new BigDecimal("2.0")) == 0);
        assertTrue(cross.getZ().compareTo(new BigDecimal("-1.0")) == 0);
        assertTrue(cross.getW().compareTo(new BigDecimal("0.0")) == 0);

        cross = Vector.cross(v2, v1);
        assertTrue(cross.getX().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(cross.getY().compareTo(new BigDecimal("-2.0")) == 0);
        assertTrue(cross.getZ().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(cross.getW().compareTo(new BigDecimal("0.0")) == 0);

    }
}
