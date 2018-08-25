package com.badgersoft.raytrace.elements;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class PointTest {

    @Test
    public void create() {
        Point p = new Point(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        assertTrue(p.getX().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(p.getY().compareTo(new BigDecimal("2.0")) == 0);
        assertTrue(p.getZ().compareTo(new BigDecimal("3.0")) == 0);
        assertTrue(p.getW().compareTo(new BigDecimal("1.0")) == 0);
    }

    @Test
    public void equals() {
        Point p1 = new Point(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        Point p2 = new Point(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        assertTrue(p1.equals(p2));
    }

    @Test
    public void notEquals() {
        Point p1 = new Point(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        Point p2 = new Point(new BigDecimal("2.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        assertTrue(!p1.equals(p2));
        Point p3 = new Point(new BigDecimal("1.0"), new BigDecimal("3.0"), new BigDecimal("3.0"));
        assertTrue(!p1.equals(p3));
        Point p4 = new Point(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("4.0"));
        assertTrue(!p1.equals(p4));

        Vector v = new Vector(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"));
        assertTrue(!p1.equals(v));
    }

    @Test
    public void addPoint() {
        Point p1 = new Point(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"));
        Point p2 = new Point(new BigDecimal("-2.0"), new BigDecimal("3.0"), new BigDecimal("1.0"));
        Vector v = p1.add(p2);
        assertTrue(v.getX().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(v.getY().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(v.getZ().compareTo(new BigDecimal("6.0")) == 0);
        assertTrue(v.getW().compareTo(new BigDecimal("0.0")) == 0);
    }

    @Test
    public void subtractPoint() {
        Point p1 = new Point(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"));
        Point p2 = new Point(new BigDecimal("-2.0"), new BigDecimal("3.0"), new BigDecimal("1.0"));
        Vector v = p1.subtract(p2);
        assertTrue(v.getX().compareTo(new BigDecimal("5.0")) == 0);
        assertTrue(v.getY().compareTo(new BigDecimal("-5.0")) == 0);
        assertTrue(v.getZ().compareTo(new BigDecimal("4.0")) == 0);
        assertTrue(v.getW().compareTo(new BigDecimal("0.0")) == 0);
    }

    @Test
    public void addVector() {
        Point p1 = new Point(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"));
        Vector v = new Vector(new BigDecimal("1.1"), new BigDecimal("2.22"), new BigDecimal("3.333"));
        Point p2 = p1.add(v);
        assertTrue(p2.getX().compareTo(new BigDecimal("4.1")) == 0);
        assertTrue(p2.getY().compareTo(new BigDecimal("0.22")) == 0);
        assertTrue(p2.getZ().compareTo(new BigDecimal("8.333")) == 0);
        assertTrue(p2.getW().compareTo(new BigDecimal("1.0")) == 0);
    }

    @Test
    public void subtractVector() {
        Point p1 = new Point(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"));
        Vector v = new Vector(new BigDecimal("1.1"), new BigDecimal("2.22"), new BigDecimal("3.333"));
        Point p2 = p1.subtract(v);
        assertTrue(p2.getX().compareTo(new BigDecimal("1.9")) == 0);
        assertTrue(p2.getY().compareTo(new BigDecimal("-4.22")) == 0);
        assertTrue(p2.getZ().compareTo(new BigDecimal("1.667")) == 0);
        assertTrue(p2.getW().compareTo(new BigDecimal("1.0")) == 0);
    }
}
