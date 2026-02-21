package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectileTest {

    @Test
    public void testDefaultConstructor() {
        Projectile projectile = new Projectile();
        assertNull(projectile.getPoint());
        assertNull(projectile.getVelocity());
    }

    @Test
    public void testConstructorWithParameters() {
        Point point = new Point(0.0, 1.0, 0.0);
        Vector velocity = new Vector(1.0, 1.0, 0.0);
        
        Projectile projectile = new Projectile(point, velocity);
        
        assertEquals(point, projectile.getPoint());
        assertEquals(velocity, projectile.getVelocity());
    }

    @Test
    public void testSetPoint() {
        Projectile projectile = new Projectile();
        Point point = new Point(5.0, 10.0, 0.0);
        
        projectile.setPoint(point);
        
        assertEquals(point, projectile.getPoint());
    }

    @Test
    public void testSetVelocity() {
        Projectile projectile = new Projectile();
        Vector velocity = new Vector(2.0, 3.0, 0.0);
        
        projectile.setVelocity(velocity);
        
        assertEquals(velocity, projectile.getVelocity());
    }

    @Test
    public void testTickWithGravityOnly() {
        Point startPoint = new Point(0.0, 10.0, 0.0);
        Vector startVelocity = new Vector(1.0, 0.0, 0.0);
        Projectile projectile = new Projectile(startPoint, startVelocity);
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(0.0, 0.0, 0.0);
        World world = new World(wind, gravity);
        
        Projectile newProjectile = Projectile.tick(world, projectile);
        
        assertEquals(1.0, newProjectile.getPoint().getX(), 0.0001);
        assertEquals(10.0, newProjectile.getPoint().getY(), 0.0001);
        assertEquals(1.0, newProjectile.getVelocity().getX(), 0.0001);
        assertEquals(-0.1, newProjectile.getVelocity().getY(), 0.0001);
    }

    @Test
    public void testTickWithGravityAndWind() {
        Point startPoint = new Point(0.0, 10.0, 0.0);
        Vector startVelocity = new Vector(1.0, 0.0, 0.0);
        Projectile projectile = new Projectile(startPoint, startVelocity);
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        World world = new World(wind, gravity);
        
        Projectile newProjectile = Projectile.tick(world, projectile);
        
        assertEquals(1.0, newProjectile.getPoint().getX(), 0.0001);
        assertEquals(10.0, newProjectile.getPoint().getY(), 0.0001);
        assertEquals(0.99, newProjectile.getVelocity().getX(), 0.0001);
        assertEquals(-0.1, newProjectile.getVelocity().getY(), 0.0001);
    }

    @Test
    public void testTickMultipleIterations() {
        Point startPoint = new Point(0.0, 1.0, 0.0);
        Vector startVelocity = new Vector(1.0, 1.8, 0.0);
        Projectile projectile = new Projectile(startPoint, startVelocity);
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        World world = new World(wind, gravity);
        
        // First tick
        projectile = Projectile.tick(world, projectile);
        assertEquals(1.0, projectile.getPoint().getX(), 0.0001);
        assertEquals(2.8, projectile.getPoint().getY(), 0.0001);
        
        // Second tick
        projectile = Projectile.tick(world, projectile);
        assertEquals(1.99, projectile.getPoint().getX(), 0.01);
        assertEquals(4.49, projectile.getPoint().getY(), 0.01);
    }

    @Test
    public void testTickWithNoForces() {
        Point startPoint = new Point(0.0, 0.0, 0.0);
        Vector startVelocity = new Vector(1.0, 1.0, 0.0);
        Projectile projectile = new Projectile(startPoint, startVelocity);
        
        Vector gravity = new Vector(0.0, 0.0, 0.0);
        Vector wind = new Vector(0.0, 0.0, 0.0);
        World world = new World(wind, gravity);
        
        Projectile newProjectile = Projectile.tick(world, projectile);
        
        assertEquals(1.0, newProjectile.getPoint().getX(), 0.0001);
        assertEquals(1.0, newProjectile.getPoint().getY(), 0.0001);
        assertEquals(1.0, newProjectile.getVelocity().getX(), 0.0001);
        assertEquals(1.0, newProjectile.getVelocity().getY(), 0.0001);
    }

    @Test
    public void testProjectileEventuallyFalls() {
        Point startPoint = new Point(0.0, 1.0, 0.0);
        Vector startVelocity = new Vector(1.0, 1.0, 0.0);
        Projectile projectile = new Projectile(startPoint, startVelocity);
        
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        Vector wind = new Vector(0.0, 0.0, 0.0);
        World world = new World(wind, gravity);
        
        int iterations = 0;
        while (projectile.getPoint().getY() > 0 && iterations < 1000) {
            projectile = Projectile.tick(world, projectile);
            iterations++;
        }
        
        assertTrue("Projectile should eventually fall below y=0", projectile.getPoint().getY() <= 0);
        assertTrue("Should complete in reasonable iterations", iterations < 1000);
    }
}
