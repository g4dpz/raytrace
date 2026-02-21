package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

public class RenderWorld {
    private List<SceneObject> objects;
    private List<PointLight> lights;

    public RenderWorld() {
        this.objects = new ArrayList<>();
        this.lights = new ArrayList<>();
    }

    public List<SceneObject> getObjects() {
        return objects;
    }

    public void addObject(SceneObject object) {
        this.objects.add(object);
    }

    public List<PointLight> getLights() {
        return lights;
    }

    public void addLight(PointLight light) {
        this.lights.add(light);
    }

    public List<Intersection> intersectWorld(Ray ray) {
        List<Intersection> intersections = new ArrayList<>();
        for (SceneObject object : objects) {
            if (object instanceof Sphere) {
                intersections.addAll(ray.intersect((Sphere) object));
            } else if (object instanceof Plane) {
                intersections.addAll(intersectPlane(ray, (Plane) object));
            } else if (object instanceof Cube) {
                intersections.addAll(intersectCube(ray, (Cube) object));
            } else if (object instanceof Cylinder) {
                intersections.addAll(((Cylinder) object).intersect(ray));
            } else if (object instanceof Cone) {
                intersections.addAll(((Cone) object).intersect(ray));
            } else if (object instanceof Triangle) {
                intersections.addAll(((Triangle) object).intersect(ray));
            } else if (object instanceof SmoothTriangle) {
                intersections.addAll(((SmoothTriangle) object).intersect(ray));
            } else if (object instanceof Group) {
                intersections.addAll(((Group) object).intersect(ray));
            } else if (object instanceof CSG) {
                intersections.addAll(((CSG) object).intersect(ray));
            }
        }
        intersections.sort((a, b) -> Double.compare(a.getT(), b.getT()));
        return intersections;
    }

    private List<Intersection> intersectPlane(Ray ray, Plane plane) {
        List<Intersection> xs = new ArrayList<>();
        Ray objectRay = ray.transform(Matrix.invert(plane.getTransform()));
        
        if (Math.abs(objectRay.getDirection().getY()) < 0.00001) {
            return xs;
        }
        
        double t = -objectRay.getOrigin().getY() / objectRay.getDirection().getY();
        xs.add(new Intersection(t, plane));
        return xs;
    }

    private List<Intersection> intersectCube(Ray ray, Cube cube) {
        List<Intersection> xs = new ArrayList<>();
        Ray objectRay = ray.transform(Matrix.invert(cube.getTransform()));
        
        double[] xt = checkAxis(objectRay.getOrigin().getX(), objectRay.getDirection().getX());
        double[] yt = checkAxis(objectRay.getOrigin().getY(), objectRay.getDirection().getY());
        double[] zt = checkAxis(objectRay.getOrigin().getZ(), objectRay.getDirection().getZ());
        
        double tmin = Math.max(Math.max(xt[0], yt[0]), zt[0]);
        double tmax = Math.min(Math.min(xt[1], yt[1]), zt[1]);
        
        if (tmin > tmax) {
            return xs;
        }
        
        xs.add(new Intersection(tmin, cube));
        xs.add(new Intersection(tmax, cube));
        return xs;
    }

    private double[] checkAxis(double origin, double direction) {
        double tminNumerator = (-1 - origin);
        double tmaxNumerator = (1 - origin);
        
        double tmin, tmax;
        if (Math.abs(direction) >= 0.00001) {
            tmin = tminNumerator / direction;
            tmax = tmaxNumerator / direction;
        } else {
            tmin = tminNumerator * Double.POSITIVE_INFINITY;
            tmax = tmaxNumerator * Double.POSITIVE_INFINITY;
        }
        
        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }
        
