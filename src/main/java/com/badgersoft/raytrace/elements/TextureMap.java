package com.badgersoft.raytrace.elements;

public class TextureMap extends UVPattern {
    private UVPattern uvPattern;
    private String mappingType; // "spherical", "planar", "cylindrical"
    
    public TextureMap(UVPattern uvPattern, String mappingType) {
        this.uvPattern = uvPattern;
        this.mappingType = mappingType;
    }
    
    @Override
    public Colour uvPatternAt(double u, double v) {
        return uvPattern.uvPatternAt(u, v);
    }
    
    @Override
    public Colour patternAt(Point point) {
        double[] uv;
        switch (mappingType) {
            case "planar":
                uv = planarMap(point);
                break;
            case "cylindrical":
                uv = cylindricalMap(point);
                break;
            case "spherical":
            default:
                uv = sphericalMap(point);
                break;
        }
        return uvPatternAt(uv[0], uv[1]);
    }
}
