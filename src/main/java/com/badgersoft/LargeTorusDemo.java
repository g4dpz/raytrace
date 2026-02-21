package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class LargeTorusDemo {
    public static void main(String[] args) {
        System.out.println("Rendering large diameter torus with 20% tube radius...");
        
        // Large torus: major radius 3.0, minor radius 0.6 (20% of major)
        double majorRadius = 3.0;
        double minorRadius = majorRadius * 0.2;  // 20% = 0.6
        
        System.out.println("Major radius: " + majorRadius);
        System.out.println("Minor radius: " + minorRadius + " (20% of major)");
        System.out.println("Outer diameter: " + (2 * (majorRadius + minorRadius)));
        
        Torus torus = new Torus(majorRadius, minorRadius);
        torus.setTransform(
            Matrix.rotationX(Math.PI / 4)
                .multiply(Matrix.rotationY(Math.PI / 6))
        );
        
        // Vibrant gradient material
        Material torusMaterial = new Material();
        torusMaterial.setColor(new Colour(0.9, 0.2, 0.5));  // Pink/magenta
        torusMaterial.setDiffuse(0.7);
        torusMaterial.setSpecular(0.8);
        torusMaterial.setShininess(200);
        torusMaterial.setReflective(0.2);
        torus.setMaterial(torusMaterial);
        
        // Add a reflective floor with pattern
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -2, 0));
        Material floorMaterial = new Material();
        CheckerPattern pattern = new CheckerPattern(
            new Colour(0.2, 0.2, 0.2),
            new Colour(0.8, 0.8, 0.8)
        );
        pattern.setTransform(Matrix.scale(0.5, 0.5, 0.5));
        floorMaterial.setPattern(pattern);
        floorMaterial.setSpecular(0.3);
        floorMaterial.setReflective(0.4);
        floor.setMaterial(floorMaterial);
        
        // Add a sphere in the center (through the torus hole)
        Sphere centerSphere = new Sphere();
        centerSphere.setTransform(Matrix.scale(1.5, 1.5, 1.5));
        Material sphereMaterial = new Material();
        sphereMaterial.setColor(new Colour(0.2, 0.6, 0.9));  // Blue
        sphereMaterial.setDiffuse(0.6);
        sphereMaterial.setSpecular(0.9);
        sphereMaterial.setShininess(300);
        sphereMaterial.setReflective(0.3);
        centerSphere.setMaterial(sphereMaterial);
        
        // Add backdrop plane
        Plane backdrop = new Plane();
        backdrop.setTransform(
            Matrix.translation(0, 0, 10)
                .multiply(Matrix.rotationX(Math.PI / 2))
        );
        Material backdropMaterial = new Material();
        GradientPattern gradientPattern = new GradientPattern(
            new Colour(0.1, 0.1, 0.2),
            new Colour(0.4, 0.5, 0.7)
        );
        gradientPattern.setTransform(
            Matrix.translation(-8, 0, 0)
                .multiply(Matrix.scale(16, 1, 1))
        );
        backdropMaterial.setPattern(gradientPattern);
        backdropMaterial.setSpecular(0.0);
        backdrop.setMaterial(backdropMaterial);
        
        // Create dramatic lighting
        PointLight light1 = new PointLight(
            new Point(-10, 10, -10),
            new Colour(0.8, 0.8, 0.8)
        );
        
        PointLight light2 = new PointLight(
            new Point(10, 8, -8),
            new Colour(0.5, 0.5, 0.5)
        );
        
        PointLight light3 = new PointLight(
            new Point(0, 15, -5),
            new Colour(0.3, 0.3, 0.3)
        );
        
        // Build world
        RenderWorld world = new RenderWorld();
        world.addObject(torus);
        world.addObject(centerSphere);
        world.addObject(floor);
        world.addObject(backdrop);
        world.addLight(light1);
        world.addLight(light2);
        world.addLight(light3);
        
        // Create camera positioned to show the large torus
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + threads + " threads");
        
        ParallelCamera camera = new ParallelCamera(1200, 900, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 4, -12),   // Further back to see the large torus
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Starting render...");
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Rendering completed in " + (elapsed / 1000.0) + " seconds");
        
        // Save as PPM
        canvas.writeToPPM("large_torus.ppm");
        System.out.println("Saved to large_torus.ppm");
        
        // Convert to PNG
        System.out.println("Converting to PNG...");
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sips", "-s", "format", "png", "large_torus.ppm", "--out", "large_torus.png"}
            );
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Saved to large_torus.png");
                
                // Get file size
                java.io.File file = new java.io.File("large_torus.png");
                long fileSize = file.length();
                System.out.println("File size: " + (fileSize / 1024) + " KB");
            } else {
                System.out.println("Conversion failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            System.out.println("Could not convert to PNG: " + e.getMessage());
            System.out.println("You can manually convert large_torus.ppm to PNG");
        }
        
        System.out.println("\nTorus specifications:");
        System.out.println("  Major radius: " + majorRadius);
        System.out.println("  Minor radius: " + minorRadius + " (20% of major)");
        System.out.println("  Outer diameter: " + (2 * (majorRadius + minorRadius)));
        System.out.println("  Inner hole diameter: " + (2 * (majorRadius - minorRadius)));
        System.out.println("  Tube diameter: " + (2 * minorRadius));
        System.out.println("\nDone!");
    }
}
