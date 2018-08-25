package com.badgersoft.raytrace.elements;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                pixels[line][pos] = new Colour(new BigDecimal("0.0"), new BigDecimal("0.0"), new BigDecimal("0.0"));
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

    private String scaleColour(BigDecimal colour) {
        return colour.divide(new BigDecimal("1.0")).multiply(new BigDecimal("255")).setScale(0, RoundingMode.FLOOR).toString();
    }


}
