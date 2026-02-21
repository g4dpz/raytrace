package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class CubeWithHoleDemo {
    public static void main(String[] args) {
        System.out.println("Rendering cube with cylindrical hole...");
        
        // Create a cube
        Cube cube = new Cube();
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.8, 0.3, 0.2));  // Reddish
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.3);
        cube.setMaterial(cubeMaterial);
        
        // Create a cylinder for the hole (going through Y axis)
        Cylinder cylinder = new Cylinder();
        cylinder.setMinimum(-2);  // Extend beyond cube
        cylinder.setMaximum(2);
        cylinder.setTransform(Matrix.scale(0.4, 1, 0.4));  // Make it thinner
        Material cylinderMaterial = new Material();
        cylinderMaterial.setColor(new Colour(0.5, 0.5, 0.5));
        cylinder.setMaterial(cylinderMaterial);
        
        // Create CSG difference: cube - cylinder = cube with hole
        CSG cubeWithHole = new CSG(CSG.Operation.DIFFERENCE, cube, cylinder);
        cubeWithHole.setTransform(
            Matrix.rotationY(Math.PI / 6)
                .multiply(Matrix.rotationX(Math.PI / 8))
        );
        
        // Add a floor
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -2, 0));
        Material floorMaterial = new Material();
        CheckerPattern pattern = new CheckerPattern(
            new Colour(0.2, 0.2, 0.2),
            new Colour(0.8, 0.8, 0.8)
        );
        pattern.setTransform(Matrix.scale(0.5, 0.5, 0.5));
        floorMaterial.setPattern(pattern);
        floorMaterial.setSpecular(0.2);
        floorMaterial.setReflective(0.3);
        floor.setMaterial(floorMaterial);
        
        // Add a small sphere to show the hole goes through
        Sphere sphere = new Sphere();
        sphere.setTransform(
            Matrix.translation(0, 0, 0)
                .multiply(Matrix.scale(0.3, 0.3, 0.3))
        );
        Material sphereMaterial = new Material();
        sphereMaterial.setColor(new Colour(0.2, 0.6, 0.9));  // Blue
        sphereMaterial.setDiffuse(0.7);
        sphereMaterial.setSpecular(0.9);
        sphereMaterial.setShininess(300);
        sphere.setMaterial(sphereMaterial);
        
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
        world.addObject(cubeWithHole);
        world.addObject(sphere);
        world.addObject(floor);
        world.addLight(light1);
        world.addLight(light2);
        
        // Create camera
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + threads + " threads");
        
        ParallelCamera camera = new ParallelCamera(800, 600, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(3, 2, -4),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Starting render...");
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Rendering completed in " + (elapsed / 1000.0) + " seconds");
        
        // Save as PPM
        canvas.writeToPPM("cube_with_hole.ppm");
        System.out.println("Saved to cube_with_hole.ppm");
        
        // Convert to PNG
        System.out.println("Converting to PNG...");
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sips", "-s", "format", "png", "cube_with_hole.ppm", "--out", "cube_with_hole.png"}
            );
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Saved to cube_with_hole.png");
                
                // Get file size
                java.io.File file = new java.io.File("cube_with_hole.png");
                long fileSize = file.length();
                System.out.println("File size: " + (fileSize / 1024) + " KB");
            } else {
                System.out.println("Conversion failed");
            }
        } catch (Exception e) {
            System.out.println("Could not convert to PNG: " + e.getMessage());
        }
        
        System.out.println("\nCSG Operation: DIFFERENCE");
        System.out.println("  Cube - Cylinder = Cube with cylindrical hole");
        System.out.println("  Hole diameter: 0.8 units");
        System.out.println("  Cube size: 2×2×2 units");
        System.out.println("\nDone!");
    }
}
