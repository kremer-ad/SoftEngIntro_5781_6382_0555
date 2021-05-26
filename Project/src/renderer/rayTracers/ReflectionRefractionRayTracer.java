package renderer.rayTracers;

import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;


public class ReflectionRefractionRayTracer extends RayTracerBasic {
    /**
     * the maximum reflections to calculate (to avoid infinity recursion)
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * distance to trace from to avoid self reflections
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * the initial k
     */
    private static final double INITIAL_K = 1.0;


    public ReflectionRefractionRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestGeoPoint = scene.geometries.findClosestIntersection(ray);
        return closestGeoPoint == null ? scene.background : scene.ambientLight.getIntensity()
                .add(calcColor(closestGeoPoint, ray,MAX_CALC_COLOR_LEVEL,INITIAL_K))
                .add(closestGeoPoint.geometry.getEmission());
    }


    protected Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission()
                .add(super.calcColor(intersection, ray));
        return level == 1 ? color : color.add(calcGlobalEffect(intersection, ray.getDir(), level, k));
    }


    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint intersection = this.scene.geometries.findClosestIntersection(ray);
        return intersection == null ? scene.background : calcColor(intersection, ray, level - 1, kkx).scale(kx);
    }

    private Color calcGlobalEffect(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr >= MIN_CALC_COLOR_K) {
            color = calcGlobalEffect(constructReflectionRay(gp.point, v, n), level, material.kR, kkr);
        }
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K) {
            color = color.add(calcGlobalEffect(constructReflectionRay(gp.point, v, n), level, material.kT, kkt));
        }

        return color;
    }

    private Ray constructReflectionRay(Point3D pt, Vector view, Vector normal) {
        Vector dir = view.subtract(normal.scale(view.dotProduct(normal) * 2));
        return new Ray(pt, dir, normal);
    }
}
