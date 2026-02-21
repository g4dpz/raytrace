package com.badgersoft.raytrace.elements;

import org.junit.Test;
import static org.junit.Assert.*;

public class ObjParserTest {

    @Test
    public void testParsingVertices() {
        ObjParser parser = new ObjParser();
        parser.parseLine("v -1 1 0");
        parser.parseLine("v -1.0000 0.5000 0.0000");
        parser.parseLine("v 1 0 0");
        parser.parseLine("v 1 1 0");
        
        assertEquals(5, parser.getVertices().size()); // Including dummy at index 0
        assertEquals(-1.0, parser.getVertices().get(1).getX(), 0.0001);
        assertEquals(1.0, parser.getVertices().get(1).getY(), 0.0001);
        assertEquals(0.0, parser.getVertices().get(1).getZ(), 0.0001);
        
        assertEquals(-1.0, parser.getVertices().get(2).getX(), 0.0001);
        assertEquals(0.5, parser.getVertices().get(2).getY(), 0.0001);
        assertEquals(0.0, parser.getVertices().get(2).getZ(), 0.0001);
    }

    @Test
    public void testParsingTriangleFaces() {
        ObjParser parser = new ObjParser();
        parser.parseLine("v -1 1 0");
        parser.parseLine("v -1 0 0");
        parser.parseLine("v 1 0 0");
        parser.parseLine("v 1 1 0");
        parser.parseLine("f 1 2 3");
        parser.parseLine("f 1 3 4");
        
        Group g = parser.getDefaultGroup();
        assertEquals(2, g.getChildren().size());
        
        Triangle t1 = (Triangle) g.getChildren().get(0);
        Triangle t2 = (Triangle) g.getChildren().get(1);
        
        assertEquals(parser.getVertices().get(1), t1.getP1());
        assertEquals(parser.getVertices().get(2), t1.getP2());
        assertEquals(parser.getVertices().get(3), t1.getP3());
        
        assertEquals(parser.getVertices().get(1), t2.getP1());
        assertEquals(parser.getVertices().get(3), t2.getP2());
        assertEquals(parser.getVertices().get(4), t2.getP3());
    }

    @Test
    public void testParsingPolygonFaces() {
        ObjParser parser = new ObjParser();
        parser.parseLine("v -1 1 0");
        parser.parseLine("v -1 0 0");
        parser.parseLine("v 1 0 0");
        parser.parseLine("v 1 1 0");
        parser.parseLine("v 0 2 0");
        parser.parseLine("f 1 2 3 4 5");
        
        Group g = parser.getDefaultGroup();
        assertEquals(3, g.getChildren().size()); // Pentagon triangulated into 3 triangles
    }

    @Test
    public void testIgnoringUnrecognizedLines() {
        ObjParser parser = new ObjParser();
        parser.parseLine("# This is a comment");
        parser.parseLine("vt 0.5 0.5");
        parser.parseLine("vp 1 2 3");
        parser.parseLine("v 1 2 3");
        
        assertEquals(2, parser.getIgnoredLines()); // vt and vp are ignored
        assertEquals(2, parser.getVertices().size()); // Dummy + 1 vertex
    }

    @Test
    public void testParsingVertexNormals() {
        ObjParser parser = new ObjParser();
        parser.parseLine("vn 0 0 1");
        parser.parseLine("vn 0.707 0 -0.707");
        parser.parseLine("vn 1 2 3");
        
        assertEquals(4, parser.getNormals().size()); // Including dummy at index 0
        assertEquals(0.0, parser.getNormals().get(1).getX(), 0.0001);
        assertEquals(0.0, parser.getNormals().get(1).getY(), 0.0001);
        assertEquals(1.0, parser.getNormals().get(1).getZ(), 0.0001);
    }

    @Test
    public void testParsingSmoothTriangles() {
        ObjParser parser = new ObjParser();
        parser.parseLine("v 0 1 0");
        parser.parseLine("v -1 0 0");
        parser.parseLine("v 1 0 0");
        parser.parseLine("vn -1 0 0");
        parser.parseLine("vn 1 0 0");
        parser.parseLine("vn 0 1 0");
        parser.parseLine("f 1//3 2//1 3//2");
        
        Group g = parser.getDefaultGroup();
        assertEquals(1, g.getChildren().size());
        
        SmoothTriangle t = (SmoothTriangle) g.getChildren().get(0);
        assertEquals(parser.getVertices().get(1), t.getP1());
        assertEquals(parser.getVertices().get(2), t.getP2());
        assertEquals(parser.getVertices().get(3), t.getP3());
        assertEquals(parser.getNormals().get(3), t.getN1());
        assertEquals(parser.getNormals().get(1), t.getN2());
        assertEquals(parser.getNormals().get(2), t.getN3());
    }
}
