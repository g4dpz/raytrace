package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Point point = new Point(new BigDecimal("0.00"),
                new BigDecimal("1.00"),
                new BigDecimal("0.00"));

        Vector vector = new Vector(
                new BigDecimal("1.00"),
                new BigDecimal("1.80"),
                new BigDecimal("0.00"));

        Projectile projectile = new Projectile(point, vector.normalise().multiply(new BigDecimal("11.25")));

        Vector gravity = new Vector(
                new BigDecimal("0.00"),
                new BigDecimal("-0.10"),
                new BigDecimal("0.00"));

        Vector wind = new Vector(
                new BigDecimal("-0.01"),
                new BigDecimal("0.0"),
                new BigDecimal("0.0"));

        World world = new World(gravity, wind);


        List<Point> points = new ArrayList<>();

        Canvas canvas = new Canvas(910, 550);
        Colour colour = new Colour(new BigDecimal(1.0), new BigDecimal(1.0), new BigDecimal(1.0));

        while (projectile.getPoint().getY().compareTo(new BigDecimal("0.0")) > 0) {
            points.add(projectile.getPoint());
            projectile = Projectile.tick(world, projectile);
        }

        Tuple minMax = getMax(points);

        for (Point point1 : points) {
            int x = point1.getX().divide(minMax.getX(),4 , RoundingMode.HALF_EVEN).multiply(new BigDecimal(900)).toBigInteger().intValue();
            int y = point1.getY().divide(minMax.getY(),4 , RoundingMode.HALF_EVEN).multiply(new BigDecimal(500)).toBigInteger().intValue();
            canvas.writePixel(colour, x, (500 - y));
        }

        FileWriter fileWriter = new FileWriter(args[0]);
        fileWriter.append(canvas.toPpm());
        fileWriter.flush();
        fileWriter.close();

    }

    private static Tuple getMax(List<Point> points) {

        BigDecimal x = new BigDecimal("0.0");
        BigDecimal y = new BigDecimal("0.0");

        for (Point point : points) {
            if (point.getX().compareTo(x) > 0) {
                x = point.getX();
            }
            if (point.getY().compareTo(y) > 0) {
                y = point.getY();
            }
        }

        Tuple tuple = new Tuple();
        tuple.setX(x);
        tuple.setY(y);

        return tuple;
    }
}
