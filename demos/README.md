# Ray Tracer Demo Programs

This directory contains all demo programs for the ray tracer. Each demo showcases different features and capabilities.

## Quick Start

All demos can be run from the project root using Maven:

```bash
mvn compile exec:java -Dexec.mainClass="com.badgersoft.DemoName"
```

## Demo Programs

### 1. Basic Demos

#### SceneRenderer.java
**Description:** Basic scene with sphere and plane  
**Features:** Simple Phong shading, shadows, single light  
**Resolution:** 800×400  
**Render Time:** ~30 seconds  
**Output:** `scene.ppm`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.SceneRenderer"
```

**What it demonstrates:**
- Basic sphere and plane
- Phong reflection model
- Shadow rendering
- Simple camera setup

---

#### CubeDemo.java
**Description:** Single rotating cube  
**Features:** Cube primitive, transformations  
**Resolution:** 400×300  
**Render Time:** ~8 seconds (single-threaded)  
**Output:** `cube_demo.ppm`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.CubeDemo"
```

**What it demonstrates:**
- Cube primitive
- Rotation transformations
- Basic materials
- Point light

---

### 2. Performance Demos

#### FastCubeDemo.java
**Description:** Cube with parallel rendering  
**Features:** Multi-threading, progress reporting  
**Resolution:** 400×300  
**Render Time:** ~2-3 seconds (14 threads)  
**Output:** `fast_cube_demo.ppm`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.FastCubeDemo"
```

**What it demonstrates:**
- ParallelCamera usage
- Multi-threaded rendering
- 8-9x speedup
- Progress reporting

---

#### SpeedComparison.java
**Description:** Benchmarks single vs parallel rendering  
**Features:** Performance comparison, timing  
**Resolution:** 400×300  
**Render Time:** ~24 seconds total (both renders)  
**Output:** Console statistics

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.SpeedComparison"
```

**What it demonstrates:**
- Single-threaded vs multi-threaded
- Performance metrics
- Speedup calculations
- Efficiency measurements

**Sample Output:**
```
=== Results ===
Single-threaded: 21562ms
Multi-threaded:  2564ms
Speedup:         8.41x
Efficiency:      60.1%
```

---

#### CameraComparison.java
**Description:** Compares all 4 camera types  
**Features:** All camera implementations  
**Resolution:** 400×300  
**Render Time:** ~3-4 minutes total  
**Output:** 4 PPM files + statistics

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.CameraComparison"
```

**What it demonstrates:**
- Camera (single-threaded)
- ParallelCamera (multi-threaded)
- AntiAliasedCamera (supersampling)
- DepthOfFieldCamera (aperture simulation)

**Outputs:**
- `camera_single.ppm`
- `camera_parallel.ppm`
- `camera_antialiased.ppm`
- `camera_dof.ppm`

---

### 3. Advanced Feature Demos

#### AdvancedSceneRenderer.java
**Description:** Complex scene with all features  
**Features:** Multiple objects, CSG, patterns, reflections  
**Resolution:** 800×600  
**Render Time:** ~2-3 minutes  
**Output:** `advanced_scene.ppm`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.AdvancedSceneRenderer"
```

**What it demonstrates:**
- Multiple primitive types
- CSG operations
- Pattern system
- Reflections
- Multiple lights
- Groups

---

#### UltraSceneRenderer.java
**Description:** Highest quality rendering with options  
**Features:** Quality modes, anti-aliasing, all features  
**Resolution:** 400×300 to 800×600  
**Render Time:** 15 seconds to 10 minutes  
**Output:** `ultra_scene.ppm`

```bash
# Fast mode (400×300, single-threaded)
mvn exec:java -Dexec.mainClass="com.badgersoft.UltraSceneRenderer" -Dexec.args="fast"

# High quality (800×600, multi-threaded)
mvn exec:java -Dexec.mainClass="com.badgersoft.UltraSceneRenderer" -Dexec.args="high"

# Ultra quality (800×600, 4x anti-aliasing)
mvn exec:java -Dexec.mainClass="com.badgersoft.UltraSceneRenderer" -Dexec.args="ultra"
```

**What it demonstrates:**
- Perlin noise textures
- UV mapping
- Pattern blending
- Multi-threading
- Anti-aliasing
- Quality vs speed tradeoffs

---

