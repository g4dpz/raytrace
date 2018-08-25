package com.badgersoft.raytrace.elements;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class TupleTest {

    @Test
    public void create() {
        Tuple t = new Tuple(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("0.0"));
        assertTrue(t.getX().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(t.getY().compareTo(new BigDecimal("2.0")) == 0);
        assertTrue(t.getZ().compareTo(new BigDecimal("3.0")) == 0);
        assertTrue(t.getW().compareTo(new BigDecimal("0.0")) == 0);
    }

    @Test
    public void equals() {
        Tuple t1 = new Tuple(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("0.0"));
        Tuple t2 = new Tuple(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("0.0"));
        assertTrue(t1.equals(t2));
    }

    @Test
    public void notEquals() {
        Tuple t1 = new Tuple(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("0.0"));
        Tuple t2 = new Tuple(new BigDecimal("2.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("0.0"));
        assertTrue(!t1.equals(t2));
        Tuple t3 = new Tuple(new BigDecimal("1.0"), new BigDecimal("3.0"), new BigDecimal("3.0"), new BigDecimal("0.0"));
        assertTrue(!t1.equals(t3));
        Tuple t4 = new Tuple(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("4.0"), new BigDecimal("0.0"));
        assertTrue(!t1.equals(t4));
        Tuple t5 = new Tuple(new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0"), new BigDecimal("1.0"));
        assertTrue(!t1.equals(t5));
    }

    @Test
    public void add() {
        Tuple t1 = new Tuple(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"), new BigDecimal("1.0"));
        Tuple t2 = new Tuple(new BigDecimal("-2.0"), new BigDecimal("3.0"), new BigDecimal("1.0"), new BigDecimal("0.0"));
        Tuple t3 = t1.add(t2);
        assertTrue(t3.getX().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(t3.getY().compareTo(new BigDecimal("1.0")) == 0);
        assertTrue(t3.getZ().compareTo(new BigDecimal("6.0")) == 0);
        assertTrue(t3.getW().compareTo(new BigDecimal("1.0")) == 0);
    }
    
    @Test
    public void negate() {
        Tuple t1 = new Tuple(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"), new BigDecimal("1.0"));
        Tuple t2 = t1.negate();
        assertTrue(t2.getX().compareTo(new BigDecimal("-3.0")) == 0);
        assertTrue(t2.getY().compareTo(new BigDecimal("2.0")) == 0);
        assertTrue(t2.getZ().compareTo(new BigDecimal("-5.0")) == 0);
        assertTrue(t2.getW().compareTo(new BigDecimal("-1.0")) == 0);
    }

    @Test
    public void multiply() {
        Tuple t1 = new Tuple(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"), new BigDecimal("1.0"));
        Tuple t2 = t1.multiply(new BigDecimal(-2.1));
        assertTrue(t2.getX().compareTo(new BigDecimal("-6.3000")) == 0);
        assertTrue(t2.getY().compareTo(new BigDecimal("4.2000")) == 0);
        assertTrue(t2.getZ().compareTo(new BigDecimal("-10.5000")) == 0);
        assertTrue(t2.getW().compareTo(new BigDecimal("-2.1000")) == 0);
    }

    @Test
    public void divide() {
        Tuple t1 = new Tuple(new BigDecimal("3.0"), new BigDecimal("-2.0"), new BigDecimal("5.0"), new BigDecimal("1.0"));
        Tuple t2 = t1.divide(new BigDecimal(-2.1));
        assertTrue(t2.getX().compareTo(new BigDecimal("-1.4286")) == 0);
        assertTrue(t2.getY().compareTo(new BigDecimal("0.9524")) == 0);
        assertTrue(t2.getZ().compareTo(new BigDecimal("-2.3810")) == 0);
        assertTrue(t2.getW().compareTo(new BigDecimal("-0.4762")) == 0);
    }
}
