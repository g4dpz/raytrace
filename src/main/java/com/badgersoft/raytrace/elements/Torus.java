package com.badgersoft.raytrace.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Torus (donut shape) primitive
 * Defined by major radius R (from center to tube center) and minor radius r (tube thickness)
 */
public class Torus extends SceneObject {
    private double majorRadius;  // R - distance from torus center to tube center
    private double minorRadius;  // r - tube radius
    
    public Torus() {
        this(1.0, 0.25);  // Default: major radius 1.0, minor radius 0.25
    }
    
    public Torus(double majorRadius, double minorRadius) {
        super();
        this.majorRadius = majorRadius;
        this.minorRadius = minorRadius;
    }
    
    public double getMajorRadius() {
        return majorRadius;
    }
    
    public double getMinorRadius() {
        return minorRadius;
    }
    
    /**
     * Ray-torus intersection using quartic equation
     * Based on the implicit equation: (x²+y²+z²+R²-r²)² = 4R²(x²+z²)
     */
    public List<Intersection> intersect(Ray ray) {
        List<Intersection> xs = new ArrayList<>();
        
        // Transform ray to object space
        Ray objectRay = ray.transform(Matrix.invert(transform));
        
        Point o = objectRay.getOrigin();
        Vector d = objectRay.getDirection();
        
        double ox = o.getX();
        double oy = o.getY();
        double oz = o.getZ();
        double dx = d.getX();
        double dy = d.getY();
        double dz = d.getZ();
        
        double R = majorRadius;
        double r = minorRadius;
        double R2 = R * R;
        double r2 = r * r;
        
        // Coefficients for the quartic equation: at⁴ + bt³ + ct² + dt + e = 0
        double sumD2 = dx*dx + dy*dy + dz*dz;
        double sumO2 = ox*ox + oy*oy + oz*oz;
        double e = sumO2 - R2 - r2;
        double f = ox*dx + oy*dy + oz*dz;
        double fourR2 = 4.0 * R2;
        
        double a = sumD2 * sumD2;
        double b = 4.0 * sumD2 * f;
        double c = 2.0 * sumD2 * e + 4.0 * f * f + fourR2 * dy * dy;
        double d_coef = 4.0 * f * e + 2.0 * fourR2 * oy * dy;
        double e_coef = e * e - fourR2 * (r2 - oy * oy);
        
        // Solve quartic equation
        double[] roots = solveQuartic(a, b, c, d_coef, e_coef);
        
        for (double t : roots) {
            if (!Double.isNaN(t) && t > 0.00001) {  // Avoid self-intersection
                xs.add(new Intersection(t, this));
            }
        }
        
        return xs;
    }
    
    /**
     * Solve quartic equation ax⁴ + bx³ + cx² + dx + e = 0
     * Returns array of real roots
     */
    private double[] solveQuartic(double a, double b, double c, double d, double e) {
        List<Double> roots = new ArrayList<>();
        
        if (Math.abs(a) < 1e-10) {
            // Degenerate to cubic
            return solveCubic(b, c, d, e);
        }
        
        // Normalize coefficients
        b /= a;
        c /= a;
        d /= a;
        e /= a;
        
        // Ferrari's method
        double b2 = b * b;
        double b3 = b2 * b;
        double b4 = b2 * b2;
        
        double alpha = -3.0 * b2 / 8.0 + c;
        double beta = b3 / 8.0 - b * c / 2.0 + d;
        double gamma = -3.0 * b4 / 256.0 + b2 * c / 16.0 - b * d / 4.0 + e;
        
        if (Math.abs(beta) < 1e-10) {
            // Biquadratic case
            double disc = alpha * alpha - 4.0 * gamma;
            if (disc >= 0) {
                double sqrtDisc = Math.sqrt(disc);
                double y1 = (-alpha + sqrtDisc) / 2.0;
                double y2 = (-alpha - sqrtDisc) / 2.0;
                
                if (y1 >= 0) {
                    double sqrt1 = Math.sqrt(y1);
                    roots.add(-b / 4.0 + sqrt1);
                    roots.add(-b / 4.0 - sqrt1);
                }
                if (y2 >= 0 && Math.abs(y1 - y2) > 1e-10) {
                    double sqrt2 = Math.sqrt(y2);
                    roots.add(-b / 4.0 + sqrt2);
                    roots.add(-b / 4.0 - sqrt2);
                }
            }
        } else {
            // General case - solve resolvent cubic
            double[] cubicRoots = solveCubic(1.0, 2.0 * alpha, alpha * alpha - 4.0 * gamma, -beta * beta);
            
            if (cubicRoots.length > 0) {
                double y = cubicRoots[0];
                double sqrtY = Math.sqrt(Math.max(0, y));
                double disc1 = 2.0 * y - alpha + beta / sqrtY;
                double disc2 = 2.0 * y - alpha - beta / sqrtY;
                
                if (disc1 >= 0) {
                    double sqrtDisc1 = Math.sqrt(disc1);
                    roots.add(-b / 4.0 + (sqrtY + sqrtDisc1) / 2.0);
                    roots.add(-b / 4.0 + (sqrtY - sqrtDisc1) / 2.0);
                }
                if (disc2 >= 0) {
                    double sqrtDisc2 = Math.sqrt(disc2);
                    roots.add(-b / 4.0 + (-sqrtY + sqrtDisc2) / 2.0);
                    roots.add(-b / 4.0 + (-sqrtY - sqrtDisc2) / 2.0);
                }
            }
        }
        
        double[] result = new double[roots.size()];
        for (int i = 0; i < roots.size(); i++) {
            result[i] = roots.get(i);
        }
        return result;
    }
    
