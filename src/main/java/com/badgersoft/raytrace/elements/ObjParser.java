package com.badgersoft.raytrace.elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjParser {
    private List<Point> vertices;
    private List<Vector> normals;
    private Group defaultGroup;
    private Group currentGroup;
    private int ignoredLines;

    public ObjParser() {
        this.vertices = new ArrayList<>();
        this.normals = new ArrayList<>();
        this.defaultGroup = new Group();
        this.currentGroup = defaultGroup;
        this.ignoredLines = 0;
        
        // OBJ files are 1-indexed, so add a dummy vertex at index 0
        vertices.add(new Point(0, 0, 0));
        normals.add(new Vector(0, 0, 0));
    }

    public static ObjParser parseFile(String filename) throws IOException {
        ObjParser parser = new ObjParser();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parser.parseLine(line);
            }
        }
        
        return parser;
    }

    public void parseLine(String line) {
        line = line.trim();
        
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }
        
        String[] parts = line.split("\\s+");
        
        if (parts.length == 0) {
            return;
        }
        
        switch (parts[0]) {
            case "v":
                if (parts.length >= 4) {
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    vertices.add(new Point(x, y, z));
                } else {
                    ignoredLines++;
                }
                break;
                
            case "vn":
                if (parts.length >= 4) {
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    normals.add(new Vector(x, y, z));
                } else {
                    ignoredLines++;
                }
                break;
                
            case "f":
                if (parts.length >= 4) {
                    parseFace(parts);
                } else {
                    ignoredLines++;
                }
                break;
                
            case "g":
                if (parts.length >= 2) {
                    currentGroup = new Group();
                    defaultGroup.addChild(currentGroup);
                } else {
                    ignoredLines++;
                }
                break;
                
            default:
                ignoredLines++;
                break;
        }
    }

    private void parseFace(String[] parts) {
        List<Integer> vertexIndices = new ArrayList<>();
        List<Integer> normalIndices = new ArrayList<>();
        
        for (int i = 1; i < parts.length; i++) {
            String[] indices = parts[i].split("/");
            vertexIndices.add(Integer.parseInt(indices[0]));
            
            if (indices.length >= 3 && !indices[2].isEmpty()) {
                normalIndices.add(Integer.parseInt(indices[2]));
            }
        }
        
        // Fan triangulation for polygons with more than 3 vertices
        for (int i = 1; i < vertexIndices.size() - 1; i++) {
            Point p1 = vertices.get(vertexIndices.get(0));
            Point p2 = vertices.get(vertexIndices.get(i));
            Point p3 = vertices.get(vertexIndices.get(i + 1));
            
            if (normalIndices.size() >= vertexIndices.size()) {
                // Smooth triangle with normals
                Vector n1 = normals.get(normalIndices.get(0));
                Vector n2 = normals.get(normalIndices.get(i));
                Vector n3 = normals.get(normalIndices.get(i + 1));
                currentGroup.addChild(new SmoothTriangle(p1, p2, p3, n1, n2, n3));
            } else {
                // Regular triangle
                currentGroup.addChild(new Triangle(p1, p2, p3));
            }
        }
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public List<Vector> getNormals() {
        return normals;
    }

    public Group getDefaultGroup() {
        return defaultGroup;
    }

    public int getIgnoredLines() {
        return ignoredLines;
    }

    public Group toGroup() {
        return defaultGroup;
    }
}
