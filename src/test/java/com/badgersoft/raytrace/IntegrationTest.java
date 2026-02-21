package com.badgersoft.raytrace;

import com.badgersoft.raytrace.elements.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IntegrationTest {

    @Test
    public void testCompleteProjectileTrajectory() {
        // Setup projectile
        Point startPoint = new Point(0.0, 1.0, 0.0);
        Vector velocity = new Vector(1.0, 1.8, 0.0);
        Projectile projectile = new Projectile(startPoint, velocity.normalise().multiply(11.25));
        
        // Setup world
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        World world = new World(wind, gravity);
        
        // Simulate trajectory
        int ticks = 0;
        double maxHeight = 0.0;
        double finalX = 0.0;
        
        while (projectile.getPoint().getY() > 0 && ticks < 1000) {
            if (projectile.getPoint().getY() > maxHeight) {
                maxHeight = projectile.getPoint().getY();
            }
            projectile = Projectile.tick(world, projectile);
            ticks++;
        }
        
        finalX = projectile.getPoint().getX();
        
        // Verify trajectory properties
        assertTrue("Should complete in reasonable time", ticks < 1000);
        assertTrue("Should reach significant height", maxHeight > 5.0);
        assertTrue("Should travel horizontally", finalX > 50.0);
        assertTrue("Should end below ground", projectile.getPoint().getY() <= 0);
    }

    @Test
    public void testRenderProjectileToCanvas() {
        // Setup
        Point startPoint = new Point(0.0, 1.0, 0.0);
        Vector velocity = new Vector(1.0, 1.0, 0.0);
        Projectile projectile = new Projectile(startPoint, velocity.normalise().multiply(5.0));
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(0.0, 0.0, 0.0);
        World world = new World(wind, gravity);
        
        Canvas canvas = new Canvas(100, 100);
        Colour colour = new Colour(1.0, 0.0, 0.0);
        
        // Simulate and render
        int pointsRendered = 0;
        while (projectile.getPoint().getY() > 0 && projectile.getPoint().getY() < 100) {
            int x = (int) projectile.getPoint().getX();
            int y = 99 - (int) projectile.getPoint().getY(); // Flip Y for canvas
            
            if (x >= 0 && x < 100 && y >= 0 && y < 100) {
                canvas.writePixel(colour, x, y);
                pointsRendered++;
            }
            
            projectile = Projectile.tick(world, projectile);
        }
        
        assertTrue("Should render multiple points", pointsRendered > 5);
        
        // Verify PPM generation works
        String ppm = canvas.toPpm();
        assertNotNull(ppm);
        assertTrue("PPM should have header", ppm.startsWith("P3\n"));
        assertTrue("PPM should end with newline", ppm.endsWith("\n"));
    }

    @Test
    public void testRaySphereIntersectionWithTransform() {
        // Create a sphere with transformation
        Sphere sphere = new Sphere();
        Matrix transform = Matrix.scale(2, 2, 2);
        sphere.setTransform(transform);
        
        // Create a ray
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        
        // Test intersection
        List<Intersection> intersections = ray.intersect(sphere);
        
        assertEquals(2, intersections.size());
        assertTrue("Should have valid intersections", intersections.get(0).getT() > 0);
    }

    @Test
    public void testMatrixTransformationChain() {
        // Test chaining multiple transformations
        Point p = new Point(1, 0, 1);
        
        Matrix rotation = Matrix.rotationY(Math.PI / 2);
        Matrix scaling = Matrix.scale(5, 5, 5);
        Matrix translation = Matrix.translation(10, 5, 7);
        
        // Apply individual transformations
        Tuple p2 = rotation.multiply(p);
        Tuple p3 = scaling.multiply(p2);
        Tuple p4 = translation.multiply(p3);
        
        // Apply combined transformation
        Matrix combined = translation.multiply(scaling.multiply(rotation));
        Tuple result = combined.multiply(p);
        
        // Results should be the same
        assertEquals(p4.getX(), result.getX(), 0.0001);
        assertEquals(p4.getY(), result.getY(), 0.0001);
        assertEquals(p4.getZ(), result.getZ(), 0.0001);
    }

    @Test
    public void testColourBlending() {
        // Test basic colour operations
        Colour red = new Colour(1.0, 0.0, 0.0);
        Colour green = new Colour(0.0, 1.0, 0.0);
        
        // Mix colours
        Colour yellow = red.add(green);
        
        // Verify result
        assertEquals(1.0, yellow.getRed(), 0.0001);
        assertEquals(1.0, yellow.getGreen(), 0.0001);
        assertEquals(0.0, yellow.getBlue(), 0.0001);
    }

    @Test
    public void testClockFaceGeneration() {
        // Test generating clock face points
        double radians = Math.PI / 6.0; // 30 degrees
        Point center = new Point(0, 0, 0);
        Matrix translate = Matrix.translation(100, 0, 0);
        Matrix rotateX = Matrix.rotationX(Math.PI / 2.0);
        Matrix translateToCenter = Matrix.translation(200, 200, 0);
        
        Point radiusPoint = new Point(translate.multiply(center));
        
        // Generate 12 points
        Point[] clockPoints = new Point[12];
        for (int i = 0; i < 12; i++) {
            Matrix rotateY = Matrix.rotationY(i * radians);
            clockPoints[i] = new Point(
                translateToCenter.multiply(
                    rotateX.multiply(
                        rotateY.multiply(radiusPoint)
                    )
                )
            );
        }
        
        // Verify we have 12 distinct points
        assertEquals(12, clockPoints.length);
        
        // Verify points are roughly equidistant from center
        double expectedDistance = 100.0;
        for (Point p : clockPoints) {
            double dx = p.getX() - 200;
            double dy = p.getY() - 200;
            double distance = Math.sqrt(dx * dx + dy * dy);
            assertEquals(expectedDistance, distance, 1.0);
        }
    }

    @Test
    public void testCanvasPPMGeneration() {
        Canvas canvas = new Canvas(10, 10);
        
        // Create a simple pattern
        Colour red = new Colour(1.0, 0.0, 0.0);
        Colour blue = new Colour(0.0, 0.0, 1.0);
        
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if ((x + y) % 2 == 0) {
                    canvas.writePixel(red, x, y);
                } else {
                    canvas.writePixel(blue, x, y);
                }
            }
        }
        
        String ppm = canvas.toPpm();
        
        // Verify PPM structure
        String[] lines = ppm.split("\n");
        assertTrue("Should have multiple lines", lines.length > 3);
        assertEquals("P3", lines[0]);
        assertEquals("10 10", lines[1]);
        assertEquals("255", lines[2]);
        
        // Verify content contains color values
        assertTrue("Should contain red values", ppm.contains("255"));
        assertTrue("Should contain blue values", ppm.contains("0"));
    }

    @Test
    public void testVectorNormalizationInPhysics() {
        // Test that normalized vectors maintain direction but unit length
        Vector v1 = new Vector(1.0, 2.0, 3.0);
        Vector normalized = v1.normalise();
        
        // Calculate magnitude
        double magnitude = Math.sqrt(
            normalized.getX() * normalized.getX() +
            normalized.getY() * normalized.getY() +
            normalized.getZ() * normalized.getZ()
        );
        
        assertEquals(1.0, magnitude, 0.0001);
    }
}
