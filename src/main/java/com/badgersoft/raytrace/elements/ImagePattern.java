package com.badgersoft.raytrace.elements;

public class ImagePattern extends UVPattern {
    private Colour[][] pixels;
    private int width;
    private int height;
    
    public ImagePattern(Colour[][] pixels) {
        this.pixels = pixels;
        this.height = pixels.length;
        this.width = pixels[0].length;
    }
    
    public ImagePattern(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Colour[height][width];
        
        // Initialize with a default pattern
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = new Colour(0, 0, 0);
            }
        }
    }
    
    public void setPixel(int x, int y, Colour color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x] = color;
        }
    }
    
    public Colour getPixel(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return pixels[y][x];
        }
        return new Colour(0, 0, 0);
    }
    
    @Override
    public Colour uvPatternAt(double u, double v) {
        // Clamp u and v to [0, 1]
        u = Math.max(0, Math.min(1, u));
        v = Math.max(0, Math.min(1, v));
        
        // Convert to pixel coordinates
        int x = (int) (u * (width - 1));
        int y = (int) ((1 - v) * (height - 1)); // Flip v
        
        return getPixel(x, y);
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    // Factory method to create a test pattern
    public static ImagePattern createTestPattern(int width, int height) {
        ImagePattern pattern = new ImagePattern(width, height);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = (double) x / width;
                double g = (double) y / height;
                double b = 0.5;
                pattern.setPixel(x, y, new Colour(r, g, b));
            }
        }
        
        return pattern;
    }
}
