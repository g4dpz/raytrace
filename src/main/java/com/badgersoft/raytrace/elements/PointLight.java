package com.badgersoft.raytrace.elements;

public class PointLight {
    private Point position;
    private Colour intensity;

    public PointLight(Point position, Colour intensity) {
        this.position = position;
        this.intensity = intensity;
    }

    public Point getPosition() {
        return position;
    }

    public Colour getIntensity() {
        return intensity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PointLight)) return false;
        PointLight other = (PointLight) obj;
        return position.equals(other.position) && intensity.equals(other.intensity);
    }
}
