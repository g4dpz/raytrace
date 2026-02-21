package com.badgersoft.raytrace.elements;

public class Plane extends SceneObject {
    
    public Plane() {
        super();
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        Vector objectNormal = new Vector(0, 1, 0);
        Vector worldNormal = new Vector(Matrix.invert(transform).transpose().multiply(objectNormal));
        return worldNormal.normalise();
    }
}
