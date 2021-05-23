package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

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
            if (nl * nv > 0) {
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
