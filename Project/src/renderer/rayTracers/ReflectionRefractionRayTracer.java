package renderer.rayTracers;

import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

public class ReflectionRefractionRayTracer extends RayTracerBasic{
    public ReflectionRefractionRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Color ret = super.traceRay(ray);

        return ret;
    }

    private Color calcReflection(Material matt){
        return null;
    }

    private Color calcRefraction(Material matt){
        return null;
    }

    private Vector calcReflectionVector(Vector view, Vector normal){
        return view.subtract(normal.scale(view.dotProduct(normal)*2));
    }
}
