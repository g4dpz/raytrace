package com.badgersoft.raytrace.elements;

public class PerturbedPattern extends Pattern {
    private Pattern basePattern;
    private double scale;
    private int octaves;
    
    public PerturbedPattern(Pattern basePattern, double scale, int octaves) {
        this.basePattern = basePattern;
        this.scale = scale;
        this.octaves = octaves;
    }
    
    @Override
    public Colour patternAt(Point point) {
        // Add noise-based perturbation to the point
        double noiseX = PerlinNoise.turbulence(point.getX(), point.getY(), point.getZ(), octaves);
        double noiseY = PerlinNoise.turbulence(point.getY(), point.getZ(), point.getX(), octaves);
        double noiseZ = PerlinNoise.turbulence(point.getZ(), point.getX(), point.getY(), octaves);
        
        Point perturbedPoint = new Point(
            point.getX() + noiseX * scale,
            point.getY() + noiseY * scale,
            point.getZ() + noiseZ * scale
        );
        
        return basePattern.patternAt(perturbedPoint);
    }
}
