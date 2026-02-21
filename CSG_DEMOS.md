# CSG (Constructive Solid Geometry) Demonstrations

## Overview

These demos showcase CSG operations, specifically using the DIFFERENCE operation to create complex shapes by subtracting one primitive from another.

## Demo 1: Cube with Single Hole

**File:** `CubeWithHoleDemo.java`  
**Output:** `cube_with_hole.png` (116 KB, 800×600)  
**Render Time:** ~88 seconds (14 threads)

### Description
A red cube with a cylindrical hole drilled through it vertically (Y-axis). A small blue sphere is placed at the center to show the hole goes all the way through.

### CSG Operation
```
Cube - Cylinder = Cube with hole
```

### Specifications
- **Cube size:** 2×2×2 units
- **Hole diameter:** 0.8 units
- **Hole orientation:** Vertical (Y-axis)
- **Material:** Reddish with moderate specularity

### Features Demonstrated
- CSG DIFFERENCE operation
- Single-axis hole
- Checkered reflective floor
- Multiple lights
- Small sphere showing hole depth

### Run Command
```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.CubeWithHoleDemo"
```

### Code Structure
```java
// Create cube
Cube cube = new Cube();

// Create cylinder (extends beyond cube)
Cylinder cylinder = new Cylinder();
cylinder.setMinimum(-2);
cylinder.setMaximum(2);
cylinder.setTransform(Matrix.scale(0.4, 1, 0.4));

// CSG difference
CSG cubeWithHole = new CSG(CSG.Operation.DIFFERENCE, cube, cylinder);
```

---

## Demo 2: Cube with Three Perpendicular Holes

**File:** `CubeWithThreeHolesDemo.java`  
**Output:** `cube_three_holes.png` (322 KB, 1000×800)  
**Render Time:** ~451 seconds (~7.5 minutes, 14 threads)

### Description
A golden cube with three cylindrical holes drilled through it, one along each axis (X, Y, Z). The holes intersect at the center, creating a complex geometric shape.

### CSG Operations
```
Step 1: Cube - CylinderY = Cube with vertical hole
Step 2: Result - CylinderX = Cube with vertical + horizontal hole
Step 3: Result - CylinderZ = Cube with three perpendicular holes
```

### Specifications
- **Cube size:** 2×2×2 units
- **Hole diameter:** 0.7 units each
- **Hole orientations:** 
  - Y-axis (vertical)
  - X-axis (horizontal)
  - Z-axis (depth)
- **Material:** Golden with high specularity
- **Intersection:** All three holes meet at cube center

### Features Demonstrated
- Multiple CSG DIFFERENCE operations
- Nested CSG (CSG of CSG objects)
- Three-axis holes
- Complex geometry
- Gradient backdrop
- Three-point lighting
- Reflective floor

### Run Command
```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.CubeWithThreeHolesDemo"
```

### Code Structure
```java
// Create cube
Cube cube = new Cube();

// Create three cylinders (one per axis)
Cylinder cylinderY = new Cylinder();  // Vertical
Cylinder cylinderX = new Cylinder();  // Horizontal (rotated)
Cylinder cylinderZ = new Cylinder();  // Depth (rotated)

// Nested CSG operations
CSG step1 = new CSG(CSG.Operation.DIFFERENCE, cube, cylinderY);
CSG step2 = new CSG(CSG.Operation.DIFFERENCE, step1, cylinderX);
CSG cubeWithHoles = new CSG(CSG.Operation.DIFFERENCE, step2, cylinderZ);
```

---

## Comparison

| Aspect | Single Hole | Three Holes |
|--------|-------------|-------------|
| Holes | 1 | 3 |
| CSG Operations | 1 | 3 (nested) |
| Complexity | Simple | Complex |
| Render Time | 88s | 451s |
| Resolution | 800×600 | 1000×800 |
| File Size | 116 KB | 322 KB |
| Material | Red matte | Golden metallic |
| Lights | 2 | 3 |

---

## CSG Operations Explained

### DIFFERENCE Operation

The DIFFERENCE operation subtracts one shape from another:
```
A - B = Parts of A that are NOT inside B
```

**Use cases:**
- Drilling holes
- Creating cavities
- Carving shapes
- Boolean subtraction

### How It Works

1. **Ray Intersection:** When a ray hits the CSG object, it finds intersections with both shapes
2. **Filtering:** The CSG rules determine which intersections to keep
3. **DIFFERENCE Rule:** Keep intersections where ray enters A but not B, or exits B while inside A

### Other CSG Operations

**UNION (A ∪ B):**
- Combines two shapes
- Result contains both shapes
- Example: Two spheres → Dumbbell

**INTERSECTION (A ∩ B):**
- Only the overlapping part
- Result is where both shapes exist
- Example: Two spheres → Lens shape

---

## Technical Details

### Cylinder Setup for Holes

To drill a hole through a cube:

1. **Extend cylinder beyond cube:**
   ```java
   cylinder.setMinimum(-2);  // Below cube
   cylinder.setMaximum(2);   // Above cube
   ```

2. **Scale to desired diameter:**
   ```java
   cylinder.setTransform(Matrix.scale(0.4, 1, 0.4));
   ```

