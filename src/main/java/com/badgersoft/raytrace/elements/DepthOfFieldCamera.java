package com.badgersoft.raytrace.elements;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DepthOfFieldCamera extends Camera {
    private double aperture;
    private double focalDistance;
    private int samplesPerPixel;
    private int numThreads;
    
    public DepthOfFieldCamera(int hsize, int vsize, double fieldOfView, 
                              double aperture, double focalDistance, int samplesPerPixel) {
        super(hsize, vsize, fieldOfView);
        this.aperture = aperture;
        this.focalDistance = focalDistance;
        this.samplesPerPixel = samplesPerPixel;
        this.numThreads = Runtime.getRuntime().availableProcessors();
    }
    
    @Override
    public Canvas render(RenderWorld world, int recursionDepth) {
        Canvas image = new Canvas(getHsize(), getVsize());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger rowsCompleted = new AtomicInteger(0);
        
        System.out.println("Rendering with depth of field (" + samplesPerPixel + " samples)...");
        
        for (int y = 0; y < getVsize(); y++) {
            final int row = y;
            executor.submit(() -> {
                for (int x = 0; x < getHsize(); x++) {
                    Colour pixelColor = new Colour(0, 0, 0);
                    
                    // Multiple samples with random aperture offset
                    for (int s = 0; s < samplesPerPixel; s++) {
                        Ray ray = rayForPixelWithAperture(x, row);
                        Colour sampleColor = world.colorAt(ray, recursionDepth);
                        pixelColor = pixelColor.add(sampleColor);
                    }
                    
                    // Average the samples
                    pixelColor = pixelColor.multiply(1.0 / samplesPerPixel);
                    
                    synchronized (image) {
                        image.writePixel(pixelColor, x, row);
                    }
                }
                int completed = rowsCompleted.incrementAndGet();
                if (completed % 10 == 0) {
                    System.out.println("Progress: " + completed + "/" + getVsize() + " rows (" + 
                                     (completed * 100 / getVsize()) + "%)");
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.err.println("Rendering interrupted: " + e.getMessage());
        }
        
        return image;
    }
    
    private Ray rayForPixelWithAperture(int px, int py) {
        // Get the base ray
        double xoffset = (px + 0.5) * getPixelSize();
        double yoffset = (py + 0.5) * getPixelSize();
        
        double worldX = getHalfWidth() - xoffset;
        double worldY = getHalfHeight() - yoffset;
        
        Matrix inverseTransform = Matrix.invert(getTransform());
        Point pixel = new Point(inverseTransform.multiply(new Point(worldX, worldY, -1)));
        Point cameraOrigin = new Point(inverseTransform.multiply(new Point(0, 0, 0)));
        Vector direction = pixel.subtract(cameraOrigin).normalise();
        
        // Calculate focal point
        Point focalPoint = cameraOrigin.add(direction.multiply(focalDistance));
        
        // Random point on aperture (circular)
        double angle = Math.random() * 2 * Math.PI;
        double radius = Math.sqrt(Math.random()) * aperture;
        double apertureX = Math.cos(angle) * radius;
        double apertureY = Math.sin(angle) * radius;
        
        // Transform aperture offset to world space
        Vector right = new Vector(inverseTransform.multiply(new Vector(1, 0, 0)));
        Vector up = new Vector(inverseTransform.multiply(new Vector(0, 1, 0)));
        
        Point newOrigin = cameraOrigin
            .add(right.multiply(apertureX))
            .add(up.multiply(apertureY));
        
        Vector newDirection = focalPoint.subtract(newOrigin).normalise();
        
        return new Ray(newOrigin, newDirection);
    }
    
    private double getHalfWidth() {
        return getPixelSize() * getHsize() / 2;
    }
    
    private double getHalfHeight() {
        return getPixelSize() * getVsize() / 2;
    }
}
