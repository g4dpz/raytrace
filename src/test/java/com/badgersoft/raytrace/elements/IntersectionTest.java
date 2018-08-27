package com.badgersoft.raytrace.elements;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.badgersoft.raytrace.elements.Intersection.intersections;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntersectionTest {

    @Test
    public void aggregate() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(1.0, s);
        Intersection i2 = new Intersection(2.0, s);

        List<Intersection> intersections = new ArrayList<>();
        intersections.add(i1);
        intersections.add(i2);

        for (int i = 0; i < intersections.size(); i++) {
            final Intersection intersection = intersections.get(i);
            switch(i) {
                case 0:
                    assertTrue(s.equals(intersection.getScreenObject()));
                    break;
                case 1:
                    assertTrue(s.equals(intersection.getScreenObject()));
                    break;
            }
        }
    }

    @Test
    public void intersectionList() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(1.0, s);
        Intersection i2 = new Intersection(2.0, s);
        final List<Intersection> intersections = intersections(i1, i2);
        assertEquals(2, intersections.size());
        assertEquals(i1, intersections.get(0));
        assertEquals(i2, intersections.get(1));
    }
}
