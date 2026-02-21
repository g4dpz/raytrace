package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class AdvancedSceneRenderer {
    
    public static void main(String[] args) {
        RenderWorld world = new RenderWorld();
        
        // Add lights
        PointLight light1 = new PointLight(new Point(-10, 10, -10), new Colour(0.7, 0.7, 0.7));
        PointLight light2 = new PointLight(new Point(10, 10, -10), new Colour(0.3, 0.3, 0.3));
        world.addLight(light1);
        world.addLight(light2);
        
        // Floor with checker pattern
        Plane floor = new Plane();
        Material floorMaterial = new Material();
        floorMaterial.setPattern(new CheckerPattern(new Colour(0.9, 0.9, 0.9), new Colour(0.1, 0.1, 0.1)));
        floorMaterial.setSpecular(0);
        floorMaterial.setReflective(0.1);
        floor.setMaterial(floorMaterial);
        world.addObject(floor);
        
        // Glass sphere with refraction
        Sphere glassSphere = new Sphere();
        glassSphere.setTransform(Matrix.translation(-1.5, 1, 0.5));
        Material glassMaterial = new Material();
        glassMaterial.setColor(new Colour(0.1, 0.1, 0.1));
        glassMaterial.setDiffuse(0.1);
        glassMaterial.setSpecular(0.9);
        glassMaterial.setShininess(300);
        glassMaterial.setTransparency(0.9);
        glassMaterial.setRefractiveIndex(1.5);
        glassSphere.setMaterial(glassMaterial);
        world.addObject(glassSphere);
        
        // Reflective sphere
        Sphere mirrorSphere = new Sphere();
        mirrorSphere.setTransform(Matrix.translation(1.5, 1, -0.5));
        Material mirrorMaterial = new Material();
        mirrorMaterial.setColor(new Colour(0.1, 0.1, 0.1));
        mirrorMaterial.setDiffuse(0.1);
        mirrorMaterial.setSpecular(0.9);
        mirrorMaterial.setShininess(300);
        mirrorMaterial.setReflective(0.9);
        mirrorSphere.setMaterial(mirrorMaterial);
        world.addObject(mirrorSphere);
        
        // Cylinder
        Cylinder cylinder = new Cylinder();
        cylinder.setMinimum(0);
        cylinder.setMaximum(2);
        cylinder.setClosed(true);
        cylinder.setTransform(
            Matrix.translation(0, 0, -2)
                .multiply(Matrix.scale(0.5, 1, 0.5))
        );
        Material cylinderMaterial = new Material();
        cylinderMaterial.setColor(new Colour(1, 0.3, 0.3));
        cylinderMaterial.setDiffuse(0.7);
        cylinderMaterial.setSpecular(0.3);
        cylinder.setMaterial(cylinderMaterial);
        world.addObject(cylinder);
        
        // Cone
        Cone cone = new Cone();
        cone.setMinimum(-1);
        cone.setMaximum(0);
        cone.setClosed(true);
        cone.setTransform(
            Matrix.translation(0, 1, 2)
                .multiply(Matrix.scale(0.5, 1, 0.5))
        );
        Material coneMaterial = new Material();
        coneMaterial.setColor(new Colour(0.3, 1, 0.3));
        coneMaterial.setDiffuse(0.7);
        coneMaterial.setSpecular(0.3);
        cone.setMaterial(coneMaterial);
        world.addObject(cone);
        
        // Cube
        Cube cube = new Cube();
        cube.setTransform(
            Matrix.translation(-3, 0.5, 0)
                .multiply(Matrix.rotationY(Math.PI / 4))
                .multiply(Matrix.scale(0.5, 0.5, 0.5))
        );
        Material cubeMaterial = new Material();
        cubeMaterial.setColor(new Colour(0.3, 0.3, 1));
        cubeMaterial.setDiffuse(0.7);
        cubeMaterial.setSpecular(0.3);
        cube.setMaterial(cubeMaterial);
        world.addObject(cube);
        
        // Group of small spheres
        Group group = new Group();
        for (int i = 0; i < 3; i++) {
            Sphere s = new Sphere();
            s.setTransform(
                Matrix.translation(3 + i * 0.6, 0.3, -1 + i * 0.3)
                    .multiply(Matrix.scale(0.3, 0.3, 0.3))
            );
            Material m = new Material();
            m.setColor(new Colour(1.0 - i * 0.3, 0.5, i * 0.3));
            m.setDiffuse(0.7);
            m.setSpecular(0.3);
            s.setMaterial(m);
            group.addChild(s);
        }
        world.addObject(group);
        
        // CSG - sphere with cube subtracted
        Sphere csgSphere = new Sphere();
        Cube csgCube = new Cube();
        csgCube.setTransform(Matrix.scale(0.7, 0.7, 0.7));
        CSG csg = new CSG(CSG.Operation.DIFFERENCE, csgSphere, csgCube);
        csg.setTransform(
            Matrix.translation(3, 1, 2)
                .multiply(Matrix.rotationY(Math.PI / 4))
                .multiply(Matrix.scale(0.6, 0.6, 0.6))
        );
        Material csgMaterial = new Material();
        csgMaterial.setColor(new Colour(1, 0.8, 0.2));
        csgMaterial.setDiffuse(0.7);
        csgMaterial.setSpecular(0.3);
        csg.setMaterial(csgMaterial);
        world.addObject(csg);
        
        // Camera
        Camera camera = new Camera(800, 400, Math.PI / 3);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 2.5, -8),
            new Point(0, 1, 0),
            new Vector(0, 1, 0)
        ));
        
        System.out.println("Rendering advanced scene with:");
        System.out.println("- Reflective and refractive materials");
        System.out.println("- Cylinders, cones, and cubes");
        System.out.println("- Groups and CSG operations");
        System.out.println("- Checker pattern on floor");
        System.out.println();
        System.out.println("This may take a few minutes...");
        
        long startTime = System.currentTimeMillis();
        Canvas canvas = camera.render(world, 5);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Rendering completed in " + (endTime - startTime) / 1000.0 + " seconds");
        System.out.println("Saving to file...");
        canvas.writeToPPM("advanced_scene.ppm");
        
        System.out.println("Done! Image saved to advanced_scene.ppm");
    }
}