    /**
     * Solve cubic equation ax³ + bx² + cx + d = 0
     */
    private double[] solveCubic(double a, double b, double c, double d) {
        List<Double> roots = new ArrayList<>();
        
        if (Math.abs(a) < 1e-10) {
            // Degenerate to quadratic
            return solveQuadratic(b, c, d);
        }
        
        // Normalize
        b /= a;
        c /= a;
        d /= a;
        
        double q = (3.0 * c - b * b) / 9.0;
        double r = (9.0 * b * c - 27.0 * d - 2.0 * b * b * b) / 54.0;
        double disc = q * q * q + r * r;
        
        double offset = -b / 3.0;
        
        if (disc >= 0) {
            // One real root
            double sqrtDisc = Math.sqrt(disc);
            double s = Math.cbrt(r + sqrtDisc);
            double t = Math.cbrt(r - sqrtDisc);
            roots.add(offset + s + t);
        } else {
            // Three real roots
            double theta = Math.acos(r / Math.sqrt(-q * q * q));
            double sqrtQ = Math.sqrt(-q);
            roots.add(offset + 2.0 * sqrtQ * Math.cos(theta / 3.0));
            roots.add(offset + 2.0 * sqrtQ * Math.cos((theta + 2.0 * Math.PI) / 3.0));
            roots.add(offset + 2.0 * sqrtQ * Math.cos((theta + 4.0 * Math.PI) / 3.0));
        }
        
        double[] result = new double[roots.size()];
        for (int i = 0; i < roots.size(); i++) {
            result[i] = roots.get(i);
        }
        return result;
    }
    
    /**
     * Solve quadratic equation ax² + bx + c = 0
     */
    private double[] solveQuadratic(double a, double b, double c) {
        if (Math.abs(a) < 1e-10) {
            if (Math.abs(b) < 1e-10) {
                return new double[0];
            }
            return new double[]{-c / b};
        }
        
        double disc = b * b - 4.0 * a * c;
        if (disc < 0) {
            return new double[0];
        }
        
        double sqrtDisc = Math.sqrt(disc);
        return new double[]{
            (-b - sqrtDisc) / (2.0 * a),
            (-b + sqrtDisc) / (2.0 * a)
        };
    }
    
    @Override
    public Vector normalAt(Point worldPoint) {
        // Transform point to object space
        Point objectPoint = new Point(Matrix.invert(transform).multiply(worldPoint));
        
        double x = objectPoint.getX();
        double y = objectPoint.getY();
        double z = objectPoint.getZ();
        
        double R = majorRadius;
        
        // Calculate normal using gradient of implicit function
        double sumSq = x*x + y*y + z*z;
        double k = sumSq - R*R - minorRadius*minorRadius;
        
        Vector objectNormal = new Vector(
            4.0 * x * k,
            4.0 * y * (k + 2.0 * R * R),
            4.0 * z * k
        );
        
        // Transform normal to world space
        Vector worldNormal = new Vector(
            Matrix.invert(transform).transpose().multiply(objectNormal)
        );
        
        return worldNormal.normalise();
    }
    
    @Override
    public BoundingBox getBounds() {
        double outerRadius = majorRadius + minorRadius;
        return new BoundingBox(
            new Point(-outerRadius, -minorRadius, -outerRadius),
            new Point(outerRadius, minorRadius, outerRadius)
        ).transform(transform);
    }
}
