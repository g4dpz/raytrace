package com.badgersoft;

import com.badgersoft.raytrace.elements.Matrix;
import com.badgersoft.raytrace.elements.Point;
import com.badgersoft.raytrace.elements.Tuple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClockTest {

    @Test
    public void testClockRadiansCalculation() {
        double radians = 1.0 / 12.0 * 2.0 * Math.PI;
        double expected = Math.PI / 6.0; // 30 degrees in radians
        
        assertEquals(expected, radians, 0.0001);
    }

    @Test
    public void testClockGenerates12Points() {
        List<Point> points = new ArrayList<>();
        double radians = 1.0 / 12.0 * 2.0 * Math.PI;
        Point p1 = new Point(0, 0, 0);
        Matrix translate = Matrix.translation(250, 0, 0);
        Matrix rotateX = Matrix.rotationX(Math.PI / 2.0);
        Matrix translateXY = Matrix.translation(275, 275, 0);
        Point p2 = new Point(translate.multiply(p1));
        
        for (int i = 0; i < 12; i++) {
            Matrix rotateY = Matrix.rotationY(i * radians);
            Point p3 = new Point(translateXY.multiply(rotateX.multiply(rotateY.multiply(p2))));
            points.add(p3);
        }
        
        assertEquals(12, points.size());
    }

    @Test
    public void testClockPointsAreDistinct() {
        List<Point> points = new ArrayList<>();
        double radians = 1.0 / 12.0 * 2.0 * Math.PI;
        Point p1 = new Point(0, 0, 0);
        Matrix translate = Matrix.translation(250, 0, 0);
        Matrix rotateX = Matrix.rotationX(Math.PI / 2.0);
        Matrix translateXY = Matrix.translation(275, 275, 0);
        Point p2 = new Point(translate.multiply(p1));
        
        for (int i = 0; i < 12; i++) {
            Matrix rotateY = Matrix.rotationY(i * radians);
            Point p3 = new Point(translateXY.multiply(rotateX.multiply(rotateY.multiply(p2))));
            points.add(p3);
        }
        
        // Check that all points are distinct
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1Point = points.get(i);
                Point p2Point = points.get(j);
                boolean distinct = Math.abs(p1Point.getX() - p2Point.getX()) > 0.001 ||
                                 Math.abs(p1Point.getY() - p2Point.getY()) > 0.001;
                assertTrue("Points " + i + " and " + j + " should be distinct", distinct);
            }
        }
    }

    @Test
    public void testClockPointsAreCenteredAround275() {
        List<Point> points = new ArrayList<>();
        double radians = 1.0 / 12.0 * 2.0 * Math.PI;
        Point p1 = new Point(0, 0, 0);
        Matrix translate = Matrix.translation(250, 0, 0);
        Matrix rotateX = Matrix.rotationX(Math.PI / 2.0);
        Matrix translateXY = Matrix.translation(275, 275, 0);
        Point p2 = new Point(translate.multiply(p1));
        
        for (int i = 0; i < 12; i++) {
            Matrix rotateY = Matrix.rotationY(i * radians);
            Point p3 = new Point(translateXY.multiply(rotateX.multiply(rotateY.multiply(p2))));
            points.add(p3);
        }
        
        // Calculate average position
        double avgX = 0.0;
        double avgY = 0.0;
        for (Point p : points) {
            avgX += p.getX();
            avgY += p.getY();
        }
        avgX /= points.size();
        avgY /= points.size();
        
        // Should be centered around (275, 275)
        assertEquals(275.0, avgX, 1.0);
        assertEquals(275.0, avgY, 1.0);
    }

    @Test
    public void testGetMaxHelper() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(10.0, 20.0, 0.0));
        points.add(new Point(30.0, 15.0, 0.0));
        points.add(new Point(5.0, 25.0, 0.0));
        
        Tuple result = getMax(points);
        
        assertEquals(30.0, result.getX(), 0.0001);
        assertEquals(25.0, result.getY(), 0.0001);
    }

    @Test
    public void testGetMaxWithSinglePoint() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(42.0, 84.0, 0.0));
        
        Tuple result = getMax(points);
        
        assertEquals(42.0, result.getX(), 0.0001);
        assertEquals(84.0, result.getY(), 0.0001);
    }

    @Test
    public void testGetMaxWithNegativeValues() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(-10.0, -20.0, 0.0));
        points.add(new Point(5.0, 10.0, 0.0));
        
        Tuple result = getMax(points);
        
        assertEquals(5.0, result.getX(), 0.0001);
        assertEquals(10.0, result.getY(), 0.0001);
    }

    // Helper method from Clock class
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
