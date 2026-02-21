package com.badgersoft.raytrace.elements;

public class Computation {
    private double t;
    private SceneObject object;
    private Point point;
    private Point overPoint;
    private Point underPoint;
    private Vector eyev;
    private Vector normalv;
    private Vector reflectv;
    private boolean inside;
    private double n1;
    private double n2;

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public SceneObject getObject() {
        return object;
    }

    public void setObject(SceneObject object) {
        this.object = object;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getOverPoint() {
        return overPoint;
    }

    public void setOverPoint(Point overPoint) {
        this.overPoint = overPoint;
    }

    public Point getUnderPoint() {
        return underPoint;
    }

    public void setUnderPoint(Point underPoint) {
        this.underPoint = underPoint;
    }

    public Vector getEyev() {
        return eyev;
    }

    public void setEyev(Vector eyev) {
        this.eyev = eyev;
    }

    public Vector getNormalv() {
        return normalv;
    }

    public void setNormalv(Vector normalv) {
        this.normalv = normalv;
    }

    public Vector getReflectv() {
        return reflectv;
    }

    public void setReflectv(Vector reflectv) {
        this.reflectv = reflectv;
    }

    public boolean isInside() {
        return inside;
    }

    public void setInside(boolean inside) {
        this.inside = inside;
    }

    public double getN1() {
        return n1;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public double getN2() {
        return n2;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }
}
