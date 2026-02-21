package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class CSGTest {

    @Test
    public void testCSGCreation() {
        Sphere s1 = new Sphere();
        Cube c1 = new Cube();
        CSG csg = new CSG(CSG.Operation.UNION, s1, c1);
        
        assertEquals(CSG.Operation.UNION, csg.getOperation());
        assertEquals(s1, csg.getLeft());
        assertEquals(c1, csg.getRight());
    }

    // @Test
    public void testUnionIntersectionAllowed() {
        assertFalse(CSG.intersectionAllowed(CSG.Operation.UNION, true, true, true));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.UNION, true, true, false));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.UNION, true, false, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.UNION, true, false, false));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.UNION, false, true, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.UNION, false, true, false));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.UNION, false, false, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.UNION, false, false, false));
    }

    // @Test
    public void testIntersectionIntersectionAllowed() {
        assertTrue(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, true, true, true));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, true, true, false));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, true, false, true));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, true, false, false));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, false, true, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, false, true, false));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, false, false, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.INTERSECTION, false, false, false));
    }

    @Test
    public void testDifferenceIntersectionAllowed() {
        assertFalse(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, true, true, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, true, true, false));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, true, false, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, true, false, false));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, false, true, true));
        assertTrue(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, false, true, false));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, false, false, true));
        assertFalse(CSG.intersectionAllowed(CSG.Operation.DIFFERENCE, false, false, false));
    }
}
