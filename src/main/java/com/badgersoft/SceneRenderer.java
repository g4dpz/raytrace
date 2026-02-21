package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class SceneRenderer {
    
    public static void main(String[] args) {
        RenderWorld world = new RenderWorld();
        
        PointLight light = new PointLight(new Point(-10, 10, -10), new Colour(1, 1, 1));
        world.addLight(light);
        
        Sphere floor = new Sphere();
        floor.setTransform(Matrix.scale(10, 0.01, 10));
        Material floorMaterial = new Material();
        floorMaterial.setColor(new Colour(1, 0.9, 0.9));
        floorMaterial.setSpecular(0);
        floor.setMaterial(floorMaterial);
        world.addObject(floor);
        
        Sphere leftWall = new Sphere();
        leftWall.setTransform(
            Matrix.translation(0, 0, 5)
                .multiply(Matrix.rotationY(-Math.PI / 4))
                .multiply(Matrix.rotationX(Math.PI / 2))
                .multiply(Matrix.scale(10, 0.01, 10))
        );
        leftWall.setMaterial(floorMaterial);
        world.addObject(leftWall);
        
        Sphere rightWall = new Sphere();
        rightWall.setTransform(
            Matrix.translation(0, 0, 5)
                .multiply(Matrix.rotationY(Math.PI / 4))
                .multiply(Matrix.rotationX(Math.PI / 2))
                .multiply(Matrix.scale(10, 0.01, 10))
        );
        rightWall.setMaterial(floorMaterial);
        world.addObject(rightWall);
        
        Sphere middle = new Sphere();
        middle.setTransform(Matrix.translation(-0.5, 1, 0.5));
        Material middleMaterial = new Material();
        middleMaterial.setColor(new Colour(0.1, 1, 0.5));
        middleMaterial.setDiffuse(0.7);
        middleMaterial.setSpecular(0.3);
        middle.setMaterial(middleMaterial);
        world.addObject(middle);
        
        Sphere right = new Sphere();
        right.setTransform(
            Matrix.translation(1.5, 0.5, -0.5)
                .multiply(Matrix.scale(0.5, 0.5, 0.5))
        );
        Material rightMaterial = new Material();
        rightMaterial.setColor(new Colour(0.5, 1, 0.1));
        rightMaterial.setDiffuse(0.7);
        rightMaterial.setSpecular(0.3);
        right.setMaterial(rightMaterial);
        world.addObject(right);
        
        Sphere left = new Sphere();
        left.setTransform(
            Matrix.translation(-1.5, 0.33, -0.75)
                .multiply(Matrix.scale(0.33, 0.33, 0.33))
        );
        Material leftMaterial = new Material();
        leftMaterial.setColor(new Colour(1, 0.8, 0.1));
        leftMaterial.setDiffuse(0.7);
        leftMaterial.setSpecular(0.3);
        left.setMaterial(leftMaterial);
        world.addObject(left);
        
        Camera camera = new Camera(400, 200, Math.PI / 3);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 1.5, -5),
            new Point(0, 1, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Rendering scene...");
        Canvas canvas = camera.render(world, 5);
        
        System.out.println("Saving to file...");
        canvas.writeToPPM("scene.ppm");
        
        System.out.println("Done! Image saved to scene.ppm");
    }
}
