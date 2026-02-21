package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class SpeedComparison {
    public static void main(String[] args) {
        System.out.println("=== Ray Tracer Speed Comparison ===\n");
        
        // Create scene
        Cube cube = new Cube();
        cube.setTransform(Matrix.rotationY(Math.PI / 4).multiply(Matrix.rotationX(Math.PI / 6)));
        
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.2, 0.6, 1.0));
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.3);
        cube.setMaterial(cubeMaterial);
        
        PointLight light = new PointLight(new Point(-5, 5, -5), new Colour(1, 1, 1));
        
        RenderWorld world = new RenderWorld();
        world.addObject(cube);
        world.addLight(light);
        
        // Test 1: Single-threaded
        System.out.println("Test 1: Single-threaded Camera");
        Camera singleCamera = new Camera(400, 300, Math.PI / 3);
        singleCamera.setTransform(Matrix.viewTransform(
            new Point(0, 1.5, -5),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        long start1 = System.currentTimeMillis();
        Canvas canvas1 = singleCamera.render(world);
        long time1 = System.currentTimeMillis() - start1;
        System.out.println("Time: " + time1 + "ms\n");
        
        // Test 2: Multi-threaded
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Test 2: ParallelCamera with " + threads + " threads");
        ParallelCamera parallelCamera = new ParallelCamera(400, 300, Math.PI / 3, threads);
        parallelCamera.setTransform(Matrix.viewTransform(
            new Point(0, 1.5, -5),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        long start2 = System.currentTimeMillis();
        Canvas canvas2 = parallelCamera.render(world);
        long time2 = System.currentTimeMillis() - start2;
        System.out.println("Time: " + time2 + "ms\n");
        
        // Results
        System.out.println("=== Results ===");
        System.out.println("Single-threaded: " + time1 + "ms");
        System.out.println("Multi-threaded:  " + time2 + "ms");
        System.out.println("Speedup:         " + String.format("%.2f", (double)time1 / time2) + "x");
        System.out.println("Efficiency:      " + String.format("%.1f", 100.0 * time1 / time2 / threads) + "%");
    }
}