#### DepthOfFieldDemo.java
**Description:** Depth of field camera effects  
**Features:** Aperture simulation, focal distance, bokeh  
**Resolution:** 800×600  
**Render Time:** ~2-3 minutes  
**Output:** `depth_of_field.ppm`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.DepthOfFieldDemo"
```

**What it demonstrates:**
- DepthOfFieldCamera
- Aperture size control
- Focal distance
- Bokeh effects
- Realistic blur

---

#### SoftShadowsDemo.java
**Description:** Area lights with soft shadows  
**Features:** Area lights, soft shadow penumbra  
**Resolution:** 800×600  
**Render Time:** ~3-4 minutes  
**Output:** `soft_shadows.ppm`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.SoftShadowsDemo"
```

**What it demonstrates:**
- AreaLight implementation
- Soft shadow penumbra
- Multiple light samples
- Realistic lighting

---

### 4. Torus Demos

#### TorusDemo.java
**Description:** Single torus with metallic material  
**Features:** Torus primitive, reflections  
**Resolution:** 800×600  
**Render Time:** ~47 seconds  
**Output:** `torus.ppm`, `torus.png`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusDemo"
```

**What it demonstrates:**
- Torus primitive (quartic equation)
- Metallic materials
- Reflective floor
- Multiple lights

**Torus specs:**
- Major radius: 1.0
- Minor radius: 0.25 (25%)
- Outer diameter: 2.5

---

#### TorusGallery.java
**Description:** Three tori with different materials  
**Features:** Multiple tori, glass, metal, matte  
**Resolution:** 1200×800  
**Render Time:** ~2-3 minutes  
**Output:** `torus_gallery.ppm`, `torus_gallery.png`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusGallery"
```

**What it demonstrates:**
- Multiple tori in one scene
- Gold metallic material
- Glass/transparent material
- Matte material
- Gradient backdrop
- Complex lighting

---

#### LargeTorusDemo.java
**Description:** Large diameter torus with 20% tube  
**Features:** Large scale torus, elegant proportions  
**Resolution:** 1200×900  
**Render Time:** ~4-5 minutes  
**Output:** `large_torus.ppm`, `large_torus.png`

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.LargeTorusDemo"
```

**What it demonstrates:**
- Large torus (major radius: 3.0)
- 20% tube ratio (minor radius: 0.6)
- Sphere through center hole
- Architectural proportions

**Torus specs:**
- Major radius: 3.0
- Minor radius: 0.6 (20%)
- Outer diameter: 7.2
- Inner hole diameter: 4.8

---

## Demo Categories

### By Complexity

**Beginner:**
1. SceneRenderer
2. CubeDemo
3. FastCubeDemo

**Intermediate:**
4. TorusDemo
5. DepthOfFieldDemo
6. SoftShadowsDemo

**Advanced:**
7. AdvancedSceneRenderer
8. UltraSceneRenderer
9. TorusGallery
10. LargeTorusDemo

**Benchmarking:**
11. SpeedComparison
12. CameraComparison

---

### By Feature

**Primitives:**
- CubeDemo (cube)
- TorusDemo (torus)
- AdvancedSceneRenderer (all primitives)

**Performance:**
- FastCubeDemo (parallel rendering)
- SpeedComparison (benchmarking)
- CameraComparison (camera types)

**Materials:**
- TorusGallery (metal, glass, matte)
- AdvancedSceneRenderer (patterns, reflections)

**Camera Effects:**
- DepthOfFieldDemo (DOF)
- CameraComparison (all cameras)

**Lighting:**
- SoftShadowsDemo (area lights)
- AdvancedSceneRenderer (multiple lights)

**Quality:**
- UltraSceneRenderer (quality modes)
- CameraComparison (anti-aliasing)

---

## Render Time Guide

### Fast Renders (< 30 seconds)
- CubeDemo: ~8s
- FastCubeDemo: ~3s
- SpeedComparison: ~24s (both renders)

### Medium Renders (30s - 2 minutes)
- SceneRenderer: ~30s
- TorusDemo: ~47s
- DepthOfFieldDemo: ~2m

### Long Renders (2-5 minutes)
- AdvancedSceneRenderer: ~2-3m
- SoftShadowsDemo: ~3-4m
- TorusGallery: ~2-3m
- CameraComparison: ~3-4m

### Very Long Renders (5+ minutes)
- LargeTorusDemo: ~4-5m
- UltraSceneRenderer (ultra): ~10m

*Times based on 14-core CPU with ParallelCamera*

---

## Output Files

All demos generate PPM files. Some also convert to PNG automatically.

### PPM Files
Standard output format, can be viewed with:
- GIMP
- ImageMagick
- Online PPM viewers

### PNG Conversion
Some demos automatically convert to PNG using `sips` (macOS):
- TorusDemo → `torus.png`
- TorusGallery → `torus_gallery.png`
- LargeTorusDemo → `large_torus.png`

Manual conversion:
```bash
# macOS
sips -s format png input.ppm --out output.png