        return new double[]{tmin, tmax};
    }

    public Intersection hit(List<Intersection> intersections) {
        for (Intersection intersection : intersections) {
            if (intersection.getT() >= 0) {
                return intersection;
            }
        }
        return null;
    }

    public Colour shadeHit(Computation comps, int remaining) {
        Colour surface = new Colour(0, 0, 0);
        
        for (PointLight light : lights) {
            boolean shadowed = isShadowed(comps.getOverPoint(), light);
            surface = surface.add(Lighting.lighting(
                comps.getObject().getMaterial(),
                comps.getObject(),
                light,
                comps.getPoint(),
                comps.getEyev(),
                comps.getNormalv(),
                shadowed
            ));
        }
        
        Colour reflected = reflectedColor(comps, remaining);
        Colour refracted = refractedColor(comps, remaining);
        
        Material material = comps.getObject().getMaterial();
        if (material.getReflective() > 0 && material.getTransparency() > 0) {
            double reflectance = schlick(comps);
            return surface.add(reflected.multiply(reflectance))
                         .add(refracted.multiply(1 - reflectance));
        }
        
        return surface.add(reflected).add(refracted);
    }

    public Colour colorAt(Ray ray, int remaining) {
        List<Intersection> intersections = intersectWorld(ray);
        Intersection hit = hit(intersections);
        
        if (hit == null) {
            return new Colour(0, 0, 0);
        }
        
        Computation comps = prepareComputations(hit, ray, intersections);
        return shadeHit(comps, remaining);
    }

    public boolean isShadowed(Point point, PointLight light) {
        Vector v = light.getPosition().subtract(point);
        double distance = v.magnitude();
        Vector direction = v.normalise();
        
        Ray ray = new Ray(point, direction);
        List<Intersection> intersections = intersectWorld(ray);
        Intersection h = hit(intersections);
        
        return h != null && h.getT() < distance;
    }
    
    public boolean isShadowed(Point point, Point lightPosition) {
        Vector v = lightPosition.subtract(point);
        double distance = v.magnitude();
        Vector direction = v.normalise();
        
        Ray ray = new Ray(point, direction);
        List<Intersection> intersections = intersectWorld(ray);
        Intersection h = hit(intersections);
        
        return h != null && h.getT() < distance;
    }

    public Colour reflectedColor(Computation comps, int remaining) {
        if (remaining <= 0 || comps.getObject().getMaterial().getReflective() == 0) {
            return new Colour(0, 0, 0);
        }
        
        Ray reflectRay = new Ray(comps.getOverPoint(), comps.getReflectv());
        Colour color = colorAt(reflectRay, remaining - 1);
        
        return color.multiply(comps.getObject().getMaterial().getReflective());
    }

    public Colour refractedColor(Computation comps, int remaining) {
        if (remaining <= 0 || comps.getObject().getMaterial().getTransparency() == 0) {
            return new Colour(0, 0, 0);
        }
        
        double nRatio = comps.getN1() / comps.getN2();
        double cosI = Vector.dot(comps.getEyev(), comps.getNormalv());
        double sin2T = nRatio * nRatio * (1 - cosI * cosI);
        
        if (sin2T > 1) {
            return new Colour(0, 0, 0);
        }
        
        double cosT = Math.sqrt(1.0 - sin2T);
        Vector direction = comps.getNormalv().multiply(nRatio * cosI - cosT)
                                             .subtract(comps.getEyev().multiply(nRatio));
        
        Ray refractRay = new Ray(comps.getUnderPoint(), direction);
        Colour color = colorAt(refractRay, remaining - 1);
        
        return color.multiply(comps.getObject().getMaterial().getTransparency());
    }

    public double schlick(Computation comps) {
        double cos = Vector.dot(comps.getEyev(), comps.getNormalv());
        
        if (comps.getN1() > comps.getN2()) {
            double n = comps.getN1() / comps.getN2();
            double sin2T = n * n * (1.0 - cos * cos);
            if (sin2T > 1.0) {
                return 1.0;
            }
            double cosT = Math.sqrt(1.0 - sin2T);
            cos = cosT;
        }
        
        double r0 = Math.pow((comps.getN1() - comps.getN2()) / (comps.getN1() + comps.getN2()), 2);
        return r0 + (1 - r0) * Math.pow(1 - cos, 5);
    }

    public Computation prepareComputations(Intersection intersection, Ray ray, List<Intersection> xs) {
        Computation comps = new Computation();
        comps.setT(intersection.getT());
        comps.setObject(intersection.getScreenObject());
        comps.setPoint(ray.position(intersection.getT()));
        comps.setEyev(ray.getDirection().negate());
        
        // Handle smooth triangles with UV coordinates
        if (intersection instanceof IntersectionWithUV && intersection.getScreenObject() instanceof SmoothTriangle) {
            IntersectionWithUV uvIntersection = (IntersectionWithUV) intersection;
            SmoothTriangle triangle = (SmoothTriangle) intersection.getScreenObject();
            comps.setNormalv(triangle.normalAt(comps.getPoint(), uvIntersection.getU(), uvIntersection.getV()));
        } else {
            comps.setNormalv(intersection.getScreenObject().normalAt(comps.getPoint()));
        }
        
        if (Vector.dot(comps.getNormalv(), comps.getEyev()) < 0) {
            comps.setInside(true);
            comps.setNormalv(comps.getNormalv().negate());
        } else {
            comps.setInside(false);
        }
        
        comps.setOverPoint(new Point(comps.getPoint().add(comps.getNormalv().multiply(0.0001))));
        comps.setUnderPoint(new Point(comps.getPoint().subtract(comps.getNormalv().multiply(0.0001))));
        comps.setReflectv(ray.getDirection().reflect(comps.getNormalv()));
        
        List<SceneObject> containers = new ArrayList<>();
        for (Intersection i : xs) {
            if (i == intersection) {
                if (containers.isEmpty()) {
                    comps.setN1(1.0);
                } else {
                    comps.setN1(containers.get(containers.size() - 1).getMaterial().getRefractiveIndex());
                }
            }
            
            if (containers.contains(i.getScreenObject())) {
                containers.remove(i.getScreenObject());
            } else {
                containers.add(i.getScreenObject());
            }
            
            if (i == intersection) {
                if (containers.isEmpty()) {
                    comps.setN2(1.0);
                } else {
                    comps.setN2(containers.get(containers.size() - 1).getMaterial().getRefractiveIndex());
                }
                break;
            }
        }
        
        return comps;
    }
}
