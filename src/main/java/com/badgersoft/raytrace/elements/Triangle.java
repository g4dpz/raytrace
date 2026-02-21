package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends SceneObject {
    private Point p1;
    private Point p2;
    private Point p3;
    private Vector e1;
    private Vector e2;
    private Vector normal;

    public Triangle(Point p1, Point p2, Point p3) {
        super();
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.e1 = p2.subtract(p1);
        this.e2 = p3.subtract(p1);
        this.normal = Vector.cross(e2, e1).normalise();
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    public Vector getE1() {
        return e1;
    }

    public Vector getE2() {
        return e2;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        Vector objectNormal = normal;
        Vector worldNormal = new Vector(Matrix.invert(transform).transpose().multiply(objectNormal));
        return worldNormal.normalise();
    }

    public List<Intersection> intersect(Ray ray) {
        List<Intersection> xs = new ArrayList<>();
        
        Ray objectRay = ray.transform(Matrix.invert(transform));
        
        Vector dirCrossE2 = Vector.cross(objectRay.getDirection(), e2);
        double det = Vector.dot(e1, dirCrossE2);
        
        if (Math.abs(det) < 0.00001) {
            return xs;
        }
        
        double f = 1.0 / det;
        Vector p1ToOrigin = objectRay.getOrigin().subtract(p1);
        double u = f * Vector.dot(p1ToOrigin, dirCrossE2);
        
        if (u < 0 || u > 1) {
            return xs;
        }
        
        Vector originCrossE1 = Vector.cross(p1ToOrigin, e1);
        double v = f * Vector.dot(objectRay.getDirection(), originCrossE1);
        
        if (v < 0 || (u + v) > 1) {
            return xs;
        }
        
        double t = f * Vector.dot(e2, originCrossE1);
        xs.add(new Intersection(t, this));
        
        return xs;
    }
}
