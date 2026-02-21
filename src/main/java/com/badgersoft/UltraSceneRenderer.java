package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class UltraSceneRenderer {
    
    public static void main(String[] args) {
        System.out.println("=== Ultra Ray Tracer Demo ===");
        System.out.println("Features: Multi-threading, Anti-aliasing, Procedural textures");
        System.out.println();
        
        RenderWorld world = new RenderWorld();
        
        // Multiple lights for better illumination
        PointLight light1 = new PointLight(new Point(-10, 10, -10), new Colour(0.6, 0.6, 0.6));
        PointLight light2 = new PointLight(new Point(10, 10, -10), new Colour(0.4, 0.4, 0.4));
        world.addLight(light1);
        world.addLight(light2);
        
        // Floor with perlin noise pattern
        Plane floor = new Plane();
        Material floorMaterial = new Material();
        PerlinPattern perlinFloor = new PerlinPattern(
            new Colour(0.2, 0.2, 0.2),
            new Colour(0.8, 0.8, 0.8),
            2.0,
            6
        );
        floorMaterial.setPattern(perlinFloor);
        floorMaterial.setSpecular(0.1);
        floorMaterial.setReflective(0.2);
        floor.setMaterial(floorMaterial);
        world.addObject(floor);
        
        // Glass sphere with UV checkers
        Sphere glassSphere = new Sphere();
        glassSphere.setTransform(Matrix.translation(-2, 1, 0));
        Material glassMaterial = new Material();
        CheckersUV uvCheckers = new CheckersUV(10, 5, 
            new Colour(0.9, 0.9, 0.9), 
            new Colour(0.1, 0.1, 0.1));
        TextureMap textureMap = new TextureMap(uvCheckers, "spherical");
        glassMaterial.setPattern(textureMap);
        glassMaterial.setDiffuse(0.2);
        glassMaterial.setSpecular(0.9);
        glassMaterial.setShininess(300);
        glassMaterial.setTransparency(0.8);
        glassMaterial.setRefractiveIndex(1.5);
        glassSphere.setMaterial(glassMaterial);
        world.addObject(glassSphere);
        
        // Marble sphere with perturbed stripes
        Sphere marbleSphere = new Sphere();
        marbleSphere.setTransform(Matrix.translation(0, 1, 2));
        Material marbleMaterial = new Material();
        StripePattern stripes = new StripePattern(
            new Colour(0.8, 0.2, 0.2),
            new Colour(0.2, 0.2, 0.8)
        );
        stripes.setTransform(Matrix.scale(0.2, 0.2, 0.2).multiply(Matrix.rotationY(Math.PI / 4)));
        PerturbedPattern marble = new PerturbedPattern(stripes, 0.3, 4);
        marbleMaterial.setPattern(marble);
        marbleMaterial.setDiffuse(0.7);
        marbleMaterial.setSpecular(0.3);
        marbleMaterial.setReflective(0.1);
        marbleSphere.setMaterial(marbleMaterial);
        world.addObject(marbleSphere);
        
        // Metallic sphere with blended pattern
        Sphere metallicSphere = new Sphere();
        metallicSphere.setTransform(Matrix.translation(2, 1, -1));
        Material metallicMaterial = new Material();
        GradientPattern gradient = new GradientPattern(
            new Colour(1, 0.8, 0.1),
            new Colour(0.8, 0.4, 0.1)
        );
        PerlinPattern noise = new PerlinPattern(
            new Colour(0.9, 0.7, 0.1),
            new Colour(1, 0.9, 0.3),
            5.0,
            3
        );
        BlendedPattern blended = new BlendedPattern(gradient, noise, 0.3);
        metallicMaterial.setPattern(blended);
        metallicMaterial.setDiffuse(0.3);
        metallicMaterial.setSpecular(0.9);
        metallicMaterial.setShininess(300);
        metallicMaterial.setReflective(0.7);
        metallicSphere.setMaterial(metallicMaterial);
        world.addObject(metallicSphere);
        
        // Cylinder with UV texture
        Cylinder cylinder = new Cylinder();
        cylinder.setMinimum(0);
        cylinder.setMaximum(2);
        cylinder.setClosed(true);
        cylinder.setTransform(
            Matrix.translation(-4, 0, -2)
                .multiply(Matrix.scale(0.5, 1, 0.5))
        );
        Material cylinderMaterial = new Material();
        CheckersUV cylinderCheckers = new CheckersUV(8, 4,
            new Colour(0.2, 0.8, 0.2),
            new Colour(0.8, 0.2, 0.2)
        );
        TextureMap cylinderTexture = new TextureMap(cylinderCheckers, "cylindrical");
        cylinderMaterial.setPattern(cylinderTexture);
        cylinderMaterial.setDiffuse(0.7);
        cylinderMaterial.setSpecular(0.3);
        cylinder.setMaterial(cylinderMaterial);
        world.addObject(cylinder);
        
        // Cone with perlin noise
        Cone cone = new Cone();
        cone.setMinimum(-1);
        cone.setMaximum(0);
        cone.setClosed(true);
        cone.setTransform(
            Matrix.translation(4, 1, -2)
                .multiply(Matrix.scale(0.7, 1.5, 0.7))
        );
        Material coneMaterial = new Material();
        PerlinPattern coneNoise = new PerlinPattern(
            new Colour(0.1, 0.3, 0.8),
            new Colour(0.3, 0.6, 1.0),
            3.0,
            5
        );
        coneMaterial.setPattern(coneNoise);
        coneMaterial.setDiffuse(0.7);
        coneMaterial.setSpecular(0.4);
        cone.setMaterial(coneMaterial);
        world.addObject(cone);
        
        // Small reflective spheres
        for (int i = 0; i < 5; i++) {
            Sphere s = new Sphere();
            double angle = i * Math.PI * 2 / 5;
            s.setTransform(
                Matrix.translation(Math.cos(angle) * 4, 0.3, Math.sin(angle) * 4)
                    .multiply(Matrix.scale(0.3, 0.3, 0.3))
            );
            Material m = new Material();
            m.setColor(new Colour(
                0.5 + 0.5 * Math.cos(angle),
                0.5 + 0.5 * Math.sin(angle),
                0.5 + 0.5 * Math.cos(angle + Math.PI / 2)
            ));
            m.setDiffuse(0.5);
            m.setSpecular(0.8);
            m.setReflective(0.5);
            s.setMaterial(m);
            world.addObject(s);
        }
        
        // Choose rendering mode
        String mode = args.length > 0 ? args[0] : "fast";
        
        long startTime = System.currentTimeMillis();
        Canvas canvas;
        
        switch (mode) {
            case "ultra":
                System.out.println("Mode: Ultra Quality (Anti-aliased 4x, 800x600)");
                AntiAliasedCamera aaCamera = new AntiAliasedCamera(800, 600, Math.PI / 3, 4);
                aaCamera.setTransform(Matrix.viewTransform(
                    new Point(0, 3, -10),
                    new Point(0, 1, 0),
                    new Vector(0, 1, 0)
                ));
                canvas = aaCamera.render(world, 5);
                break;
                
            case "high":
                System.out.println("Mode: High Quality (Multi-threaded, 800x600)");
                ParallelCamera pCamera = new ParallelCamera(800, 600, Math.PI / 3);
                pCamera.setTransform(Matrix.viewTransform(
                    new Point(0, 3, -10),
                    new Point(0, 1, 0),
                    new Vector(0, 1, 0)
                ));
                canvas = pCamera.render(world, 5);
                break;
                
            case "fast":
            default:
                System.out.println("Mode: Fast (Single-threaded, 400x300)");
                Camera camera = new Camera(400, 300, Math.PI / 3);
                camera.setTransform(Matrix.viewTransform(
                    new Point(0, 3, -10),
                    new Point(0, 1, 0),
                    new Vector(0, 1, 0)
                ));
                canvas = camera.render(world, 5);
                break;
        }
        
        long endTime = System.currentTimeMillis();
        double seconds = (endTime - startTime) / 1000.0;
        
        System.out.println();
        System.out.println("Rendering completed in " + String.format("%.2f", seconds) + " seconds");
        System.out.println("Saving to file...");
        
        String filename = "ultra_scene_" + mode + ".ppm";
        canvas.writeToPPM(filename);
        
        System.out.println("Done! Image saved to " + filename);
        System.out.println();
        System.out.println("Usage: java UltraSceneRenderer [fast|high|ultra]");
        System.out.println("  fast  - 400x300, single-threaded (default)");
        System.out.println("  high  - 800x600, multi-threaded");
        System.out.println("  ultra - 800x600, anti-aliased 4x");
    }
}
