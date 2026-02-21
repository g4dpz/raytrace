package com.badgersoft.raytrace.elements;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelCamera extends Camera {
    private int numThreads;
    
    public ParallelCamera(int hsize, int vsize, double fieldOfView) {
        super(hsize, vsize, fieldOfView);
        this.numThreads = Runtime.getRuntime().availableProcessors();
    }
    
    public ParallelCamera(int hsize, int vsize, double fieldOfView, int numThreads) {
        super(hsize, vsize, fieldOfView);
        this.numThreads = numThreads;
    }
    
    @Override
    public Canvas render(RenderWorld world, int recursionDepth) {
        Canvas image = new Canvas(getHsize(), getVsize());
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger rowsCompleted = new AtomicInteger(0);
        
        System.out.println("Rendering with " + numThreads + " threads...");
        
        for (int y = 0; y < getVsize(); y++) {
            final int row = y;
            executor.submit(() -> {
                for (int x = 0; x < getHsize(); x++) {
                    Ray ray = rayForPixel(x, row);
                    Colour color = world.colorAt(ray, recursionDepth);
                    synchronized (image) {
                        image.writePixel(color, x, row);
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
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.err.println("Rendering interrupted: " + e.getMessage());
        }
        
        return image;
    }
}
