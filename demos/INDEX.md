# Demo Programs Index

## 📁 Directory Structure

```
demos/
├── README.md              ← Complete documentation (START HERE)
├── QUICK_REFERENCE.md     ← Quick command reference
├── DEMOS_LOCATION.md      ← Where to find demo files
└── INDEX.md               ← This file

src/main/java/com/badgersoft/
├── SceneRenderer.java
├── CubeDemo.java
├── FastCubeDemo.java
├── SpeedComparison.java
├── CameraComparison.java
├── AdvancedSceneRenderer.java
├── UltraSceneRenderer.java
├── DepthOfFieldDemo.java
├── SoftShadowsDemo.java
├── TorusDemo.java
├── TorusGallery.java
└── LargeTorusDemo.java
```

## 📚 Documentation Files

### README.md
**Complete demo documentation**
- Detailed description of each demo
- Features demonstrated
- Render times and resolutions
- Output files
- Customization tips
- Troubleshooting

👉 **Start here for comprehensive information**

### QUICK_REFERENCE.md
**Quick command reference**
- One-line commands for all demos
- Quick stats table
- Feature matrix
- Fastest to slowest ranking
- Best learning order

👉 **Use this for quick lookups**

### DEMOS_LOCATION.md
**File location guide**
- Where demo files are located
- Why they're in src/main/java
- How to find and edit demos
- Creating new demos

👉 **Read this if you can't find the demo files**

### INDEX.md
**This file**
- Overview of documentation
- Quick navigation

## 🚀 Quick Start

### 1. First Time? Start Here:
```bash
# Read the complete documentation
cat demos/README.md

# Or open in your browser/editor
open demos/README.md
```

### 2. Want Quick Commands?
```bash
# See quick reference
cat demos/QUICK_REFERENCE.md
```

### 3. Run Your First Demo:
```bash
# Fast cube demo (3 seconds)
mvn exec:java -Dexec.mainClass="com.badgersoft.FastCubeDemo"
```

### 4. See Performance Comparison:
```bash
# Compare single vs parallel rendering
mvn exec:java -Dexec.mainClass="com.badgersoft.SpeedComparison"
```

### 5. Render a Torus:
```bash
# Beautiful torus with PNG output
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusDemo"
```

## 📊 Demo Categories

### By Difficulty

**🟢 Beginner (Start Here)**
- CubeDemo
- FastCubeDemo
- SceneRenderer

**🟡 Intermediate**
- TorusDemo
- DepthOfFieldDemo
- SoftShadowsDemo
- SpeedComparison

**🔴 Advanced**
- AdvancedSceneRenderer
- UltraSceneRenderer
- TorusGallery
- LargeTorusDemo
- CameraComparison

### By Render Time

**⚡ Fast (< 30s)**
- FastCubeDemo (3s)
- CubeDemo (8s)
- SpeedComparison (24s)

**⏱️ Medium (30s - 2m)**
- SceneRenderer (30s)
- TorusDemo (47s)
- DepthOfFieldDemo (2m)

**🐌 Slow (2m+)**
- AdvancedSceneRenderer (2-3m)
- TorusGallery (2-3m)
- SoftShadowsDemo (3-4m)
- CameraComparison (3-4m)
- LargeTorusDemo (4-5m)
- UltraSceneRenderer (15s-10m)

### By Feature

**🎨 Primitives**
- CubeDemo → Cube
- TorusDemo → Torus
- AdvancedSceneRenderer → All primitives

**⚡ Performance**
- FastCubeDemo → Parallel rendering
- SpeedComparison → Benchmarking
- CameraComparison → Camera comparison

**💎 Materials**
- TorusGallery → Metal, glass, matte
- AdvancedSceneRenderer → Patterns, reflections

**📷 Camera Effects**
- DepthOfFieldDemo → Depth of field
- CameraComparison → All camera types

**💡 Lighting**
- SoftShadowsDemo → Area lights
- AdvancedSceneRenderer → Multiple lights

**✨ Quality**
- UltraSceneRenderer → Quality modes
- CameraComparison → Anti-aliasing

## 🎯 Recommended Learning Path

### Week 1: Basics
1. Read `README.md`
2. Run `CubeDemo`
3. Run `FastCubeDemo`
4. Compare the render times

### Week 2: Performance
5. Run `SpeedComparison`
6. Understand parallel rendering
7. Modify resolution in a demo

### Week 3: Features
8. Run `TorusDemo`
9. Run `DepthOfFieldDemo`
10. Run `SoftShadowsDemo`

### Week 4: Advanced
11. Run `AdvancedSceneRenderer`
12. Run `TorusGallery`
13. Run `UltraSceneRenderer` (all modes)

### Week 5: Mastery
14. Run `CameraComparison`
15. Run `LargeTorusDemo`
16. Create your own demo!

## 🔧 Common Tasks

### View All Demo Files
```bash
ls -1 src/main/java/com/badgersoft/*Demo*.java
ls -1 src/main/java/com/badgersoft/*Renderer*.java
ls -1 src/main/java/com/badgersoft/*Comparison*.java
```

### Run All Fast Demos
```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.FastCubeDemo"
mvn exec:java -Dexec.mainClass="com.badgersoft.CubeDemo"
mvn exec:java -Dexec.mainClass="com.badgersoft.SpeedComparison"
```

### Run All Torus Demos
```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusDemo"
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusGallery"
mvn exec:java -Dexec.mainClass="com.badgersoft.LargeTorusDemo"
```

### Convert All PPM to PNG
```bash
for f in *.ppm; do
    sips -s format png "$f" --out "${f%.ppm}.png"
done
```

## 📈 Statistics

- **Total Demos:** 12
- **Total Documentation:** 4 files
- **Combined Render Time:** ~30-40 minutes (all demos)
- **Output Files:** 15+ images
- **Lines of Documentation:** 2000+

## 🆘 Help

### Can't Find Demo Files?
Read: `DEMOS_LOCATION.md`

### Need Quick Commands?
Read: `QUICK_REFERENCE.md`

### Want Detailed Info?
Read: `README.md`

### Demo Won't Run?
```bash
# Rebuild
mvn clean compile

# Check if file exists
ls src/main/java/com/badgersoft/DemoName.java
```

### Slow Rendering?
- Reduce resolution
- Use ParallelCamera
- See performance tips in `README.md`

## 🎉 Have Fun!

These demos showcase the full capabilities of the ray tracer. Experiment, modify, and create your own scenes!

---

**Quick Links:**
- [Complete Documentation](README.md)
- [Quick Reference](QUICK_REFERENCE.md)
- [File Locations](DEMOS_LOCATION.md)
- [Project Root](../)
