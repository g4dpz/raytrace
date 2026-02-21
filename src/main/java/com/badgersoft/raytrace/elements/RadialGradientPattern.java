package com.badgersoft.raytrace.elements;

public class RadialGradientPattern extends Pattern {
    private Colour colorA;
    private Colour colorB;
    
    public RadialGradientPattern(Colour colorA, Colour colorB) {
        this.colorA = colorA;
        this.colorB = colorB;
    }
    
    @Override
    public Colour patternAt(Point point) {
        double distance = Math.sqrt(point.getX() * point.getX() + point.getZ() * point.getZ());
        double fraction = distance - Math.floor(distance);
        
        return new Colour(
            colorA.getRed() + fraction * (colorB.getRed() - colorA.getRed()),
            colorA.getGreen() + fraction * (colorB.getGreen() - colorA.getGreen()),
            colorA.getBlue() + fraction * (colorB.getBlue() - colorA.getBlue())
        );
    }
}
