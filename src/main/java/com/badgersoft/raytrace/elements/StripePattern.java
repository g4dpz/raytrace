package com.badgersoft.raytrace.elements;

public class StripePattern extends Pattern {
    private Colour a;
    private Colour b;

    public StripePattern(Colour a, Colour b) {
        super();
        this.a = a;
        this.b = b;
    }

    public Colour getA() {
        return a;
    }

    public Colour getB() {
        return b;
    }

    @Override
    public Colour patternAt(Point point) {
        if (Math.floor(point.getX()) % 2 == 0) {
            return a;
        }
        return b;
    }
}
