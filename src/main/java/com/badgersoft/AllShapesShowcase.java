package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class AllShapesShowcase {
    public static void main(String[] args) {
        System.out.println("Rendering all primitive shapes showcase...");
        
        // Create a simple floor (no reflections)
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -1, 0));
        Material floorMaterial = new Material();
        floorMaterial.setColor(new Colour(0.9, 0.9, 0.9));
        floorMaterial.setSpecular(0.0);
        floorMaterial.setReflective(0.0);  // No reflections
        floor.setMaterial(floorMaterial);
        
        // Spacing between shapes
        double spacing = 2.5;
        double startX = -9.0;
        int shapeIndex = 0;
        
        // 1. SPHERE
        Sphere sphere = new Sphere();
        sphere.setTransform(Matrix.translation(startX + spacing * shapeIndex++, 0, 0));
        Material sphereMaterial = new Material();
        sphereMaterial.setColor(new Colour(1.0, 0.2, 0.2));  // Red
        sphereMaterial.setDiffuse(0.7);
        sphereMaterial.setSpecular(0.3);
        sphere.setMaterial(sphereMaterial);
        
        // 2. CUBE
        Cube cube = new Cube();
        cube.setTransform(Matrix.translation(startX + spacing * shapeIndex++, 0, 0));
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.2, 1.0, 0.2));  // Green
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.3);
        cube.setMaterial(cubeMaterial);
        
        // 3. CYLINDER
        Cylinder cylinder = new Cylinder();
        cylinder.setMinimum(-1);
        cylinder.setMaximum(1);
        cylinder.setClosed(true);
        cylinder.setTransform(Matrix.translation(startX + spacing * shapeIndex++, 0, 0));
        Material cylinderMaterial = new Material();
        cylinderMaterial.setColor(new Colour(0.2, 0.2, 1.0));  // Blue
        cylinderMaterial.setDiffuse(0.7);
        cylinderMaterial.setSpecular(0.3);
        cylinder.setMaterial(cylinderMaterial);
        
        // 4. CONE
        Cone cone = new Cone();
        cone.setMinimum(-1);
        cone.setMaximum(0);
        cone.setClosed(true);
        cone.setTransform(Matrix.translation(startX + spacing * shapeIndex++, 0, 0));
        Material coneMaterial = new Material();
        coneMaterial.setColor(new Colour(1.0, 1.0, 0.2));  // Yellow
        coneMaterial.setDiffuse(0.7);
        coneMaterial.setSpecular(0.3);
        cone.setMaterial(coneMaterial);
        
        // 5. PLANE (small square to show it)
        Plane planeShape = new Plane();
        planeShape.setTransform(
            Matrix.translation(startX + spacing * shapeIndex++, 0, 0)
                .multiply(Matrix.rotationX(Math.PI / 2))
                .multiply(Matrix.scale(0.8, 0.8, 0.8))
        );
        Material planeMaterial = new Material();
        planeMaterial.setColor(new Colour(1.0, 0.5, 0.0));  // Orange
        planeMaterial.setDiffuse(0.7);
        planeMaterial.setSpecular(0.3);
        planeShape.setMaterial(planeMaterial);
        
        // 6. TRIANGLE
        Triangle triangle = new Triangle(
            new Point(0, 1, 0),
            new Point(-0.8, -1, 0),
            new Point(0.8, -1, 0)
        );
        triangle.setTransform(Matrix.translation(startX + spacing * shapeIndex++, 0, 0));
        Material triangleMaterial = new Material();
        triangleMaterial.setColor(new Colour(0.5, 0.0, 1.0));  // Purple
        triangleMaterial.setDiffuse(0.7);
        triangleMaterial.setSpecular(0.3);
        triangle.setMaterial(triangleMaterial);
        
        // 7. TORUS
        Torus torus = new Torus(0.7, 0.25);
        torus.setTransform(
            Matrix.translation(startX + spacing * shapeIndex++, 0, 0)
                .multiply(Matrix.rotationX(Math.PI / 3))
        );
        Material torusMaterial = new Material();
        torusMaterial.setColor(new Colour(0.0, 1.0, 1.0));  // Cyan
        torusMaterial.setDiffuse(0.7);
        torusMaterial.setSpecular(0.3);
        torus.setMaterial(torusMaterial);
        
        // 8. CSG (Cube with hole)
        Cube csgCube = new Cube();
        Cylinder csgCylinder = new Cylinder();
        csgCylinder.setMinimum(-2);
        csgCylinder.setMaximum(2);
        csgCylinder.setTransform(Matrix.scale(0.4, 1, 0.4));
        CSG csgShape = new CSG(CSG.Operation.DIFFERENCE, csgCube, csgCylinder);
        csgShape.setTransform(
            Matrix.translation(startX + spacing * shapeIndex++, 0, 0)
                .multiply(Matrix.rotationY(Math.PI / 6))
        );
        Material csgMaterial = new Material();
        csgMaterial.setColor(new Colour(1.0, 0.0, 1.0));  // Magenta
        csgMaterial.setDiffuse(0.7);
        csgMaterial.setSpecular(0.3);
        csgCube.setMaterial(csgMaterial);
        
        // Create lights
        PointLight light1 = new PointLight(
            new Point(-10, 10, -10),
            new Colour(0.9, 0.9, 0.9)
        );
        
        PointLight light2 = new PointLight(
            new Point(10, 10, -10),
            new Colour(0.5, 0.5, 0.5)
        );
        
        // Build world
        RenderWorld world = new RenderWorld();
        world.addObject(sphere);
        world.addObject(cube);
        world.addObject(cylinder);
        world.addObject(cone);
        world.addObject(planeShape);
        world.addObject(triangle);
        world.addObject(torus);
        world.addObject(csgShape);
        world.addObject(floor);
        world.addLight(light1);
        world.addLight(light2);
        
        // Create camera
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + threads + " threads");
        
        ParallelCamera camera = new ParallelCamera(1600, 600, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 5, -12),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("\nShapes in order (left to right):");
        System.out.println("1. Sphere (red)");
        System.out.println("2. Cube (green)");
        System.out.println("3. Cylinder (blue)");
        System.out.println("4. Cone (yellow)");
        System.out.println("5. Plane (orange)");
        System.out.println("6. Triangle (purple)");
        System.out.println("7. Torus (cyan)");
        System.out.println("8. CSG - Cube with hole (magenta)");
        
        System.out.println("\nStarting render...");
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Rendering completed in " + (elapsed / 1000.0) + " seconds");
        
        // Save as PPM
        canvas.writeToPPM("all_shapes.ppm");
        System.out.println("Saved to all_shapes.ppm");
        
        // Convert to PNG
        System.out.println("Converting to PNG...");
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sips", "-s", "format", "png", "all_shapes.ppm", "--out", "all_shapes.png"}
            );
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Saved to all_shapes.png");
                
                // Get file size
                java.io.File file = new java.io.File("all_shapes.png");
                long fileSize = file.length();
                System.out.println("File size: " + (fileSize / 1024) + " KB");
            }
        } catch (Exception e) {
            System.out.println("Could not convert to PNG: " + e.getMessage());
        }
        
        System.out.println("\nDone!");
    }
}
