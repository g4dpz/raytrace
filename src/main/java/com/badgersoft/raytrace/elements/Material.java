package com.badgersoft.raytrace.elements;

public class Material {
    private Colour color;
    private double ambient;
    private double diffuse;
    private double specular;
    private double shininess;
    private double reflective;
    private double transparency;
    private double refractiveIndex;
    private Pattern pattern;

    public Material() {
        this.color = new Colour(1.0, 1.0, 1.0);
        this.ambient = 0.1;
        this.diffuse = 0.9;
        this.specular = 0.9;
        this.shininess = 200.0;
        this.reflective = 0.0;
        this.transparency = 0.0;
        this.refractiveIndex = 1.0;
        this.pattern = null;
    }

    public Colour getColor() {
        return color;
    }

    public void setColor(Colour color) {
        this.color = color;
    }

    public double getAmbient() {
        return ambient;
    }

    public void setAmbient(double ambient) {
        this.ambient = ambient;
    }

    public double getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(double diffuse) {
        this.diffuse = diffuse;
    }

    public double getSpecular() {
        return specular;
    }

    public void setSpecular(double specular) {
        this.specular = specular;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    public double getReflective() {
        return reflective;
    }

    public void setReflective(double reflective) {
        this.reflective = reflective;
    }

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public double getRefractiveIndex() {
        return refractiveIndex;
    }

    public void setRefractiveIndex(double refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Material)) return false;
        Material other = (Material) obj;
        return color.equals(other.color) &&
               Double.compare(ambient, other.ambient) == 0 &&
               Double.compare(diffuse, other.diffuse) == 0 &&
               Double.compare(specular, other.specular) == 0 &&
               Double.compare(shininess, other.shininess) == 0;
    }
}
