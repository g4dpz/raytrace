package com.badgersoft.raytrace.elements;

public class IntersectionWithUV extends Intersection {
    private double u;
    private double v;

    public IntersectionWithUV(double t, SceneObject object, double u, double v) {
        super(t, object);
        this.u = u;
        this.v = v;
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }
}
