package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    protected RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * gets a ray and return the color of the first intersection point with the ray
     *
     * @param ray the ray to trace
     * @return the color of the ray
     */
    public abstract Color traceRay(Ray ray);
}
