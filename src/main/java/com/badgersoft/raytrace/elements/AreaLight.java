package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

public class AreaLight {
    private Point corner;
    private Vector uvec;
    private Vector vvec;
    private int usteps;
    private int vsteps;
    private Colour intensity;
    private List<Point> samples;
    
    public AreaLight(Point corner, Vector fullUvec, int usteps, Vector fullVvec, int vsteps, Colour intensity) {
        this.corner = corner;
        this.usteps = usteps;
        this.vsteps = vsteps;
        this.intensity = intensity;
        
        // Divide vectors by steps to get increments
        this.uvec = fullUvec.multiply(1.0 / usteps);
        this.vvec = fullVvec.multiply(1.0 / vsteps);
        
        // Pre-calculate sample positions with jitter
        this.samples = new ArrayList<>();
        for (int v = 0; v < vsteps; v++) {
            for (int u = 0; u < usteps; u++) {
                Point sample = pointOnLight(u, v);
                samples.add(sample);
            }
        }
    }
    
    public Point getCorner() {
        return corner;
    }
    
    public Vector getUvec() {
        return uvec;
    }
    
    public Vector getVvec() {
        return vvec;
    }
    
    public int getUsteps() {
        return usteps;
    }
    
    public int getVsteps() {
        return vsteps;
    }
    
    public Colour getIntensity() {
        return intensity;
    }
    
    public List<Point> getSamples() {
        return samples;
    }
    
    public Point pointOnLight(int u, int v) {
        // Add 0.5 to center the sample in the cell
        Vector uOffset = uvec.multiply(u + 0.5);
        Vector vOffset = vvec.multiply(v + 0.5);
        return corner.add(uOffset).add(vOffset);
    }
    
    public double intensityAt(Point point, RenderWorld world) {
        int total = usteps * vsteps;
        int illuminated = 0;
        
        for (Point sample : samples) {
            if (!world.isShadowed(point, sample)) {
                illuminated++;
            }
        }
        
        return (double) illuminated / total;
    }
}
