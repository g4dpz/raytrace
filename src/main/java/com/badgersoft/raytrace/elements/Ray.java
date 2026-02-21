package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

import static com.badgersoft.raytrace.elements.Vector.dot;

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

    public Point position(double time) {
        return origin.add(direction.multiply(time));
    }

    public List<Intersection> intersect(Sphere sphere) {
        List<Intersection> result = new ArrayList<>();
        Ray r2 = transform(Matrix.invert(sphere.getTransform()));
        Vector sphereToRay = r2.getOrigin().subtract(sphere.getOrigin());
        final double a = dot(r2.getDirection(), r2.getDirection());
        final double b = 2 * dot(r2.getDirection(), sphereToRay);
        final double c = dot(sphereToRay, sphereToRay) - 1.0;
        double discriminant = (b * b) - (4.0 * a * c);

        if (discriminant < 0) {
            return result;
        }

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        if (t1 > t2){
            double t3 = t2;
            t1 = t2;
            t2 = t3;
        }

        result.add(new Intersection(t1, sphere));
        result.add(new Intersection(t2, sphere));

        return result;
    }

    public Ray transform(Matrix m) {
        Point origin = new Point(m.multiply(getOrigin()));
        Vector direction = new Vector(m.multiply(getDirection()));
        return new Ray(origin, direction);
    }
}
