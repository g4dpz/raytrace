package com.badgersoft.raytrace.elements;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AntiAliasedCamera extends Camera {
    private int samplesPerPixel;
    private int numThreads;
    
    public AntiAliasedCamera(int hsize, int vsize, double fieldOfView, int samplesPerPixel) {
        super(hsize, vsize, fieldOfView);
        this.samplesPerPixel = samplesPerPixel;
        this.numThreads = Runtime.getRuntime().availableProcessors();
    }
    
    public AntiAliasedCamera(int hsize, int vsize, double fieldOfView, int samplesPerPixel, int numThreads) {
        super(hsize, vsize, fieldOfView);
        this.samplesPerPixel = samplesPerPixel;
        this.numThreads = numThreads;
    }
    
    @Override
    public Canvas render(RenderWorld world, int recursionDepth) {
        Canvas image = new Canvas(getHsize(), getVsize());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger rowsCompleted = new AtomicInteger(0);
        
        System.out.println("Rendering with " + numThreads + " threads and " + 
                         samplesPerPixel + "x anti-aliasing...");
        
        for (int y = 0; y < getVsize(); y++) {
            final int row = y;
            executor.submit(() -> {
                for (int x = 0; x < getHsize(); x++) {
                    Colour pixelColor = new Colour(0, 0, 0);
                    
                    // Stratified sampling
                    int sqrtSamples = (int) Math.sqrt(samplesPerPixel);
                    for (int sy = 0; sy < sqrtSamples; sy++) {
                        for (int sx = 0; sx < sqrtSamples; sx++) {
                            double jitterX = (sx + Math.random()) / sqrtSamples;
                            double jitterY = (sy + Math.random()) / sqrtSamples;
                            
                            Ray ray = rayForPixelWithJitter(x, row, jitterX, jitterY);
                            Colour sampleColor = world.colorAt(ray, recursionDepth);
                            pixelColor = pixelColor.add(sampleColor);
                        }
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
    
    private Ray rayForPixelWithJitter(int px, int py, double jitterX, double jitterY) {
        double xoffset = (px + jitterX) * getPixelSize();
        double yoffset = (py + jitterY) * getPixelSize();
        
        double worldX = getHalfWidth() - xoffset;
        double worldY = getHalfHeight() - yoffset;
        
        Matrix inverseTransform = Matrix.invert(getTransform());
        Point pixel = new Point(inverseTransform.multiply(new Point(worldX, worldY, -1)));
        Point origin = new Point(inverseTransform.multiply(new Point(0, 0, 0)));
        Vector direction = pixel.subtract(origin).normalise();
        
        return new Ray(origin, direction);
    }
    
    private double getHalfWidth() {
        // Access via reflection or make it protected in Camera
        return getPixelSize() * getHsize() / 2;
    }
    
    private double getHalfHeight() {
        return getPixelSize() * getVsize() / 2;
    }
}
