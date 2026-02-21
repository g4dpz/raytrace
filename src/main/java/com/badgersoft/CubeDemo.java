package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class CubeDemo {
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
        
        // Create camera
        Camera camera = new Camera(400, 300, Math.PI / 3);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 1.5, -5),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Rendering cube with point light...");
        Canvas canvas = camera.render(world);
        
        System.out.println("Saving to cube_demo.ppm");
        canvas.writeToPPM("cube_demo.ppm");
        System.out.println("Done!");
    }
}
