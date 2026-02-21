package com.badgersoft.raytrace.elements;

public abstract class UVPattern extends Pattern {
    
    public abstract Colour uvPatternAt(double u, double v);
    
    @Override
    public Colour patternAt(Point point) {
        double[] uv = sphericalMap(point);
        return uvPatternAt(uv[0], uv[1]);
    }
    
    public static double[] sphericalMap(Point point) {
        double theta = Math.atan2(point.getX(), point.getZ());
        Vector vec = new Vector(point.getX(), point.getY(), point.getZ());
        double radius = vec.magnitude();
        double phi = Math.acos(point.getY() / radius);
        
        double rawU = theta / (2 * Math.PI);
        double u = 1 - (rawU + 0.5);
        double v = 1 - phi / Math.PI;
        
        return new double[]{u, v};
    }
    
    public static double[] planarMap(Point point) {
        double u = point.getX() % 1.0;
        double v = point.getZ() % 1.0;
        
        if (u < 0) u += 1;
        if (v < 0) v += 1;
        
        return new double[]{u, v};
    }
    
    public static double[] cylindricalMap(Point point) {
        double theta = Math.atan2(point.getX(), point.getZ());
        double rawU = theta / (2 * Math.PI);
        double u = 1 - (rawU + 0.5);
        double v = point.getY() % 1.0;
        
        if (v < 0) v += 1;
        
        return new double[]{u, v};
    }
}
