package com.badgersoft.raytrace.elements;

public class Camera {
    private int hsize;
    private int vsize;
    private double fieldOfView;
    private Matrix transform;
    private double pixelSize;
    private double halfWidth;
    private double halfHeight;

    public Camera(int hsize, int vsize, double fieldOfView) {
        this.hsize = hsize;
        this.vsize = vsize;
        this.fieldOfView = fieldOfView;
        this.transform = new Matrix(new double[][]{{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}});
        
        double halfView = Math.tan(fieldOfView / 2);
        double aspect = (double) hsize / vsize;
        
        if (aspect >= 1) {
            this.halfWidth = halfView;
            this.halfHeight = halfView / aspect;
        } else {
            this.halfWidth = halfView * aspect;
            this.halfHeight = halfView;
        }
        
        this.pixelSize = (halfWidth * 2) / hsize;
    }

    public int getHsize() {
        return hsize;
    }

    public int getVsize() {
        return vsize;
    }

    public double getFieldOfView() {
        return fieldOfView;
    }

    public Matrix getTransform() {
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    public double getPixelSize() {
        return pixelSize;
    }

    public Ray rayForPixel(int px, int py) {
        double xoffset = (px + 0.5) * pixelSize;
        double yoffset = (py + 0.5) * pixelSize;
        
        double worldX = halfWidth - xoffset;
        double worldY = halfHeight - yoffset;
        
        Matrix inverseTransform = Matrix.invert(transform);
        Point pixel = new Point(inverseTransform.multiply(new Point(worldX, worldY, -1)));
        Point origin = new Point(inverseTransform.multiply(new Point(0, 0, 0)));
        Vector direction = pixel.subtract(origin).normalise();
        
        return new Ray(origin, direction);
    }

    public Canvas render(RenderWorld world, int recursionDepth) {
        Canvas image = new Canvas(hsize, vsize);
        
        for (int y = 0; y < vsize; y++) {
            if (y % 10 == 0) {
                System.out.println("Rendering row " + y + " of " + vsize);
            }
            for (int x = 0; x < hsize; x++) {
                Ray ray = rayForPixel(x, y);
                Colour color = world.colorAt(ray, recursionDepth);
                image.writePixel(color, x, y);
            }
        }
        
        return image;
    }

    public Canvas render(RenderWorld world) {
        return render(world, 5);
    }
}
