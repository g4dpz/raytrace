package com.badgersoft.raytrace.elements;

import java.util.Objects;

public class Sphere extends SceneObject {
    private Point origin;
    private double radius;

    public Sphere() {
        super();
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
    public Vector normalAt(Point worldPoint) {
        Point objectPoint = new Point(Matrix.invert(transform).multiply(worldPoint));
        Vector objectNormal = objectPoint.subtract(origin);
        Vector worldNormal = new Vector(Matrix.invert(transform).transpose().multiply(objectNormal));
        return worldNormal.normalise();
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
