package com.badgersoft.raytrace.elements;

public class Cylinder extends SceneObject {
    private double minimum;
    private double maximum;
    private boolean closed;

    public Cylinder() {
        super();
        this.minimum = Double.NEGATIVE_INFINITY;
        this.maximum = Double.POSITIVE_INFINITY;
        this.closed = false;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        Point objectPoint = new Point(Matrix.invert(transform).multiply(worldPoint));
        
        double dist = objectPoint.getX() * objectPoint.getX() + objectPoint.getZ() * objectPoint.getZ();
        
        Vector objectNormal;
        if (dist < 1 && objectPoint.getY() >= maximum - 0.00001) {
            objectNormal = new Vector(0, 1, 0);
        } else if (dist < 1 && objectPoint.getY() <= minimum + 0.00001) {
            objectNormal = new Vector(0, -1, 0);
        } else {
            objectNormal = new Vector(objectPoint.getX(), 0, objectPoint.getZ());
        }
        
        Vector worldNormal = new Vector(Matrix.invert(transform).transpose().multiply(objectNormal));
        return worldNormal.normalise();
    }

    private boolean checkCap(Ray ray, double t) {
        double x = ray.getOrigin().getX() + t * ray.getDirection().getX();
        double z = ray.getOrigin().getZ() + t * ray.getDirection().getZ();
        return (x * x + z * z) <= 1;
    }

    public java.util.List<Intersection> intersect(Ray ray) {
        java.util.List<Intersection> xs = new java.util.ArrayList<>();
        
        Ray objectRay = ray.transform(Matrix.invert(transform));
        
        double a = objectRay.getDirection().getX() * objectRay.getDirection().getX() +
                   objectRay.getDirection().getZ() * objectRay.getDirection().getZ();
        
        if (Math.abs(a) < 0.00001) {
            intersectCaps(objectRay, xs);
            return xs;
        }
        
        double b = 2 * objectRay.getOrigin().getX() * objectRay.getDirection().getX() +
                   2 * objectRay.getOrigin().getZ() * objectRay.getDirection().getZ();
        
        double c = objectRay.getOrigin().getX() * objectRay.getOrigin().getX() +
                   objectRay.getOrigin().getZ() * objectRay.getOrigin().getZ() - 1;
        
        double disc = b * b - 4 * a * c;
        
        if (disc < 0) {
            return xs;
        }
        
        double t0 = (-b - Math.sqrt(disc)) / (2 * a);
        double t1 = (-b + Math.sqrt(disc)) / (2 * a);
        
        if (t0 > t1) {
            double temp = t0;
            t0 = t1;
            t1 = temp;
        }
        
        double y0 = objectRay.getOrigin().getY() + t0 * objectRay.getDirection().getY();
        if (minimum < y0 && y0 < maximum) {
            xs.add(new Intersection(t0, this));
        }
        
        double y1 = objectRay.getOrigin().getY() + t1 * objectRay.getDirection().getY();
        if (minimum < y1 && y1 < maximum) {
            xs.add(new Intersection(t1, this));
        }
        
        intersectCaps(objectRay, xs);
        
        return xs;
    }

    private void intersectCaps(Ray ray, java.util.List<Intersection> xs) {
        if (!closed || Math.abs(ray.getDirection().getY()) < 0.00001) {
            return;
        }
        
        double t = (minimum - ray.getOrigin().getY()) / ray.getDirection().getY();
        if (checkCap(ray, t)) {
            xs.add(new Intersection(t, this));
        }
        
        t = (maximum - ray.getOrigin().getY()) / ray.getDirection().getY();
        if (checkCap(ray, t)) {
            xs.add(new Intersection(t, this));
        }
    }
}
