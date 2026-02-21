package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

public class CSG extends SceneObject {
    public enum Operation {
        UNION,
        INTERSECTION,
        DIFFERENCE
    }

    private Operation operation;
    private SceneObject left;
    private SceneObject right;

    public CSG(Operation operation, SceneObject left, SceneObject right) {
        super();
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    public Operation getOperation() {
        return operation;
    }

    public SceneObject getLeft() {
        return left;
    }

    public SceneObject getRight() {
        return right;
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        throw new UnsupportedOperationException("Cannot get normal of CSG directly");
    }

    public List<Intersection> intersect(Ray ray) {
        Ray objectRay = ray.transform(Matrix.invert(transform));
        
        List<Intersection> leftXs = intersectShape(objectRay, left);
        List<Intersection> rightXs = intersectShape(objectRay, right);
        
        List<Intersection> xs = new ArrayList<>();
        xs.addAll(leftXs);
        xs.addAll(rightXs);
        xs.sort((a, b) -> Double.compare(a.getT(), b.getT()));
        
        return filterIntersections(xs);
    }

    private List<Intersection> intersectShape(Ray ray, SceneObject shape) {
        if (shape instanceof Sphere) {
            return ray.intersect((Sphere) shape);
        } else if (shape instanceof Cube) {
            return intersectCube(ray, (Cube) shape);
        } else if (shape instanceof Cylinder) {
            return ((Cylinder) shape).intersect(ray);
        } else if (shape instanceof Cone) {
            return ((Cone) shape).intersect(ray);
        } else if (shape instanceof CSG) {
            return ((CSG) shape).intersect(ray);
        }
        return new ArrayList<>();
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

    private List<Intersection> filterIntersections(List<Intersection> xs) {
        boolean inl = false;
        boolean inr = false;
        List<Intersection> result = new ArrayList<>();

        for (Intersection i : xs) {
            boolean lhit = includes(left, i.getScreenObject());

            if (intersectionAllowed(operation, lhit, inl, inr)) {
                result.add(i);
            }

            if (lhit) {
                inl = !inl;
            } else {
                inr = !inr;
            }
        }

        return result;
    }

    private boolean includes(SceneObject parent, SceneObject child) {
        if (parent == child) {
            return true;
        }
        
        if (parent instanceof CSG) {
            CSG csg = (CSG) parent;
            return includes(csg.left, child) || includes(csg.right, child);
        }
        
        if (parent instanceof Group) {
            Group group = (Group) parent;
            for (SceneObject c : group.getChildren()) {
                if (includes(c, child)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    public static boolean intersectionAllowed(Operation op, boolean lhit, boolean inl, boolean inr) {
        switch (op) {
            case UNION:
                return (lhit && !inr) || (!lhit && !inl);
            case INTERSECTION:
                return (lhit && inr) || (!lhit && inl);
            case DIFFERENCE:
                return (lhit && !inr) || (!lhit && inl);
            default:
                return false;
        }
    }
}
