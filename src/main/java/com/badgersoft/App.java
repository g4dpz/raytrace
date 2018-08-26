package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Point point = new Point(0.00,
                1.00,
                0.00);

        Vector vector = new Vector(
                1.00,
                1.80,
                0.00);

        Projectile projectile = new Projectile(point, vector.normalise().multiply(11.25));

        Vector gravity = new Vector(
                0.00,
                -0.10,
                0.00);

        Vector wind = new Vector(
                -0.01,
                0.0,
                0.0);

        World world = new World(gravity, wind);


        List<Point> points = new ArrayList<>();

        Canvas canvas = new Canvas(910, 550);
        Colour colour = new Colour(1.0, 1.0, 1.0);

        while (projectile.getPoint().getY() > 0) {
            points.add(projectile.getPoint());
            projectile = Projectile.tick(world, projectile);
        }

        Tuple minMax = getMax(points);

        for (Point point1 : points) {
            int x = (int) ((point1.getX() / minMax.getX()) * minMax.getX());
            int y = (int) ((point1.getY() / minMax.getY()) * minMax.getY());
            canvas.writePixel(colour, x, (540 - y));
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

        return new Tuple(x,y,0,0);
    }
}
