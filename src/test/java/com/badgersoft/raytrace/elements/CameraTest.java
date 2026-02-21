package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class CameraTest {

    @Test
    public void testConstructingCamera() {
        int hsize = 160;
        int vsize = 120;
        double fieldOfView = Math.PI / 2;
        
        Camera c = new Camera(hsize, vsize, fieldOfView);
        
        assertEquals(160, c.getHsize());
        assertEquals(120, c.getVsize());
        assertEquals(Math.PI / 2, c.getFieldOfView(), 0.0001);
    }

    @Test
    public void testPixelSizeForHorizontalCanvas() {
        Camera c = new Camera(200, 125, Math.PI / 2);
        
        assertEquals(0.01, c.getPixelSize(), 0.0001);
    }

    @Test
    public void testPixelSizeForVerticalCanvas() {
        Camera c = new Camera(125, 200, Math.PI / 2);
        
        assertEquals(0.01, c.getPixelSize(), 0.0001);
    }

    @Test
    public void testRayThroughCenterOfCanvas() {
        Camera c = new Camera(201, 101, Math.PI / 2);
        Ray r = c.rayForPixel(100, 50);
        
        assertTrue(pointEquals(new Point(0, 0, 0), r.getOrigin()));
        assertTrue(vectorEquals(new Vector(0, 0, -1), r.getDirection()));
    }

    @Test
    public void testRayThroughCornerOfCanvas() {
        Camera c = new Camera(201, 101, Math.PI / 2);
        Ray r = c.rayForPixel(0, 0);
        
        assertTrue(pointEquals(new Point(0, 0, 0), r.getOrigin()));
        assertTrue(vectorEquals(new Vector(0.66519, 0.33259, -0.66851), r.getDirection()));
    }

    @Test
    public void testRayWhenCameraIsTransformed() {
        Camera c = new Camera(201, 101, Math.PI / 2);
        c.setTransform(Matrix.rotationY(Math.PI / 4).multiply(Matrix.translation(0, -2, 5)));
        Ray r = c.rayForPixel(100, 50);
        
        assertTrue(pointEquals(new Point(0, 2, -5), r.getOrigin()));
        double val = Math.sqrt(2) / 2;
        assertTrue(vectorEquals(new Vector(val, 0, -val), r.getDirection()));
    }

    private boolean pointEquals(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) < 0.0001 &&
               Math.abs(a.getY() - b.getY()) < 0.0001 &&
               Math.abs(a.getZ() - b.getZ()) < 0.0001;
    }

    private boolean vectorEquals(Vector a, Vector b) {
        return Math.abs(a.getX() - b.getX()) < 0.01 &&
               Math.abs(a.getY() - b.getY()) < 0.01 &&
               Math.abs(a.getZ() - b.getZ()) < 0.01;
    }
}
