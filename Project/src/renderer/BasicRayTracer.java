package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class BasicRayTracer extends RayTracerBase{

    public BasicRayTracer(Scene scene){
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = scene.geometries.findIntersections(ray);
        if(intersections==null){
            return scene.background;
        }
        Point3D intersectionPoint = ray.findClosestPoint(intersections);
        return calcColor(intersectionPoint);
    }

    /**
     * calculatye the color that the given point have
     * @param point the point that we need to calculate the color for
     * @return the color of the point
     */
    private Color calcColor(Point3D point){
        return this.scene.ambientLight.intensity;
    }
}
