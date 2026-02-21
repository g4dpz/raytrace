package com.badgersoft.raytrace.elements;

public abstract class SceneObject {
    protected Material material;
    protected Matrix transform;

    public SceneObject() {
        this.material = new Material();
        this.transform = new Matrix(new double[][]{{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}});
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Matrix getTransform() {
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    public abstract Vector normalAt(Point worldPoint);
    
    public BoundingBox getBounds() {
        // Default implementation - subclasses should override for better bounds
        return new BoundingBox(
            new Point(-1, -1, -1),
            new Point(1, 1, 1)
        ).transform(transform);
    }
}
