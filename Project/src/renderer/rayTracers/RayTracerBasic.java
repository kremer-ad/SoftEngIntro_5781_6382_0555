package renderer.rayTracers;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
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


    /**
     * checks if point unshaded other geometries
     *
     * @param l  -
     * @param n
     * @param gp
     * @return true if it not unshaded other geometries
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        /*if (intersections==null) return true;
        double d=light.getDistance(gp.point);
        for(GeoPoint pnt: intersections){
            if (alignZero(pnt.point.distance(gp.point)-d)<=0) return false;
        }
        return true;*/
        return intersections == null || intersections.stream().allMatch(p -> p.geometry.getMaterial().kT != 0);
    }

    /**
     * get the transparency shadow on the given point from given light
     *
     * @param light the light
     * @param l     l parameter of the light
     * @param n     normal
     * @param gp    the point
     * @return the transparancy percent of the shadow
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        double lightDistance = light.getDistance(gp.point);
        if (intersections == null) return 1D;
        double ktr = 1D;
        for (GeoPoint pnt : intersections) {

            if (alignZero(pnt.point.distance(gp.point) - lightDistance) <= 0) {
                ktr *= pnt.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0D;
                }
            }
        }
        return ktr;
    }

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestGeoPoint = scene.geometries.findClosestIntersection(ray);
        return closestGeoPoint == null ? scene.background : scene.ambientLight.getIntensity()
                .add(calcColor(closestGeoPoint, ray));
                //.add(closestGeoPoint.geometry.getEmission());
    }

    /**
     * calculate the color that the given point have using the pong formula
     *
     * @param intersection the point that we need to calculate the color for
     * @return the color of the point
     */
    protected Color calcColor(GeoPoint intersection, Ray ray) {
        return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K);
    }

    /**
     * calculate the color with transparency and reflection
     *
     * @param intersection the intersection point to calculate
     * @param ray          the ray to the point
     * @param level        the level of reflection/refraction (to avoid infinity reflections)
     * @param k            reflection/refraction factor
     * @return the color at the specific point
     */
    protected Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray, k));
        return level == 1 ? color : color.add(calcGlobalEffect(intersection, ray.getDir(), level, k));
    }

    /**
     * calculation for the pong formula
     */
    private Color calcDiffuse(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * calculation for the pong formula
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        return lightIntensity.scale(ks * Math.pow(v.dotProduct(r), nShininess));
    }


    /**
     * calc the effect of the world from given ray
     *
     * @param ray   the ray
     * @param level the level of refraction/reflection
     * @param kx    reflection/refraction factor
     * @param kkx   another factor for the formula
     * @return the color from the world
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint intersection = this.scene.geometries.findClosestIntersection(ray);
        return intersection == null ? scene.background : calcColor(intersection, ray, level - 1, kkx).scale(kx);
    }

    /**
     * calc the effect of the world from given point
     *
     * @param gp    the point
     * @param v     the view direction
     * @param level the reflection/refraction level
     * @param k     the reflection/refraction factor
     * @return the color of the point
     */
    private Color calcGlobalEffect(GeoPoint gp, Vector v, int level, double k) {
        Color color = new Color(Color.BLACK);
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K) {
            color = calcGlobalEffect(constructReflectionRay(gp.point, v, n), level, material.kR, kkr);
        }
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K) {
            color = color.add(calcGlobalEffect(constructRefractionRay(gp.point, v, n), level, material.kT, kkt));
        }

        return color;
    }

    /**
     * get the reflection
     *
     * @param pt     the hit point
     * @param view   the hit vector
     * @param normal the normal of the hit
     * @return the reflection ray
     */
    private Ray constructReflectionRay(Point3D pt, Vector view, Vector normal) {
        Vector dir = view.subtract(normal.scale(view.dotProduct(normal) * 2));
        return new Ray(pt, dir, normal);
    }

    /**
     * calculate the refraction ray (currently not using Snell form,ula)
     *
     * @param pt     the hit point
     * @param view   the view direction
     * @param normal the normal of the hit
     * @return the refraction ray
     */
    private Ray constructRefractionRay(Point3D pt, Vector view, Vector normal) {
        return new Ray(pt, view, normal);
    }

    /**
     * calculate the color of the given object without calculating the world effects
     *
     * @param intersection the point to calculate
     * @param ray          the ray that hitting the point
     * @return the color
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = new Color(0, 0, 0);//Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffuse(kd, l, n, lightIntensity))
                            .add(calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

}
