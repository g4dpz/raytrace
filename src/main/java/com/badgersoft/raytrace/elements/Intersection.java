package com.badgersoft.raytrace.elements;

import java.util.Arrays;
import java.util.List;

public class Intersection {

    double t;
    SceneObject o;

    public Intersection(double t, SceneObject o) {
        this.t = t;
        this.o = o;
    }

    public double getT() {
        return t;
    }

    public SceneObject getScreenObject() {
        return o;
    }

    public static List<Intersection> intersections(Intersection... intersections) {
        return Arrays.asList(intersections);
    }
}
