package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class SoftShadowsDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Soft Shadows Demo ===");
        System.out.println("Demonstrating area lights for soft shadows");
        System.out.println();
        
        RenderWorld world = new RenderWorld();
        
        // Create an area light
        AreaLight areaLight = new AreaLight(
            new Point(-5, 10, -5),      // corner
            new Vector(2, 0, 0),         // u vector (width)
            4,                           // u steps
            new Vector(0, 0, 2),         // v vector (depth)
            4,                           // v steps
            new Colour(1, 1, 1)          // intensity
        );
        
        // Add point lights at area light sample positions for rendering
        for (Point sample : areaLight.getSamples()) {
            PointLight light = new PointLight(
                sample, 
                areaLight.getIntensity().multiply(1.0 / areaLight.getSamples().size())
            );
            world.addLight(light);
        }
        
        // Floor
        Plane floor = new Plane();
        Material floorMaterial = new Material();
        floorMaterial.setColor(new Colour(0.8, 0.8, 0.8));
        floorMaterial.setSpecular(0.1);
        floor.setMaterial(floorMaterial);
        world.addObject(floor);
        
        // Wall behind
        Plane wall = new Plane();
        wall.setTransform(
            Matrix.translation(0, 0, 10)
                .multiply(Matrix.rotationX(Math.PI / 2))
        );
        Material wallMaterial = new Material();
        wallMaterial.setColor(new Colour(0.9, 0.9, 0.7));
        wallMaterial.setSpecular(0);
        wall.setMaterial(wallMaterial);
        world.addObject(wall);
        
        // Sphere casting shadow
        Sphere sphere = new Sphere();
        sphere.setTransform(Matrix.translation(0, 2, 0));
        Material sphereMaterial = new Material();
        sphereMaterial.setColor(new Colour(0.8, 0.2, 0.2));
        sphereMaterial.setDiffuse(0.7);
        sphereMaterial.setSpecular(0.3);
        sphere.setMaterial(sphereMaterial);
        world.addObject(sphere);
        
        // Cube casting shadow
        Cube cube = new Cube();
        cube.setTransform(
            Matrix.translation(-2.5, 0.5, 2)
                .multiply(Matrix.rotationY(Math.PI / 6))
                .multiply(Matrix.scale(0.5, 0.5, 0.5))
        );
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.2, 0.8, 0.2));
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.3);
        cube.setMaterial(cubeMaterial);
        world.addObject(cube);
        
        // Cylinder casting shadow
        Cylinder cylinder = new Cylinder();
        cylinder.setMinimum(0);
        cylinder.setMaximum(2);
        cylinder.setClosed(true);
        cylinder.setTransform(
            Matrix.translation(2.5, 0, 1)
                .multiply(Matrix.scale(0.4, 1, 0.4))
        );
        Material cylinderMaterial = new Material();
        cylinderMaterial.setColor(new Colour(0.2, 0.2, 0.8));
        cylinderMaterial.setDiffuse(0.7);
        cylinderMaterial.setSpecular(0.3);
        cylinder.setMaterial(cylinderMaterial);
        world.addObject(cylinder);
        
        // Camera
        ParallelCamera camera = new ParallelCamera(800, 600, Math.PI / 3);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 5, -10),
            new Point(0, 1, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Area light configuration:");
        System.out.println("  Size: 2x2 units");
        System.out.println("  Samples: " + areaLight.getSamples().size());
        System.out.println("  Position: (-5, 10, -5)");
        System.out.println();
        System.out.println("Rendering with soft shadows...");
        
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world, 5);
        long endTime = System.currentTimeMillis();
        
        double seconds = (endTime - startTime) / 1000.0;
        System.out.println();
        System.out.println("Rendering completed in " + String.format("%.2f", seconds) + " seconds");
        
        canvas.writeToPPM("soft_shadows.ppm");
        System.out.println("Saved to: soft_shadows.ppm");
        System.out.println();
        System.out.println("Note: Soft shadows are created by using multiple point lights");
        System.out.println("distributed across the area light surface.");
    }
}
