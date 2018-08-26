package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Clock {

    public static void main(String... args) throws IOException {
        List<Point> points = new ArrayList<>();
        Canvas canvas = new Canvas(550, 550);
        Colour colour = new Colour(1.0, 1.0, 1.0);

        double radians = 1.0 / 12.0 * 2.0 * Math.PI;
        Point p1 = new Point(0,0,0);
        Matrix translate = Matrix.translation(250, 0, 0);
        Matrix rotateX = Matrix.rotationX(Math.PI / 2.0);
        Matrix translateXY = Matrix.translation(275,275,0);
        Point p2 = new Point(translate.multiply(p1));
        for (int i = 0; i < 12; i++) {
            Matrix rotateY = Matrix.rotationY(i * radians);
            Point p3 = new Point(translateXY.multiply(rotateX.multiply(rotateY.multiply(p2))));
            points.add(p3);
            System.out.println(p3.getX() + " " + p3.getY());
        }

        Tuple minMax = getMax(points);

        for (Point point1 : points) {
            int x = (int) ((point1.getX() / minMax.getX()) * minMax.getX());
            int y = (int) ((point1.getY() / minMax.getY()) * minMax.getY());
            canvas.writePixel(colour, x, y);
        }

        FileWriter fileWriter = new FileWriter(args[0]);
        fileWriter.append(canvas.toPpm());
        fileWriter.flush();
        fileWriter.close();

    }

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

        Tuple tuple = new Tuple();
        tuple.setX(x);
        tuple.setY(y);

        return tuple;
    }
}
