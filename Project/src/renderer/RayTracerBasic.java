package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

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
        return calcColor(closestGeoPoint, ray.getDir());
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
        result = result.add(intersection.geometry.getEmission().scale(300));
        for (LightSource lightSource : scene.lights) {
            double angleCos = Math.abs(intersection.geometry.getNormal(intersection.point).dotProduct(lightSource.getL(intersection.point)));
            result = result.add(lightSource.getIntensity(intersection.point).scale(angleCos));
            //some variables for simplify the pong formula calculation
            Vector r = lightSource.getL(intersection.point);
            double pongMax = Math.max(0, -1 * visionDirection.dotProduct(r));
            result = result.add(lightSource.getIntensity(intersection.point))
                    .scale(intersection.geometry.getMaterial().kS * Math.pow(pongMax, intersection.geometry.getMaterial().nShininess));
        }
        return result;
    }
}
