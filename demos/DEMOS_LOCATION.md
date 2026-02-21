# Demo Files Location

## Where Are The Demo Files?

All demo Java files are located in:
```
src/main/java/com/badgersoft/
```

This is required for Maven to compile them properly.

## Demo Files List

The following demo files are in `src/main/java/com/badgersoft/`:

### Basic Demos
- `SceneRenderer.java`
- `CubeDemo.java`

### Performance Demos
- `FastCubeDemo.java`
- `SpeedComparison.java`
- `CameraComparison.java`

### Advanced Feature Demos
- `AdvancedSceneRenderer.java`
- `UltraSceneRenderer.java`
- `DepthOfFieldDemo.java`
- `SoftShadowsDemo.java`

### Torus Demos
- `TorusDemo.java`
- `TorusGallery.java`
- `LargeTorusDemo.java`

## This Directory Contains

- `README.md` - Complete demo documentation
- `QUICK_REFERENCE.md` - Quick command reference
- `DEMOS_LOCATION.md` - This file

## Running Demos

From the project root directory:

```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.DemoName"
```

Example:
```bash
mvn exec:java -Dexec.mainClass="com.badgersoft.TorusDemo"
```

## Why This Structure?

Maven requires source files to be in the standard directory structure:
- `src/main/java/` for source code
- `src/test/java/` for tests

The demos are part of the source code, so they must remain in `src/main/java/com/badgersoft/`.

This `demos/` directory serves as a documentation hub with:
- Complete usage instructions
- Quick reference guides
- Feature comparisons
- Performance metrics

## Quick Access

To quickly find demo files:

```bash
# List all demo files
ls src/main/java/com/badgersoft/*Demo*.java
ls src/main/java/com/badgersoft/*Renderer*.java
ls src/main/java/com/badgersoft/*Comparison*.java

# Or use find
find src/main/java/com/badgersoft -name "*Demo*.java"
find src/main/java/com/badgersoft -name "*Renderer*.java"
```

## Editing Demos

To edit a demo:

```bash
# Open in your editor
vim src/main/java/com/badgersoft/TorusDemo.java
code src/main/java/com/badgersoft/TorusDemo.java
```

Or navigate to the file in your IDE.

## Creating New Demos

1. Create new file in `src/main/java/com/badgersoft/`
2. Name it `MyDemo.java`
3. Use package: `package com.badgersoft;`
4. Run with: `mvn exec:java -Dexec.mainClass="com.badgersoft.MyDemo"`

See `README.md` in this directory for a template.
