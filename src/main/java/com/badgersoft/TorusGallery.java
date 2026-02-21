package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

/**
 * Renders multiple tori with different materials and arrangements
 */
public class TorusGallery {
    public static void main(String[] args) {
        System.out.println("Rendering Torus Gallery...");
        
        // Create three tori in a row
        
        // Left torus - metallic gold
        Torus torus1 = new Torus(0.8, 0.25);
        torus1.setTransform(
            Matrix.translation(-2.5, 0, 0)
                .multiply(Matrix.rotationX(Math.PI / 3))
                .multiply(Matrix.rotationY(Math.PI / 6))
        );
        Material mat1 = new Material();
        mat1.setColor(new Colour(1.0, 0.84, 0.0));  // Gold
        mat1.setDiffuse(0.6);
        mat1.setSpecular(0.9);
        mat1.setShininess(300);
        mat1.setReflective(0.4);
        torus1.setMaterial(mat1);
        
        // Center torus - glass/transparent
        Torus torus2 = new Torus(0.8, 0.25);
        torus2.setTransform(
            Matrix.translation(0, 0, 0)
                .multiply(Matrix.rotationX(Math.PI / 4))
                .multiply(Matrix.rotationZ(Math.PI / 6))
        );
        Material mat2 = new Material();
        mat2.setColor(new Colour(0.1, 0.3, 0.8));  // Blue
        mat2.setDiffuse(0.3);
        mat2.setSpecular(0.9);
        mat2.setShininess(300);
        mat2.setReflective(0.5);
        mat2.setTransparency(0.7);
        mat2.setRefractiveIndex(1.5);
        torus2.setMaterial(mat2);
        
        // Right torus - matte red
        Torus torus3 = new Torus(0.8, 0.25);
        torus3.setTransform(
            Matrix.translation(2.5, 0, 0)
                .multiply(Matrix.rotationY(Math.PI / 3))
                .multiply(Matrix.rotationX(-Math.PI / 6))
        );
        Material mat3 = new Material();
        mat3.setColor(new Colour(0.9, 0.1, 0.1));  // Red
        mat3.setDiffuse(0.8);
        mat3.setSpecular(0.3);
        mat3.setShininess(50);
        torus3.setMaterial(mat3);
        
        // Add a reflective floor
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -1.5, 0));
        Material floorMaterial = new Material();
        CheckerPattern pattern = new CheckerPattern(
            new Colour(0.15, 0.15, 0.15),
            new Colour(0.85, 0.85, 0.85)
        );
        pattern.setTransform(Matrix.scale(0.5, 0.5, 0.5));
        floorMaterial.setPattern(pattern);
        floorMaterial.setSpecular(0.4);
        floorMaterial.setReflective(0.3);
        floor.setMaterial(floorMaterial);
        
        // Add backdrop
        Plane backdrop = new Plane();
        backdrop.setTransform(
            Matrix.translation(0, 0, 8)
                .multiply(Matrix.rotationX(Math.PI / 2))
        );
        Material backdropMaterial = new Material();
        GradientPattern gradientPattern = new GradientPattern(
            new Colour(0.1, 0.1, 0.3),
            new Colour(0.5, 0.5, 0.8)
        );
        gradientPattern.setTransform(
            Matrix.translation(-5, 0, 0)
                .multiply(Matrix.scale(10, 1, 1))
        );
        backdropMaterial.setPattern(gradientPattern);
        backdropMaterial.setSpecular(0.0);
        backdrop.setMaterial(backdropMaterial);
        
        // Create multiple lights for dramatic effect
        PointLight light1 = new PointLight(
            new Point(-8, 8, -8),
            new Colour(0.7, 0.7, 0.7)
        );
        
        PointLight light2 = new PointLight(
            new Point(8, 5, -5),
            new Colour(0.5, 0.5, 0.5)
        );
        
        PointLight light3 = new PointLight(
            new Point(0, 10, -3),
            new Colour(0.3, 0.3, 0.3)
        );
        
        // Build world
        RenderWorld world = new RenderWorld();
        world.addObject(torus1);
        world.addObject(torus2);
        world.addObject(torus3);
        world.addObject(floor);
        world.addObject(backdrop);
        world.addLight(light1);
        world.addLight(light2);
        world.addLight(light3);
        
        // Create camera
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + threads + " threads");
        
        ParallelCamera camera = new ParallelCamera(1200, 800, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 2, -6),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Rendering completed in " + (elapsed / 1000.0) + " seconds");
        
        // Save as PPM
        canvas.writeToPPM("torus_gallery.ppm");
        System.out.println("Saved to torus_gallery.ppm");
        
        // Convert to PNG
        System.out.println("Converting to PNG...");
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sips", "-s", "format", "png", "torus_gallery.ppm", "--out", "torus_gallery.png"}
            );
            process.waitFor();
            System.out.println("Saved to torus_gallery.png");
        } catch (Exception e) {
            System.out.println("Could not convert to PNG: " + e.getMessage());
        }
        
        System.out.println("Done!");
    }
}
