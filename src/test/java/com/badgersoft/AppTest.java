package com.badgersoft;

import com.badgersoft.raytrace.elements.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void testGetMaxHelper() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(10.0, 20.0, 0.0));
        points.add(new Point(30.0, 15.0, 0.0));
        points.add(new Point(5.0, 25.0, 0.0));
        
        Tuple result = getMax(points);
        
        assertEquals(30.0, result.getX(), 0.0001);
        assertEquals(25.0, result.getY(), 0.0001);
        assertEquals(0.0, result.getZ(), 0.0001);
        assertEquals(0.0, result.getW(), 0.0001);
    }

    @Test
    public void testGetMaxWithSinglePoint() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(100.0, 200.0, 0.0));
        
        Tuple result = getMax(points);
        
        assertEquals(100.0, result.getX(), 0.0001);
        assertEquals(200.0, result.getY(), 0.0001);
    }

    @Test
    public void testGetMaxWithZeroValues() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0.0, 0.0, 0.0));
        points.add(new Point(0.0, 0.0, 0.0));
        
        Tuple result = getMax(points);
        
        assertEquals(0.0, result.getX(), 0.0001);
        assertEquals(0.0, result.getY(), 0.0001);
    }

    @Test
    public void testProjectileSimulation() {
        Point point = new Point(0.0, 1.0, 0.0);
        Vector vector = new Vector(1.0, 1.8, 0.0);
        Projectile projectile = new Projectile(point, vector.normalise().multiply(11.25));
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        World world = new World(gravity, wind);
        
        List<Point> points = new ArrayList<>();
        int iterations = 0;
        
        while (projectile.getPoint().getY() > 0 && iterations < 1000) {
            points.add(projectile.getPoint());
            projectile = Projectile.tick(world, projectile);
            iterations++;
        }
        
        assertTrue("Should generate multiple trajectory points", points.size() > 1);
        assertTrue("Should complete in reasonable iterations", iterations < 1000);
        assertTrue("Final Y position should be at or below 0", projectile.getPoint().getY() <= 0);
    }

    @Test
    public void testProjectileReachesMaxHeight() {
        Point point = new Point(0.0, 1.0, 0.0);
        Vector vector = new Vector(1.0, 1.8, 0.0);
        Projectile projectile = new Projectile(point, vector.normalise().multiply(11.25));
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        World world = new World(gravity, wind);
        
        double maxHeight = 0.0;
        
        while (projectile.getPoint().getY() > 0) {
            if (projectile.getPoint().getY() > maxHeight) {
                maxHeight = projectile.getPoint().getY();
            }
            projectile = Projectile.tick(world, projectile);
        }
        
        assertTrue("Projectile should reach height greater than starting position", maxHeight > 1.0);
    }

    @Test
    public void testProjectileMovesHorizontally() {
        Point point = new Point(0.0, 1.0, 0.0);
        Vector vector = new Vector(1.0, 1.8, 0.0);
        Projectile projectile = new Projectile(point, vector.normalise().multiply(11.25));
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        World world = new World(gravity, wind);
        
        double startX = projectile.getPoint().getX();
        
        while (projectile.getPoint().getY() > 0) {
            projectile = Projectile.tick(world, projectile);
        }
        
        double endX = projectile.getPoint().getX();
        
        assertTrue("Projectile should move horizontally", endX > startX);
    }

    @Test
    public void testCanvasCoordinateMapping() {
        Canvas canvas = new Canvas(910, 550);
        Colour colour = new Colour(1.0, 1.0, 1.0);
        
        // Test that we can write to canvas without exceptions
        canvas.writePixel(colour, 0, 0);
        canvas.writePixel(colour, 909, 549);
        
        // Verify canvas dimensions
        assertEquals(910, canvas.getWidth());
        assertEquals(550, canvas.getHeight());
    }

    @Test
    public void testNormalizedVectorMultiplication() {
        Vector vector = new Vector(1.0, 1.8, 0.0);
        Vector normalized = vector.normalise();
        Vector scaled = normalized.multiply(11.25);
        
        // Verify the magnitude is approximately 11.25
        double magnitude = Math.sqrt(
            scaled.getX() * scaled.getX() + 
            scaled.getY() * scaled.getY() + 
            scaled.getZ() * scaled.getZ()
        );
        
        assertEquals(11.25, magnitude, 0.0001);
    }

    // Helper method from App class
    private static Tuple getMax(List<Point> points) {
        double x = 0.0;
        double y = 0.0;

        for (Point point : points) {
            if (point.getX() > x) {
                x = point.getX();
            }
            if (point.getY() > y) {
                y = point.getY();
            }
        }

        return new Tuple(x, y, 0, 0);
    }
}
