package com.badgersoft.raytrace.elements;

public class GradientPattern extends Pattern {
    private Colour a;
    private Colour b;

    public GradientPattern(Colour a, Colour b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    public Colour patternAt(Point point) {
        Colour distance = b.subtract(a);
        double fraction = point.getX() - Math.floor(point.getX());
        return a.add(distance.multiply(fraction));
    }
}
