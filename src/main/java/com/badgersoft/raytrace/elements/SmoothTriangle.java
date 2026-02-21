package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

public class SmoothTriangle extends SceneObject {
    private Point p1;
    private Point p2;
    private Point p3;
    private Vector n1;
    private Vector n2;
    private Vector n3;
    private Vector e1;
    private Vector e2;

    public SmoothTriangle(Point p1, Point p2, Point p3, Vector n1, Vector n2, Vector n3) {
        super();
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        this.e1 = p2.subtract(p1);
        this.e2 = p3.subtract(p1);
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

    public Vector getN1() {
        return n1;
    }

    public Vector getN2() {
        return n2;
    }

    public Vector getN3() {
        return n3;
    }

    public Vector getE1() {
        return e1;
    }

    public Vector getE2() {
        return e2;
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        return normalAt(worldPoint, 0, 0);
    }

    public Vector normalAt(Point worldPoint, double u, double v) {
        Vector normal = n2.multiply(u)
                          .add(n3.multiply(v))
                          .add(n1.multiply(1 - u - v));
        
        Vector worldNormal = new Vector(Matrix.invert(transform).transpose().multiply(normal));
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
        xs.add(new IntersectionWithUV(t, this, u, v));
        
        return xs;
    }
}
