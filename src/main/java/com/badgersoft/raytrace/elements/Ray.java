package com.badgersoft.raytrace.elements;

public class Ray {

    private Point origin;
    private Vector direction;

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Point getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public static Point position(Ray ray, double time) {
        return ray.origin.add(ray.direction.multiply(time));
    }
}
