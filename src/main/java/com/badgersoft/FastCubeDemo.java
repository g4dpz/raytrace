package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class FastCubeDemo {
    public static void main(String[] args) {
        // Create a cube
        Cube cube = new Cube();
        cube.setTransform(Matrix.rotationY(Math.PI / 4).multiply(Matrix.rotationX(Math.PI / 6)));
        
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.2, 0.6, 1.0));
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.3);
        cube.setMaterial(cubeMaterial);
        
        // Create a point light
        PointLight light = new PointLight(new Point(-5, 5, -5), new Colour(1, 1, 1));
        
        // Create world
        RenderWorld world = new RenderWorld();
        world.addObject(cube);
        world.addLight(light);
        
        // Use ParallelCamera for multi-threaded rendering
        int threads = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + threads + " threads for rendering");
        
        ParallelCamera camera = new ParallelCamera(400, 300, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 1.5, -5),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        long startTime = System.currentTimeMillis();
        System.out.println("Rendering cube with point light (parallel)...");
        Canvas canvas = camera.render(world);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Rendering took " + (endTime - startTime) + "ms");
        System.out.println("Saving to fast_cube_demo.ppm");
        canvas.writeToPPM("fast_cube_demo.ppm");
        System.out.println("Done!");
    }
}
