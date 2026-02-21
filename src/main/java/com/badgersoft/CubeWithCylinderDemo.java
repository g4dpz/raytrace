package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class CubeWithCylinderDemo {
    public static void main(String[] args) {
        System.out.println("Rendering cube with cylinder on top...");
        
        // Floor
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -1.5, 0));
        Material floorMaterial = new Material();
        floorMaterial.setColor(new Colour(0.9, 0.9, 0.9));
        floorMaterial.setSpecular(0.0);
        floor.setMaterial(floorMaterial);
        
        // Cube (bottom)
        Cube cube = new Cube();
        cube.setTransform(Matrix.translation(0, 0, 0));
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.2, 0.6, 1.0));  // Blue
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.3);
        cube.setMaterial(cubeMaterial);
        
        // Cylinder (on top)
        Cylinder cylinder = new Cylinder();
        cylinder.setMinimum(0);
        cylinder.setMaximum(1.5);
        cylinder.setClosed(true);
        cylinder.setTransform(
            Matrix.translation(0, 1, 0)
                .multiply(Matrix.scale(0.5, 1, 0.5))
        );
        Material cylinderMaterial = new Material();
        cylinderMaterial.setColor(new Colour(1.0, 0.3, 0.2));  // Red
        cylinderMaterial.setDiffuse(0.7);
        cylinderMaterial.setSpecular(0.3);
        cylinder.setMaterial(cylinderMaterial);
        
        // Lights
        PointLight light1 = new PointLight(
            new Point(-5, 8, -5),
            new Colour(0.9, 0.9, 0.9)
        );
        
        PointLight light2 = new PointLight(
            new Point(5, 5, -5),
            new Colour(0.4, 0.4, 0.4)
        );
        
        // Build world
        RenderWorld world = new RenderWorld();
        world.addObject(floor);
        world.addObject(cube);
        world.addObject(cylinder);
        world.addLight(light1);
        world.addLight(light2);
        
        // Camera
        int threads = Runtime.getRuntime().availableProcessors();
        ParallelCamera camera = new ParallelCamera(800, 600, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(3, 4, -5),
            new Point(0, 1, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Rendering with " + threads + " threads...");
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Completed in " + (elapsed / 1000.0) + " seconds");
        
        canvas.writeToPPM("cube_cylinder.ppm");
        System.out.println("Saved to cube_cylinder.ppm");
        
        // Convert to PNG
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sips", "-s", "format", "png", "cube_cylinder.ppm", "--out", "cube_cylinder.png"}
            );
            if (process.waitFor() == 0) {
                System.out.println("Saved to cube_cylinder.png");
            }
        } catch (Exception e) {
            System.out.println("PNG conversion failed: " + e.getMessage());
        }
        
        System.out.println("Done!");
    }
}
