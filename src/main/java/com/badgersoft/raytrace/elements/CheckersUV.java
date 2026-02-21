package com.badgersoft.raytrace.elements;

public class CheckersUV extends UVPattern {
    private int width;
    private int height;
    private Colour colorA;
    private Colour colorB;
    
    public CheckersUV(int width, int height, Colour colorA, Colour colorB) {
        this.width = width;
        this.height = height;
        this.colorA = colorA;
        this.colorB = colorB;
    }
    
    @Override
    public Colour uvPatternAt(double u, double v) {
        int u2 = (int) Math.floor(u * width);
        int v2 = (int) Math.floor(v * height);
        
        if ((u2 + v2) % 2 == 0) {
            return colorA;
        } else {
            return colorB;
        }
    }
}
