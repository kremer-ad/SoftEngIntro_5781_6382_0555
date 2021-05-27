package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.*;

public class RayTracerBasic extends RayTracerBase {

    /**
     * delta to ensure that point won't shade itself
     */
    private static final double DELTA = 0.1;

    /**
     * checks if point unshaded other geometries
     * @param l -
     * @param n
     * @param gp
     * @return true if it not unshaded other geometries
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1);

        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = gp.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,light.getDistance(gp.point));
        return intersections == null;
    }

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        GeoPoint closestGeoPoint = ray.findClosestGeoPoint(intersections);
        return scene.ambientLight.getIntensity()
                .add(calcColor(closestGeoPoint, ray))
                .add(closestGeoPoint.geometry.getEmission());
    }

    /**
     * calculate the color that the given point have using the pong formula
     *
     * @param intersection the point that we need to calculate the color for
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
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
            if (nl * nv > 0 && unshaded(lightSource,l,n,intersection)) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffuse(kd, l, n, lightIntensity))
                        .add(calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    private Color calcDiffuse(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        return lightIntensity.scale(ks * Math.pow(v.dotProduct(r), nShininess));
    }
}
