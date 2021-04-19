package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry {
    /**
     * center point of sphere
     */
    private Point3D center = null;

    /**
     * radius of sphere
     */
    private double radius = 0D;

    /**
     * sphere constructor
     * @param center center point of sphere
     * @param radius radius of sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D pnt) {

        return pnt.subtract(center).normalize();
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

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        //TODO::Implement that method
        return null;
    }
}
