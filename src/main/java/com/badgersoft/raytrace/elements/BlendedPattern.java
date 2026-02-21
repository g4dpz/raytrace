package com.badgersoft.raytrace.elements;

public class BlendedPattern extends Pattern {
    private Pattern patternA;
    private Pattern patternB;
    private double blend; // 0.0 = all A, 1.0 = all B
    
    public BlendedPattern(Pattern patternA, Pattern patternB, double blend) {
        this.patternA = patternA;
        this.patternB = patternB;
        this.blend = Math.max(0, Math.min(1, blend));
    }
    
    @Override
    public Colour patternAt(Point point) {
        Colour colorA = patternA.patternAt(point);
        Colour colorB = patternB.patternAt(point);
        
        return new Colour(
            colorA.getRed() * (1 - blend) + colorB.getRed() * blend,
            colorA.getGreen() * (1 - blend) + colorB.getGreen() * blend,
            colorA.getBlue() * (1 - blend) + colorB.getBlue() * blend
        );
    }
}
