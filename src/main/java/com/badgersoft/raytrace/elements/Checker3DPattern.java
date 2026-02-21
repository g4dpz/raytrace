package com.badgersoft.raytrace.elements;

public class Checker3DPattern extends Pattern {
    private Colour colorA;
    private Colour colorB;
    
    public Checker3DPattern(Colour colorA, Colour colorB) {
        this.colorA = colorA;
        this.colorB = colorB;
    }
    
    @Override
    public Colour patternAt(Point point) {
        int sum = (int) (Math.floor(point.getX()) + 
                        Math.floor(point.getY()) + 
                        Math.floor(point.getZ()));
        
        if (sum % 2 == 0) {
            return colorA;
        } else {
            return colorB;
        }
    }
}
