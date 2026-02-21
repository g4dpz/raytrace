package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorldTest {

    @Test
    public void testDefaultConstructor() {
        World world = new World();
        assertNull(world.getWind());
        assertNull(world.getGravity());
    }

    @Test
    public void testConstructorWithParameters() {
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        
        World world = new World(wind, gravity);
        
        assertEquals(wind, world.getWind());
        assertEquals(gravity, world.getGravity());
    }

    @Test
    public void testSetWind() {
        World world = new World();
        Vector wind = new Vector(-0.01, 0.0, 0.0);
        
        world.setWind(wind);
        
        assertEquals(wind, world.getWind());
    }

    @Test
    public void testSetGravity() {
        World world = new World();
        Vector gravity = new Vector(0.0, -0.1, 0.0);
        
        world.setGravity(gravity);
        
        assertEquals(gravity, world.getGravity());
    }

    @Test
    public void testWorldWithZeroVectors() {
        Vector wind = new Vector(0.0, 0.0, 0.0);
        Vector gravity = new Vector(0.0, 0.0, 0.0);
        
        World world = new World(wind, gravity);
        
        assertEquals(wind, world.getWind());
        assertEquals(gravity, world.getGravity());
    }
}
