package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

public class Group extends SceneObject {
    private List<SceneObject> children;

    public Group() {
        super();
        this.children = new ArrayList<>();
    }

    public void addChild(SceneObject object) {
        children.add(object);
    }

    public List<SceneObject> getChildren() {
        return children;
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        throw new UnsupportedOperationException("Cannot get normal of a group directly");
    }

    public List<Intersection> intersect(Ray ray) {
        List<Intersection> xs = new ArrayList<>();
        
        Ray objectRay = ray.transform(Matrix.invert(transform));
        
        for (SceneObject child : children) {
            if (child instanceof Sphere) {
                xs.addAll(objectRay.intersect((Sphere) child));
            } else if (child instanceof Plane) {
                xs.addAll(intersectPlane(objectRay, (Plane) child));
            } else if (child instanceof Cube) {
                xs.addAll(intersectCube(objectRay, (Cube) child));
            } else if (child instanceof Cylinder) {
                xs.addAll(((Cylinder) child).intersect(objectRay));
            } else if (child instanceof Cone) {
                xs.addAll(((Cone) child).intersect(objectRay));
            } else if (child instanceof Group) {
                xs.addAll(((Group) child).intersect(objectRay));
            }
        }
        
        xs.sort((a, b) -> Double.compare(a.getT(), b.getT()));
        return xs;
    }

    private List<Intersection> intersectPlane(Ray ray, Plane plane) {
        List<Intersection> xs = new ArrayList<>();
        Ray objectRay = ray.transform(Matrix.invert(plane.getTransform()));
        
        if (Math.abs(objectRay.getDirection().getY()) < 0.00001) {
            return xs;
        }
        
        double t = -objectRay.getOrigin().getY() / objectRay.getDirection().getY();
        xs.add(new Intersection(t, plane));
        return xs;
    }

    private List<Intersection> intersectCube(Ray ray, Cube cube) {
        List<Intersection> xs = new ArrayList<>();
        Ray objectRay = ray.transform(Matrix.invert(cube.getTransform()));
        
        double[] xt = checkAxis(objectRay.getOrigin().getX(), objectRay.getDirection().getX());
        double[] yt = checkAxis(objectRay.getOrigin().getY(), objectRay.getDirection().getY());
        double[] zt = checkAxis(objectRay.getOrigin().getZ(), objectRay.getDirection().getZ());
        
        double tmin = Math.max(Math.max(xt[0], yt[0]), zt[0]);
        double tmax = Math.min(Math.min(xt[1], yt[1]), zt[1]);
        
        if (tmin > tmax) {
            return xs;
        }
        
        xs.add(new Intersection(tmin, cube));
        xs.add(new Intersection(tmax, cube));
        return xs;
    }

    private double[] checkAxis(double origin, double direction) {
        double tminNumerator = (-1 - origin);
        double tmaxNumerator = (1 - origin);
        
        double tmin, tmax;
        if (Math.abs(direction) >= 0.00001) {
            tmin = tminNumerator / direction;
            tmax = tmaxNumerator / direction;
        } else {
            tmin = tminNumerator * Double.POSITIVE_INFINITY;
            tmax = tmaxNumerator * Double.POSITIVE_INFINITY;
        }
        
        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }
        
        return new double[]{tmin, tmax};
    }
}
