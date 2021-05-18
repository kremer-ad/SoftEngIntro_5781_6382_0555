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
                .add(calcColorEx(closestGeoPoint, ray))
                .add(closestGeoPoint.geometry.getEmission());//calcColor(closestGeoPoint, ray.getDir());
    }

    /**
     * calculate the color that the given point have using the pong formula
     *
     * @param intersection the point that we need to calculate the color for
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Vector visionDirection) {
        Color result = new Color(0, 0, 0);
        result = result.add(scene.ambientLight.getIntensity());
        result = result.add(intersection.geometry.getEmission());
        for (LightSource lightSource : scene.lights) {
            double lCosN = Math.abs(intersection.geometry.getNormal(intersection.point).dotProduct(lightSource.getL(intersection.point)));
            //r= l-2(l*n)*n
            Vector r = lightSource.getL(intersection.point)
                    .subtract(intersection.geometry.getNormal(intersection.point)
                            .scale(lightSource.getL(intersection.point)
                                    .dotProduct(intersection.geometry.getNormal(intersection.point)) * 2));
            double vCosR = visionDirection.normalized().dotProduct(r);
            Color pongResult = lightSource.getIntensity(intersection.point).scale(intersection.geometry.getMaterial().kD * lCosN + Math.max(0D, intersection.geometry.getMaterial().kS * vCosR * (-1)));
            result = result.add(pongResult);
        }
        return result;
    }

    private Color calcColorEx(GeoPoint geo, Ray ray) {
        Vector v = ray.getDir();
        Vector n = geo.geometry.getNormal(geo.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = geo.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = new Color(0, 0, 0);//Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geo.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Color lightIntensity = lightSource.getIntensity(geo.point);
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
        //r= l-2(l*n)*n
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        return lightIntensity.scale(ks * Math.pow(v.dotProduct(r), nShininess));
    }

    private int integerPower(double a, int b) {
        int ret = 1;
        if (b < 0) {
            b *= -1;
            a = 1 / a;
        }
        for (int i = 0; i < b; i++) {
            ret *= a;
        }
        return ret;
    }
}
