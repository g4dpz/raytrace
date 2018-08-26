package com.badgersoft.raytrace.elements;

import static com.badgersoft.raytrace.elements.Vector.dot;

public class Sphere {

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

    public static double[] intersects(Sphere sphere, Ray ray) {
        Vector sphereToRay = ray.getOrigin().subtract(sphere.getOrigin());
        final double a = dot(ray.getDirection(), ray.getDirection());
        final double b = 2 * dot(ray.getDirection(), sphereToRay);
        final double c = dot(sphereToRay, sphereToRay) - 1.0;
        double discriminant = (b * b) - (4.0 * a * c);

        if (discriminant < 0) {
            return new double[0];
        }

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        if (t1 > t2){
            double t3 = t2;
            t1 = t2;
            t2 = t3;
        }
        return new double[] {t1, t2};


    }

}
