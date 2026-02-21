package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class DepthOfFieldDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Depth of Field Demo ===");
        System.out.println("Demonstrating focal blur effect");
        System.out.println();
        
        RenderWorld world = new RenderWorld();
        
        // Add light
        PointLight light = new PointLight(new Point(-10, 10, -10), new Colour(1, 1, 1));
        world.addLight(light);
        
        // Floor with checker pattern
        Plane floor = new Plane();
        Material floorMaterial = new Material();
        Checker3DPattern checker = new Checker3DPattern(
            new Colour(0.8, 0.8, 0.8),
            new Colour(0.2, 0.2, 0.2)
        );
        floorMaterial.setPattern(checker);
        floorMaterial.setSpecular(0);
        floor.setMaterial(floorMaterial);
        world.addObject(floor);
        
        // Row of spheres at different distances
        for (int i = 0; i < 7; i++) {
            Sphere sphere = new Sphere();
            double z = -2 + i * 2; // From z=-2 to z=10
            sphere.setTransform(
                Matrix.translation(0, 1, z)
                    .multiply(Matrix.scale(0.5, 0.5, 0.5))
            );
            
            Material material = new Material();
            // Color varies with distance
            double hue = i / 6.0;
            material.setColor(new Colour(
                0.5 + 0.5 * Math.sin(hue * Math.PI * 2),
                0.5 + 0.5 * Math.sin((hue + 0.33) * Math.PI * 2),
                0.5 + 0.5 * Math.sin((hue + 0.67) * Math.PI * 2)
            ));
            material.setDiffuse(0.7);
            material.setSpecular(0.3);
            sphere.setMaterial(material);
            world.addObject(sphere);
        }
        
        // Add some cylinders for depth reference
        for (int i = 0; i < 3; i++) {
            Cylinder cylinder = new Cylinder();
            cylinder.setMinimum(0);
            cylinder.setMaximum(1.5);
            cylinder.setClosed(true);
            
            double x = -3 + i * 3;
            double z = i * 3;
            cylinder.setTransform(
                Matrix.translation(x, 0, z)
                    .multiply(Matrix.scale(0.3, 1, 0.3))
            );
            
            Material material = new Material();
            material.setColor(new Colour(0.8, 0.3, 0.3));
            material.setDiffuse(0.7);
            material.setSpecular(0.3);
            cylinder.setMaterial(material);
            world.addObject(cylinder);
        }
        
        // Parse command line arguments
        double aperture = 0.1;
        double focalDistance = 6.0;
        int samples = 16;
        
        if (args.length > 0) {
            aperture = Double.parseDouble(args[0]);
        }
        if (args.length > 1) {
            focalDistance = Double.parseDouble(args[1]);
        }
        if (args.length > 2) {
            samples = Integer.parseInt(args[2]);
        }
        
        System.out.println("Settings:");
        System.out.println("  Aperture: " + aperture);
        System.out.println("  Focal Distance: " + focalDistance);
        System.out.println("  Samples per pixel: " + samples);
        System.out.println();
        
        // Camera with depth of field
        DepthOfFieldCamera camera = new DepthOfFieldCamera(
            600, 400, Math.PI / 3,
            aperture, focalDistance, samples
        );
        
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 2, -8),
            new Point(0, 1, 0),
            new Vector(0, 1, 0)
        ));
        
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world, 5);
        long endTime = System.currentTimeMillis();
        
        double seconds = (endTime - startTime) / 1000.0;
        System.out.println();
        System.out.println("Rendering completed in " + String.format("%.2f", seconds) + " seconds");
        
        String filename = String.format("dof_a%.2f_f%.1f_s%d.ppm", aperture, focalDistance, samples);
        canvas.writeToPPM(filename);
        
        System.out.println("Saved to: " + filename);
        System.out.println();
        System.out.println("Usage: java DepthOfFieldDemo [aperture] [focalDistance] [samples]");
        System.out.println("  aperture: 0.0 (no blur) to 0.5 (heavy blur), default 0.1");
        System.out.println("  focalDistance: distance to focus plane, default 6.0");
        System.out.println("  samples: samples per pixel, default 16");
    }
}
