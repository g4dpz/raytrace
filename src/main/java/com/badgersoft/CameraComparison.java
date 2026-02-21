package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

/**
 * Demonstrates all camera types and their performance characteristics
 */
public class CameraComparison {
    
    private static RenderWorld createScene() {
        // Create a colorful scene with multiple objects
        
        // Center sphere
        Sphere sphere1 = new Sphere();
        Material mat1 = new Material();
        mat1.setColor(new Colour(1.0, 0.2, 0.2));
        mat1.setDiffuse(0.7);
        mat1.setSpecular(0.3);
        sphere1.setMaterial(mat1);
        
        // Left sphere
        Sphere sphere2 = new Sphere();
        sphere2.setTransform(Matrix.translation(-2.5, 0, 0));
        Material mat2 = new Material();
        mat2.setColor(new Colour(0.2, 1.0, 0.2));
        mat2.setDiffuse(0.7);
        mat2.setSpecular(0.3);
        sphere2.setMaterial(mat2);
        
        // Right sphere
        Sphere sphere3 = new Sphere();
        sphere3.setTransform(Matrix.translation(2.5, 0, 0));
        Material mat3 = new Material();
        mat3.setColor(new Colour(0.2, 0.2, 1.0));
        mat3.setDiffuse(0.7);
        mat3.setSpecular(0.3);
        sphere3.setMaterial(mat3);
        
        // Floor
        Plane floor = new Plane();
        floor.setTransform(Matrix.translation(0, -1, 0));
        Material floorMat = new Material();
        CheckerPattern pattern = new CheckerPattern(
            new Colour(0.9, 0.9, 0.9),
            new Colour(0.1, 0.1, 0.1)
        );
        floorMat.setPattern(pattern);
        floorMat.setSpecular(0.0);
        floor.setMaterial(floorMat);
        
        // Light
        PointLight light = new PointLight(
            new Point(-5, 5, -5),
            new Colour(1, 1, 1)
        );
        
        // Build world
        RenderWorld world = new RenderWorld();
        world.addObject(sphere1);
        world.addObject(sphere2);
        world.addObject(sphere3);
        world.addObject(floor);
        world.addLight(light);
        
        return world;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Camera Implementation Comparison ===\n");
        
        RenderWorld world = createScene();
        int width = 400;
        int height = 300;
        
        // Camera transform
        Matrix transform = Matrix.viewTransform(
            new Point(0, 1.5, -5),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        );
        
        // Test 1: Regular Camera (single-threaded)
        System.out.println("1. Camera (single-threaded baseline)");
        Camera camera1 = new Camera(width, height, Math.PI / 3);
        camera1.setTransform(transform);
        
        long start1 = System.currentTimeMillis();
        Canvas canvas1 = camera1.render(world);
        long time1 = System.currentTimeMillis() - start1;
        
        canvas1.writeToPPM("camera_single.ppm");
        System.out.println("   Time: " + time1 + "ms");
        System.out.println("   Output: camera_single.ppm\n");
        
        // Test 2: ParallelCamera
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("2. ParallelCamera (" + threads + " threads)");
        ParallelCamera camera2 = new ParallelCamera(width, height, Math.PI / 3, threads);
        camera2.setTransform(transform);
        
        long start2 = System.currentTimeMillis();
        Canvas canvas2 = camera2.render(world);
        long time2 = System.currentTimeMillis() - start2;
        
        canvas2.writeToPPM("camera_parallel.ppm");
        System.out.println("   Time: " + time2 + "ms");
        System.out.println("   Speedup: " + String.format("%.2f", (double)time1 / time2) + "x");
        System.out.println("   Output: camera_parallel.ppm\n");
        
        // Test 3: AntiAliasedCamera (2x2 samples)
        System.out.println("3. AntiAliasedCamera (2x2 samples, " + threads + " threads)");
        AntiAliasedCamera camera3 = new AntiAliasedCamera(width, height, Math.PI / 3, 2, threads);
        camera3.setTransform(transform);
        
        long start3 = System.currentTimeMillis();
        Canvas canvas3 = camera3.render(world);
        long time3 = System.currentTimeMillis() - start3;
        
        canvas3.writeToPPM("camera_antialiased.ppm");
        System.out.println("   Time: " + time3 + "ms");
        System.out.println("   vs Single: " + String.format("%.2f", (double)time1 / time3) + "x");
        System.out.println("   Output: camera_antialiased.ppm (smoother edges)\n");
        
        // Test 4: DepthOfFieldCamera
        System.out.println("4. DepthOfFieldCamera (aperture=0.1, focal=5.0, 4 samples)");
        DepthOfFieldCamera camera4 = new DepthOfFieldCamera(
            width, height, Math.PI / 3,
            0.1,  // aperture
            5.0,  // focal distance
            4     // samples per pixel
        );
        camera4.setTransform(transform);
        
        long start4 = System.currentTimeMillis();
        Canvas canvas4 = camera4.render(world);
        long time4 = System.currentTimeMillis() - start4;
        
        canvas4.writeToPPM("camera_dof.ppm");
        System.out.println("   Time: " + time4 + "ms");
        System.out.println("   vs Single: " + String.format("%.2f", (double)time1 / time4) + "x");
        System.out.println("   Output: camera_dof.ppm (blurred background)\n");
        
        // Summary
        System.out.println("=== Summary ===");
        System.out.println("Resolution: " + width + "x" + height);
        System.out.println("CPU Cores: " + threads);
        System.out.println();
        System.out.println("Camera Type              Time      Speedup   Features");
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println(String.format("%-24s %6dms   %.2fx     Baseline",
            "Camera", time1, 1.0));
        System.out.println(String.format("%-24s %6dms   %.2fx     Multi-threaded",
            "ParallelCamera", time2, (double)time1/time2));
        System.out.println(String.format("%-24s %6dms   %.2fx     Anti-aliasing",
            "AntiAliasedCamera", time3, (double)time1/time3));
        System.out.println(String.format("%-24s %6dms   %.2fx     Depth of field",
            "DepthOfFieldCamera", time4, (double)time1/time4));
        System.out.println();
        System.out.println("Recommendation: Use ParallelCamera for best speed/quality ratio!");
    }
}
