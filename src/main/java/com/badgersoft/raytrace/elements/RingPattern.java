package com.badgersoft.raytrace.elements;

public class RingPattern extends Pattern {
    private Colour a;
    private Colour b;

    public RingPattern(Colour a, Colour b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    public Colour patternAt(Point point) {
        double distance = Math.sqrt(point.getX() * point.getX() + point.getZ() * point.getZ());
        if (Math.floor(distance) % 2 == 0) {
            return a;
        }
        return b;
    }
}
