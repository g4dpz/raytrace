package com.badgersoft.raytrace.elements;

public abstract class Pattern {
    protected Matrix transform;

    public Pattern() {
        this.transform = new Matrix(new double[][]{{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}});
    }

    public Matrix getTransform() {
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    public abstract Colour patternAt(Point point);

    public Colour patternAtShape(SceneObject object, Point worldPoint) {
        Point objectPoint = new Point(Matrix.invert(object.getTransform()).multiply(worldPoint));
        Point patternPoint = new Point(Matrix.invert(transform).multiply(objectPoint));
        return patternAt(patternPoint);
    }
}
