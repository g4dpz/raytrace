package com.badgersoft.raytrace.elements;

public class CheckerPattern extends Pattern {
    private Colour a;
    private Colour b;

    public CheckerPattern(Colour a, Colour b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    public Colour patternAt(Point point) {
        double sum = Math.floor(point.getX()) + Math.floor(point.getY()) + Math.floor(point.getZ());
        if (sum % 2 == 0) {
            return a;
        }
        return b;
    }
}
