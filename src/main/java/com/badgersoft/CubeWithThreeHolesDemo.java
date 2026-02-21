package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class CubeWithThreeHolesDemo {
    public static void main(String[] args) {
        System.out.println("Rendering cube with three perpendicular holes...");
        
        // Create a cube
        Cube cube = new Cube();
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.9, 0.7, 0.2));  // Golden
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.6);
        cubeMaterial.setShininess(200);
        cube.setMaterial(cubeMaterial);
        
        // Create cylinder for hole through Y axis (vertical)
        Cylinder cylinderY = new Cylinder();
        cylinderY.setMinimum(-2);
        cylinderY.setMaximum(2);
        cylinderY.setTransform(Matrix.scale(0.35, 1, 0.35));
        
        // Create cylinder for hole through X axis (horizontal)
        Cylinder cylinderX = new Cylinder();
        cylinderX.setMinimum(-2);
        cylinderX.setMaximum(2);
        cylinderX.setTransform(
            Matrix.rotationZ(Math.PI / 2)
                .multiply(Matrix.scale(0.35, 1, 0.35))
        );
        
        // Create cylinder for hole through Z axis (depth)
        Cylinder cylinderZ = new Cylinder();
        cylinderZ.setMinimum(-2);
        cylinderZ.setMaximum(2);
        cylinderZ.setTransform(
            Matrix.rotationX(Math.PI / 2)
                .multiply(Matrix.scale(0.35, 1, 0.35))
        );
        
        // Build CSG: cube - cylinderY - cylinderX - cylinderZ
        // First subtract cylinderY from cube
        CSG step1 = new CSG(CSG.Operation.DIFFERENCE, cube, cylinderY);
        
        // Then subtract cylinderX from result
        CSG step2 = new CSG(CSG.Operation.DIFFERENCE, step1, cylinderX);
        
        // Finally subtract cylinderZ from result
        CSG cubeWithHoles = new CSG(CSG.Operation.DIFFERENCE, step2, cylinderZ);
        
        // Rotate the whole thing to show all three holes
        cubeWithHoles.setTransform(
            Matrix.rotationY(Math.PI / 6)
                .multiply(Matrix.rotationX(Math.PI / 6))
        );
        
        // Add a floor
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -2, 0));
        Material floorMaterial = new Material();
        CheckerPattern pattern = new CheckerPattern(
            new Colour(0.15, 0.15, 0.15),
            new Colour(0.85, 0.85, 0.85)
        );
        pattern.setTransform(Matrix.scale(0.5, 0.5, 0.5));
        floorMaterial.setPattern(pattern);
        floorMaterial.setSpecular(0.3);
        floorMaterial.setReflective(0.4);
        floor.setMaterial(floorMaterial);
        
        // Add backdrop
        Plane backdrop = new Plane();
        backdrop.setTransform(
            Matrix.translation(0, 0, 5)
                .multiply(Matrix.rotationX(Math.PI / 2))
        );
        Material backdropMaterial = new Material();
        GradientPattern gradientPattern = new GradientPattern(
            new Colour(0.1, 0.1, 0.2),
            new Colour(0.3, 0.4, 0.6)
        );
        gradientPattern.setTransform(
            Matrix.translation(-5, 0, 0)
                .multiply(Matrix.scale(10, 1, 1))
        );
        backdropMaterial.setPattern(gradientPattern);
        backdropMaterial.setSpecular(0.0);
        backdrop.setMaterial(backdropMaterial);
        
        // Create lights
        PointLight light1 = new PointLight(
            new Point(-8, 8, -8),
            new Colour(0.8, 0.8, 0.8)
        );
        
        PointLight light2 = new PointLight(
            new Point(5, 5, -5),
            new Colour(0.5, 0.5, 0.5)
        );
        
        PointLight light3 = new PointLight(
            new Point(0, 10, -3),
            new Colour(0.3, 0.3, 0.3)
        );
        
        // Build world
        RenderWorld world = new RenderWorld();
        world.addObject(cubeWithHoles);
        world.addObject(floor);
        world.addObject(backdrop);
        world.addLight(light1);
        world.addLight(light2);
        world.addLight(light3);
        
        // Create camera
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + threads + " threads");
        
        ParallelCamera camera = new ParallelCamera(1000, 800, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(3.5, 2.5, -4),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Starting render...");
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world);
        long elapsed = System.currentTimeMillis() - startTime;
        
        System.out.println("Rendering completed in " + (elapsed / 1000.0) + " seconds");
        
        // Save as PPM
        canvas.writeToPPM("cube_three_holes.ppm");
        System.out.println("Saved to cube_three_holes.ppm");
        
        // Convert to PNG
        System.out.println("Converting to PNG...");
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sips", "-s", "format", "png", "cube_three_holes.ppm", "--out", "cube_three_holes.png"}
            );
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Saved to cube_three_holes.png");
                
                // Get file size
                java.io.File file = new java.io.File("cube_three_holes.png");
                long fileSize = file.length();
                System.out.println("File size: " + (fileSize / 1024) + " KB");
            }
        } catch (Exception e) {
            System.out.println("Could not convert to PNG: " + e.getMessage());
        }
        
        System.out.println("\nCSG Operations:");
        System.out.println("  Step 1: Cube - CylinderY (vertical hole)");
        System.out.println("  Step 2: Result - CylinderX (horizontal hole)");
        System.out.println("  Step 3: Result - CylinderZ (depth hole)");
        System.out.println("  Final: Cube with three perpendicular holes");
        System.out.println("\n  Hole diameter: 0.7 units each");
        System.out.println("  Cube size: 2×2×2 units");
        System.out.println("  Holes intersect at center");
        System.out.println("\nDone!");
    }
}
