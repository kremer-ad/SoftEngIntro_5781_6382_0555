package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry {

    private Point3D center = null;
    private double radius = 0;

    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D pnt) {
        return null;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" + center + radius + '}';
    }
}
