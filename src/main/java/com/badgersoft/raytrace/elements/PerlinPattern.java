package com.badgersoft.raytrace.elements;

public class PerlinPattern extends Pattern {
    private Colour colorA;
    private Colour colorB;
    private double scale;
    private int octaves;
    
    public PerlinPattern(Colour colorA, Colour colorB) {
        this(colorA, colorB, 1.0, 4);
    }
    
    public PerlinPattern(Colour colorA, Colour colorB, double scale, int octaves) {
        this.colorA = colorA;
        this.colorB = colorB;
        this.scale = scale;
        this.octaves = octaves;
    }
    
    @Override
    public Colour patternAt(Point point) {
        double noise = PerlinNoise.turbulence(
            point.getX() * scale,
            point.getY() * scale,
            point.getZ() * scale,
            octaves
        );
        
        // Map noise from [-1, 1] to [0, 1]
        double t = (noise + 1) / 2;
        
        // Interpolate between colors
        return new Colour(
            colorA.getRed() + t * (colorB.getRed() - colorA.getRed()),
            colorA.getGreen() + t * (colorB.getGreen() - colorA.getGreen()),
            colorA.getBlue() + t * (colorB.getBlue() - colorA.getBlue())
        );
    }
}