3. **Rotate for different axes:**
   ```java
   // X-axis: Rotate around Z
   Matrix.rotationZ(Math.PI / 2)
   
   // Z-axis: Rotate around X
   Matrix.rotationX(Math.PI / 2)
   
   // Y-axis: No rotation needed (default)
   ```

### Nested CSG

For multiple operations, nest CSG objects:

```java
// Wrong: Can't subtract two things at once
CSG wrong = new CSG(DIFFERENCE, cube, cyl1, cyl2);  // Not supported

// Right: Nest operations
CSG step1 = new CSG(DIFFERENCE, cube, cyl1);
CSG step2 = new CSG(DIFFERENCE, step1, cyl2);
```

---

## Performance Notes

### Render Time Factors

**Single Hole (88s):**
- 1 CSG operation
- Simpler geometry
- Fewer ray-object tests

**Three Holes (451s = 5.1x slower):**
- 3 nested CSG operations
- More complex geometry
- More ray-object tests
- Higher resolution (1000×800 vs 800×600)
- More lights (3 vs 2)

### Optimization Tips

1. **Reduce resolution** for faster previews
2. **Simplify materials** (no reflections)
3. **Fewer lights** (1-2 instead of 3)
4. **Use bounding boxes** (already implemented)

---

## Variations to Try

### Different Hole Sizes
```java
// Thin hole
cylinder.setTransform(Matrix.scale(0.2, 1, 0.2));

// Wide hole
cylinder.setTransform(Matrix.scale(0.6, 1, 0.6));
```

### Angled Holes
```java
// Diagonal hole
cylinder.setTransform(
    Matrix.rotationZ(Math.PI / 4)
        .multiply(Matrix.scale(0.4, 1, 0.4))
);
```

### Square Holes
```java
// Use a thin cube instead of cylinder
Cube holeCube = new Cube();
holeCube.setTransform(Matrix.scale(0.3, 2, 0.3));
CSG result = new CSG(CSG.Operation.DIFFERENCE, cube, holeCube);
```

### Multiple Parallel Holes
```java
// Create grid of holes
for (int x = -1; x <= 1; x++) {
    for (int z = -1; z <= 1; z++) {
        Cylinder cyl = new Cylinder();
        cyl.setTransform(
            Matrix.translation(x * 0.5, 0, z * 0.5)
                .multiply(Matrix.scale(0.15, 2, 0.15))
        );
        result = new CSG(CSG.Operation.DIFFERENCE, result, cyl);
    }
}
```

---

## Mathematical Insight

### Why Three Holes Take Longer

**Intersection Complexity:**
- Single hole: Test cube AND cylinder
- Three holes: Test cube AND cyl1 AND cyl2 AND cyl3

**CSG Tree Depth:**
```
Single:     DIFF
           /    \
        Cube    Cyl

Three:          DIFF
               /    \
            DIFF     Cyl3
           /    \
        DIFF    Cyl2
       /    \
    Cube    Cyl1
```

Deeper tree = more intersection tests per ray.

---

## Artistic Applications

### Architecture
- Windows in walls
- Pipes through structures
- Ventilation systems

### Mechanical Parts
- Bolts and screws
- Gears with holes
- Bearings

### Jewelry
- Rings with patterns
- Beads with holes
- Decorative cutouts

### Abstract Art
- Geometric sculptures
- Light and shadow play
- Negative space exploration

---

## Troubleshooting

### Hole Doesn't Go Through
**Problem:** Cylinder too short  
**Solution:** Increase min/max values
```java
cylinder.setMinimum(-3);  // Extend further
cylinder.setMaximum(3);
```

### Hole Too Small/Large
**Problem:** Wrong scale  
**Solution:** Adjust scale transform
```java
cylinder.setTransform(Matrix.scale(0.5, 1, 0.5));  // Adjust first parameter
```

### Hole in Wrong Direction
**Problem:** Wrong rotation  
**Solution:** Apply correct rotation
```java
// X-axis
Matrix.rotationZ(Math.PI / 2)

// Z-axis  
Matrix.rotationX(Math.PI / 2)
```

### Slow Rendering
**Problem:** Complex CSG + high resolution  
**Solution:** Reduce resolution or simplify scene

---

## Related Demos

- **AdvancedSceneRenderer.java** - More CSG examples
- **TorusDemo.java** - Another complex primitive
- **CubeDemo.java** - Simple cube without holes

---

## Conclusion

These demos showcase the power of CSG operations for creating complex geometry from simple primitives. The DIFFERENCE operation is particularly useful for:

✅ Creating holes and cavities  
✅ Carving shapes  
✅ Boolean subtraction  
✅ Complex mechanical parts  

The three-hole cube demonstrates that CSG operations can be nested to create increasingly complex shapes, limited only by render time and imagination!

---

## Files

**Source Code:**
- `src/main/java/com/badgersoft/CubeWithHoleDemo.java`
- `src/main/java/com/badgersoft/CubeWithThreeHolesDemo.java`

**Outputs:**
- `cube_with_hole.png` (116 KB)
- `cube_three_holes.png` (322 KB)

**Documentation:**
- `CSG_DEMOS.md` (this file)
