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
            double lCosN = Math.abs(intersection.geometry.getNormal(intersection.point).dotProduct(lightSource.getL(intersection.point)));
            //r= l-2(l*n)*n
            Vector r = lightSource.getL(intersection.point).subtract(intersection.geometry.getNormal(intersection.point))
                    .scale(lightSource.getL(intersection.point)
                            .dotProduct(intersection.geometry.getNormal(intersection.point)) * 2);
            double vCosR = visionDirection.normalized().dotProduct(r);
            Color pongResult = lightSource.getIntensity(intersection.point).scale(intersection.geometry.getMaterial().kD * lCosN + Math.max(0D, intersection.geometry.getMaterial().kS * vCosR * (-1)));
            result = result.add(pongResult);
        }
        return result;
    }
}
