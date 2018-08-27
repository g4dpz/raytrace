package com.badgersoft.raytrace.elements;

import java.util.Objects;

public class Sphere extends SceneObject {

    private Point origin;
    private double radius;

    public Sphere() {
        origin = new Point(0,0,0);
        radius = 1.0;
    }

    public Point getOrigin() {
        return origin;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sphere)) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0 &&
                Objects.equals(origin, sphere.origin);
    }
}
