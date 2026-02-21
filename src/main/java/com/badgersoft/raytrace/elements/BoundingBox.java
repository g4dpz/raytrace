package com.badgersoft.raytrace.elements;

public class BoundingBox {
    private Point min;
    private Point max;

    public BoundingBox() {
        this.min = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        this.max = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    public BoundingBox(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    public Point getMin() {
        return min;
    }

    public Point getMax() {
        return max;
    }

    public void addPoint(Point point) {
        min = new Point(
            Math.min(min.getX(), point.getX()),
            Math.min(min.getY(), point.getY()),
            Math.min(min.getZ(), point.getZ())
        );
        max = new Point(
            Math.max(max.getX(), point.getX()),
            Math.max(max.getY(), point.getY()),
            Math.max(max.getZ(), point.getZ())
        );
    }

    public void addBox(BoundingBox box) {
        addPoint(box.min);
        addPoint(box.max);
    }

    public boolean contains(Point point) {
        return point.getX() >= min.getX() && point.getX() <= max.getX() &&
               point.getY() >= min.getY() && point.getY() <= max.getY() &&
               point.getZ() >= min.getZ() && point.getZ() <= max.getZ();
    }

    public boolean containsBox(BoundingBox box) {
        return contains(box.min) && contains(box.max);
    }

    public boolean intersects(Ray ray) {
        double[] xt = checkAxis(ray.getOrigin().getX(), ray.getDirection().getX(), min.getX(), max.getX());
        double[] yt = checkAxis(ray.getOrigin().getY(), ray.getDirection().getY(), min.getY(), max.getY());
        double[] zt = checkAxis(ray.getOrigin().getZ(), ray.getDirection().getZ(), min.getZ(), max.getZ());

        double tmin = Math.max(Math.max(xt[0], yt[0]), zt[0]);
        double tmax = Math.min(Math.min(xt[1], yt[1]), zt[1]);

        return tmin <= tmax;
    }

    private double[] checkAxis(double origin, double direction, double min, double max) {
        double tminNumerator = (min - origin);
        double tmaxNumerator = (max - origin);

        double tmin, tmax;
        if (Math.abs(direction) >= 0.00001) {
            tmin = tminNumerator / direction;
            tmax = tmaxNumerator / direction;
        } else {
            tmin = tminNumerator * Double.POSITIVE_INFINITY;
            tmax = tmaxNumerator * Double.POSITIVE_INFINITY;
        }

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        return new double[]{tmin, tmax};
    }

    public BoundingBox transform(Matrix matrix) {
        Point[] corners = new Point[8];
        corners[0] = min;
        corners[1] = new Point(min.getX(), min.getY(), max.getZ());
        corners[2] = new Point(min.getX(), max.getY(), min.getZ());
        corners[3] = new Point(min.getX(), max.getY(), max.getZ());
        corners[4] = new Point(max.getX(), min.getY(), min.getZ());
        corners[5] = new Point(max.getX(), min.getY(), max.getZ());
        corners[6] = new Point(max.getX(), max.getY(), min.getZ());
        corners[7] = max;

        BoundingBox result = new BoundingBox();
        for (Point corner : corners) {
            result.addPoint(new Point(matrix.multiply(corner)));
        }

        return result;
    }

    public Point[] split() {
        double dx = max.getX() - min.getX();
        double dy = max.getY() - min.getY();
        double dz = max.getZ() - min.getZ();

        double greatest = Math.max(Math.max(dx, dy), dz);

        double x0 = min.getX();
        double y0 = min.getY();
        double z0 = min.getZ();
        double x1 = max.getX();
        double y1 = max.getY();
        double z1 = max.getZ();

        if (greatest == dx) {
            x0 = x1 = min.getX() + dx / 2.0;
        } else if (greatest == dy) {
            y0 = y1 = min.getY() + dy / 2.0;
        } else {
            z0 = z1 = min.getZ() + dz / 2.0;
        }

        Point midMin = new Point(x0, y0, z0);
        Point midMax = new Point(x1, y1, z1);

        return new Point[]{midMin, midMax};
    }
    
    public BoundingBox merge(BoundingBox other) {
        BoundingBox result = new BoundingBox();
        result.addPoint(this.min);
        result.addPoint(this.max);
        result.addPoint(other.min);
        result.addPoint(other.max);
        return result;
    }
}
