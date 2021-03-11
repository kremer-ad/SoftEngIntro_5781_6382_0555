package geometries;

import primitives.*;

public class Tube implements Geometry {


    protected Ray axisRay = null;
    protected double radius = 0;

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D pnt) {
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" + axisRay + radius + '}';
    }
}
