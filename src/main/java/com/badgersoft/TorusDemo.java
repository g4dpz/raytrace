package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class TorusDemo {
    public static void main(String[] args) {
        System.out.println("Rendering torus (donut shape)...");
        
        // Create a torus with major radius 1.0 and minor radius 0.3
        Torus torus = new Torus(1.0, 0.3);
        torus.setTransform(
            Matrix.rotationX(Math.PI / 6)
                .multiply(Matrix.rotationY(Math.PI / 4))
        );
        
        // Shiny metallic material
        Material torusMaterial = new Material();
        torusMaterial.setColor(new Colour(0.8, 0.3, 0.1));
        torusMaterial.setDiffuse(0.7);
        torusMaterial.setSpecular(0.9);
        torusMaterial.setShininess(300);
        torusMaterial.setReflective(0.3);
        torus.setMaterial(torusMaterial);
        
        // Add a checkered floor
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -1.5, 0));
        Material floorMaterial = new Material();
        CheckerPattern pattern = new CheckerPattern(
            new Colour(0.9, 0.9, 0.9),
            new Colour(0.1, 0.1, 0.1)
        );
        floorMaterial.setPattern(pattern);
        floorMaterial.setSpecular(0.0);
        floorMaterial.setReflective(0.1);
        floor.setMaterial(floorMaterial);
        
        // Add a backdrop sphere
        Sphere backdrop = new Sphere();
        backdrop.setTransform(
            Matrix.translation(0, 0, 5)
                .multiply(Matrix.scale(3, 3, 3))
        );
        Material backdropMaterial = new Material();
        backdropMaterial.setColor(new Colour(0.2, 0.3, 0.5));
        backdropMaterial.setDiffuse(0.5);
        backdropMaterial.setSpecular(0.0);
        backdrop.setMaterial(backdropMaterial);
        
        // Create lights
        PointLight light1 = new PointLight(
            new Point(-5, 5, -5),
            new Colour(0.9, 0.9, 0.9)
        );
        
        PointLight light2 = new PointLight(
            new Point(5, 3, -3),
            new Colour(0.4, 0.4, 0.4)
        );
        
        // Build world
        RenderWorld world = new RenderWorld();
        world.addObject(torus);
        world.addObject(floor);
        world.addObject(backdrop);
        world.addLight(light1);
        world.addLight(light2);
        
        // Create camera with parallel rendering
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + threads + " threads");
        
        ParallelCamera camera = new ParallelCamera(800, 600, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 1.5, -4),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Rendering completed in " + elapsed + "ms");
        
        // Save as PPM
        canvas.writeToPPM("torus.ppm");
        System.out.println("Saved to torus.ppm");
        
        // Convert to PNG
        System.out.println("Converting to PNG...");
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sips", "-s", "format", "png", "torus.ppm", "--out", "torus.png"}
            );
            process.waitFor();
            System.out.println("Saved to torus.png");
        } catch (Exception e) {
            System.out.println("Could not convert to PNG: " + e.getMessage());
            System.out.println("You can manually convert torus.ppm to PNG");
        }
        
        System.out.println("Done!");
    }
}
