package com.badgersoft.raytrace.elements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CanvasTest {

    @Test
    public void writeAndGetPpm() {
        Canvas c = new Canvas(3, 3);
        final Colour c1 = new Colour(1, 0.3, 0.75);
        c.writePixel(c1, 1, 1);
        String ppm = c.toPpm();

        assertEquals(ppm.lastIndexOf("\n"), ppm.length() - 1);

        assertEquals("P3\n" +
                "3 3\n" +
                "255\n" +
                "0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 255 76 191 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0\n", ppm);
    }

    @Test
    public void splitPpmLinesAt70Chars() {
        Canvas c = new Canvas(25, 1);

        final Colour c1 = new Colour(1, 0.3, 0.75);
        c.writePixel(c1, 11, 0);

        String ppm = c.toPpm();assertEquals(ppm.lastIndexOf("\n"), ppm.length() - 1);;


        assertEquals("P3\n" +
                "25 1\n" +
                "255\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255\n" +
                "76 191 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0\n", ppm);
    }


}

