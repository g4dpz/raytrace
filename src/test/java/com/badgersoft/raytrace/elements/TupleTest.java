package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TupleTest {

    @Test
    public void equals() {
        Tuple t1 = new Tuple(1.0, 2.0, 3.0, 0.0);
        Tuple t2 = new Tuple(1.0, 2.0, 3.0, 0.0);
        assertTrue(t1.equals(t2));
    }

    @Test
    public void notEquals() {
        Tuple t1 = new Tuple(1.0, 2.0, 3.0, 0.0);
        Tuple t2 = new Tuple(2.0, 2.0, 3.0, 0.0);
        assertTrue(!t1.equals(t2));
        Tuple t3 = new Tuple(1.0, 3.0, 3.0, 0.0);
        assertTrue(!t1.equals(t3));
        Tuple t4 = new Tuple(1.0, 2.0, 4.0, 0.0);
        assertTrue(!t1.equals(t4));
        Tuple t5 = new Tuple(1.0, 2.0, 3.0, 1.0);
        assertTrue(!t1.equals(t5));
    }

    @Test
    public void add() {
        Tuple t1 = new Tuple(3.0, -2.0, 5.0, 1.0);
        Tuple t2 = new Tuple(-2.0, 3.0, 1.0, 0.0);
        Tuple t3 = t1.add(t2);
        assertTrue(compare(t3.getX(), 1.0));
        assertTrue(compare(t3.getY(), 1.0));
        assertTrue(compare(t3.getZ(), 6.0));
        assertTrue(compare(t3.getW(), 1.0));
    }
    
    @Test
    public void negate() {
        Tuple t1 = new Tuple(3.0, -2.0, 5.0, 1.0);
        Tuple t2 = t1.negate();
        assertTrue(compare(t2.getX(), -3.0));
        assertTrue(compare(t2.getY(), 2.0));
        assertTrue(compare(t2.getZ(), -5.0));
        assertTrue(compare(t2.getW(), -1.0));
    }

    @Test
    public void multiply() {
        Tuple t1 = new Tuple(3.0, -2.0, 5.0, 1.0);
        Tuple t2 = t1.multiply(-2.1);
        assertTrue(compare(t2.getX(), -6.3000));
        assertTrue(compare(t2.getY(), 4.2000));
        assertTrue(compare(t2.getZ(), -10.5000));
        assertTrue(compare(t2.getW(), -2.1000));
    }

    @Test
    public void divide() {
        Tuple t1 = new Tuple(3.0, -2.0, 5.0, 1.0);
        Tuple t2 = t1.divide(-2.1);

        assertTrue(compare(t2.getX(), -1.428571));
        assertTrue(compare(t2.getY(), 0.952380));
        assertTrue(compare(t2.getZ(), -2.380952));
        assertTrue(compare(t2.getW(), -0.476190));
    }

    private boolean compare(double a, double b) {
        return Math.abs(a - b) < 0.000001;
    }
}
