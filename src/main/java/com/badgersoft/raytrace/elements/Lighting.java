package com.badgersoft.raytrace.elements;

public class Lighting {
    
    public static Colour lighting(Material material, SceneObject object, PointLight light, 
                                   Point point, Vector eyev, Vector normalv, boolean inShadow) {
        Colour color;
        if (material.getPattern() != null) {
            color = material.getPattern().patternAtShape(object, point);
        } else {
            color = material.getColor();
        }

        Colour effectiveColor = color.multiply(light.getIntensity());
        Vector lightv = light.getPosition().subtract(point).normalise();
        Colour ambient = effectiveColor.multiply(material.getAmbient());

        if (inShadow) {
            return ambient;
        }

        double lightDotNormal = Vector.dot(lightv, normalv);
        Colour diffuse;
        Colour specular;

        if (lightDotNormal < 0) {
            diffuse = new Colour(0, 0, 0);
            specular = new Colour(0, 0, 0);
        } else {
            diffuse = effectiveColor.multiply(material.getDiffuse()).multiply(lightDotNormal);
            Vector reflectv = lightv.negate().reflect(normalv);
            double reflectDotEye = Vector.dot(reflectv, eyev);

            if (reflectDotEye <= 0) {
                specular = new Colour(0, 0, 0);
            } else {
                double factor = Math.pow(reflectDotEye, material.getShininess());
                specular = light.getIntensity().multiply(material.getSpecular()).multiply(factor);
            }
        }

        return ambient.add(diffuse).add(specular);
    }
}