# Linux/Windows (ImageMagick)
convert input.ppm output.png
```

---

## Customization Tips

### Change Resolution
```java
// In any demo, modify:
Camera camera = new Camera(800, 600, Math.PI / 3);
// To:
Camera camera = new Camera(400, 300, Math.PI / 3);  // Faster
Camera camera = new Camera(1920, 1080, Math.PI / 3);  // Higher quality
```

### Use Parallel Rendering
```java
// Replace Camera with ParallelCamera:
int threads = Runtime.getRuntime().availableProcessors();
ParallelCamera camera = new ParallelCamera(800, 600, Math.PI / 3, threads);
```

### Adjust Quality
```java
// For anti-aliasing:
AntiAliasedCamera camera = new AntiAliasedCamera(
    800, 600, Math.PI / 3,
    2,  // samples per pixel (2, 4, or 8)
    threads
);
```

---

## Troubleshooting

### Demo Won't Compile
```bash
# Rebuild project
mvn clean compile
```

### Out of Memory
```bash
# Increase Java heap size
export MAVEN_OPTS="-Xmx4g"
mvn exec:java -Dexec.mainClass="com.badgersoft.DemoName"
```

### Slow Rendering
- Reduce resolution
- Use ParallelCamera
- Reduce anti-aliasing samples
- Simplify scene (fewer objects/lights)

### Can't View PPM Files
- Use GIMP (free, cross-platform)
- Convert to PNG (see above)
- Use online PPM viewer

---

## Creating Your Own Demo

Template:
```java
package com.badgersoft;

import com.badgersoft.raytrace.elements.*;

public class MyDemo {
    public static void main(String[] args) {
        // Create objects
        Sphere sphere = new Sphere();
        Material material = new Material();
        material.setColor(new Colour(1, 0, 0));
        sphere.setMaterial(material);
        
        // Create light
        PointLight light = new PointLight(
            new Point(-10, 10, -10),
            new Colour(1, 1, 1)
        );
        
        // Create world
        RenderWorld world = new RenderWorld();
        world.addObject(sphere);
        world.addLight(light);
        
        // Create camera
        int threads = Runtime.getRuntime().availableProcessors();
        ParallelCamera camera = new ParallelCamera(800, 600, Math.PI / 3, threads);
        camera.setTransform(Matrix.viewTransform(
            new Point(0, 1.5, -5),
            new Point(0, 0, 0),
            new Vector(0, 1, 0)
        ));
        
        // Render
        Canvas canvas = camera.render(world);
        canvas.writeToPPM("my_demo.ppm");
    }
}
```

---

## Demo Statistics

- **Total Demos:** 12
- **Basic Demos:** 2
- **Performance Demos:** 3
- **Advanced Demos:** 4
- **Torus Demos:** 3
- **Total Render Time:** ~30-40 minutes (all demos)
- **Output Files:** 15+ images

---

## Recommended Order

For learning, try demos in this order:

1. **CubeDemo** - Understand basics
2. **FastCubeDemo** - See parallel rendering
3. **SpeedComparison** - Understand performance
4. **SceneRenderer** - Basic scene
5. **TorusDemo** - Advanced primitive
6. **DepthOfFieldDemo** - Camera effects
7. **SoftShadowsDemo** - Advanced lighting
8. **AdvancedSceneRenderer** - Complex scene
9. **TorusGallery** - Multiple materials
10. **UltraSceneRenderer** - Quality modes
11. **CameraComparison** - All cameras
12. **LargeTorusDemo** - Large scale

---

## Support

For issues or questions:
- Check `IMPLEMENTATION_SUMMARY.md` for features
- Check `PARALLEL_CAMERA_GUIDE.md` for performance
- Check `TORUS_IMPLEMENTATION.md` for torus details
- Check `FINAL_STATUS.md` for complete status

---

**Happy Rendering!** 🎨
