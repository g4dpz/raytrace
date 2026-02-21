# Demo Quick Reference Card

## One-Line Commands

```bash
# Basic Demos
mvn exec:java -Dexec.mainClass="com.badgersoft.SceneRenderer"
mvn exec:java -Dexec.mainClass="com.badgersoft.CubeDemo"

# Fast Rendering
mvn exec:java -Dexec.mainClass="com.badgersoft.FastCubeDemo"

# Benchmarks
mvn exec:java -Dexec.mainClass="com.badgersoft.SpeedComparison"
mvn exec:java -Dexec.mainClass="com.badgersoft.CameraComparison"

# Advanced Features
mvn exec:java -Dexec.mainClass="com.badgersoft.AdvancedSceneRenderer"
mvn exec:java -Dexec.mainClass="com.badgersoft.UltraSceneRenderer" -Dexec.args="fast"
mvn exec:java -Dexec.mainClass="com.badgersoft.UltraSceneRenderer" -Dexec.args="high"
mvn exec:java -Dexec.mainClass="com.badgersoft.UltraSceneRenderer" -Dexec.args="ultra"
mvn exec:java -Dexec.mainClass="com.badgersoft.DepthOfFieldDemo"
mvn exec:java -Dexec.mainClass="com.badgersoft.SoftShadowsDemo"

# Torus Demos
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusDemo"
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusGallery"
mvn exec:java -Dexec.mainClass="com.badgersoft.LargeTorusDemo"
```

## Quick Stats

| Demo | Time | Resolution | Output |
|------|------|------------|--------|
| CubeDemo | 8s | 400×300 | cube_demo.ppm |
| FastCubeDemo | 3s | 400×300 | fast_cube_demo.ppm |
| SceneRenderer | 30s | 800×400 | scene.ppm |
| TorusDemo | 47s | 800×600 | torus.png |
| SpeedComparison | 24s | 400×300 | Console only |
| DepthOfFieldDemo | 2m | 800×600 | depth_of_field.ppm |
| AdvancedSceneRenderer | 2-3m | 800×600 | advanced_scene.ppm |
| SoftShadowsDemo | 3-4m | 800×600 | soft_shadows.ppm |
| TorusGallery | 2-3m | 1200×800 | torus_gallery.png |
| CameraComparison | 3-4m | 400×300 | 4 files |
| LargeTorusDemo | 4-5m | 1200×900 | large_torus.png |
| UltraSceneRenderer | 15s-10m | varies | ultra_scene.ppm |

## Feature Matrix

| Demo | Parallel | Torus | DOF | Soft Shadows | Anti-alias | Patterns |
|------|----------|-------|-----|--------------|------------|----------|
| SceneRenderer | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| CubeDemo | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| FastCubeDemo | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| SpeedComparison | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| CameraComparison | ✅ | ❌ | ✅ | ❌ | ✅ | ✅ |
| AdvancedSceneRenderer | ✅ | ❌ | ❌ | ❌ | ❌ | ✅ |
| UltraSceneRenderer | ✅ | ❌ | ❌ | ❌ | ✅ | ✅ |
| DepthOfFieldDemo | ✅ | ❌ | ✅ | ❌ | ❌ | ❌ |
| SoftShadowsDemo | ✅ | ❌ | ❌ | ✅ | ❌ | ❌ |
| TorusDemo | ✅ | ✅ | ❌ | ❌ | ❌ | ✅ |
| TorusGallery | ✅ | ✅ | ❌ | ❌ | ❌ | ✅ |
| LargeTorusDemo | ✅ | ✅ | ❌ | ❌ | ❌ | ✅ |

## Fastest to Slowest

1. FastCubeDemo (3s)
2. CubeDemo (8s)
3. SpeedComparison (24s)
4. SceneRenderer (30s)
5. TorusDemo (47s)
6. DepthOfFieldDemo (2m)
7. AdvancedSceneRenderer (2-3m)
8. TorusGallery (2-3m)
9. SoftShadowsDemo (3-4m)
10. CameraComparison (3-4m)
11. LargeTorusDemo (4-5m)
12. UltraSceneRenderer (15s-10m)

## Best For Learning

**Start Here:**
1. CubeDemo
2. FastCubeDemo
3. SpeedComparison

**Next Level:**
4. SceneRenderer
5. TorusDemo
6. DepthOfFieldDemo

**Advanced:**
7. AdvancedSceneRenderer
8. TorusGallery
9. UltraSceneRenderer

**Benchmarking:**
10. SpeedComparison
11. CameraComparison

## Convert PPM to PNG

```bash
# macOS
sips -s format png input.ppm --out output.png

# Linux/Windows (ImageMagick)
convert input.ppm output.png
```

## Modify Resolution

In any demo file, change:
```java
Camera camera = new Camera(800, 600, Math.PI / 3);
```

To:
```java
Camera camera = new Camera(400, 300, Math.PI / 3);  // 4x faster
```

## Enable Parallel Rendering

Replace:
```java
Camera camera = new Camera(800, 600, Math.PI / 3);
```

With:
```java
int threads = Runtime.getRuntime().availableProcessors();
ParallelCamera camera = new ParallelCamera(800, 600, Math.PI / 3, threads);
```

Result: 8-9x speedup!
