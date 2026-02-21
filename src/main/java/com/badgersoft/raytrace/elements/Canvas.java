package com.badgersoft.raytrace.elements;

public class Canvas {

    private long width;
    private long height;

    private Colour[][] pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new Colour[height][width];
        for (int line = 0 ; line < height ; line++) {
            for (int pos = 0 ; pos < width ; pos++) {
                pixels[line][pos] = new Colour(0.0, 0.0, 0.0);
            }
        }
    }

    public void writePixel(Colour c, int x, int y) {
        pixels[y][x] = c;
    }

    public Colour pixelAt(int x, int y) {
        return pixels[y][x];
    }

    public String toPpm() {
        StringBuffer content = new StringBuffer("P3\n")
                .append(width).append(" ").append(height).append("\n")
                .append("255\n");

        for (int line = 0 ; line < height ; line++) {
            StringBuffer row = new StringBuffer();
            Colour colour = pixels[line][0];
            String redValue = scaleColour(colour.getRed());
            String greenValue = scaleColour(colour.getGreen());
            String blueValue = scaleColour(colour.getBlue());
            row.append(redValue).append(" ");
            row.append(greenValue).append(" ");
            row.append(blueValue);
            for (int pos = 1 ; pos < width ; pos++) {
                colour = pixels[line][pos];

                redValue = scaleColour(colour.getRed());
                int length = row.length() + redValue.length() + 1;
                if (length > 70) {
                    row.append("\n");
                    content.append(row.toString());
                    row = new StringBuffer();
                    row.append(redValue);
                }
                else {
                    row.append(" ").append(redValue);
                }

                greenValue = scaleColour(colour.getGreen());
                length = row.length() + greenValue.length() + 1;
                if (length > 70) {
                    row.append("\n");
                    content.append(row.toString());
                    row = new StringBuffer();
                    row.append(greenValue);
                }
                else {
                    row.append(" ").append(greenValue);
                }

                blueValue = scaleColour(colour.getBlue());
                length = row.length() + blueValue.length() + 1;
                if (length > 70) {
                    row.append("\n");
                    content.append(row.toString());
                    row = new StringBuffer();
                    row.append(blueValue);
                }
                else {
                    row.append(" ").append(blueValue);
                }
            }
            content.append(row.toString()).append("\n");
        }
        return content.toString();
    }

    private String scaleColour(double colour) {
        Double scaledColour = colour / 1.0;
        scaledColour *= 255;
        return Integer.toString(scaledColour.intValue());
    }

    public long getWidth() {
        return width;
    }

    public long getHeight() {
        return height;
    }

    public void writeToPPM(String filename) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(filename);
            writer.write(toPpm());
            writer.close();
        } catch (java.io.IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
