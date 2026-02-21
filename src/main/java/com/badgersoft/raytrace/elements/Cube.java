package com.badgersoft.raytrace.elements;

public class Cube extends SceneObject {
    
    public Cube() {
        super();
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        Point objectPoint = new Point(Matrix.invert(transform).multiply(worldPoint));
        
        double maxC = Math.max(Math.abs(objectPoint.getX()), 
                      Math.max(Math.abs(objectPoint.getY()), 
                               Math.abs(objectPoint.getZ())));
        
        Vector objectNormal;
        if (maxC == Math.abs(objectPoint.getX())) {
            objectNormal = new Vector(objectPoint.getX(), 0, 0);
        } else if (maxC == Math.abs(objectPoint.getY())) {
            objectNormal = new Vector(0, objectPoint.getY(), 0);
        } else {
            objectNormal = new Vector(0, 0, objectPoint.getZ());
        }
        
        Vector worldNormal = new Vector(Matrix.invert(transform).transpose().multiply(objectNormal));
        return worldNormal.normalise();
    }
}
