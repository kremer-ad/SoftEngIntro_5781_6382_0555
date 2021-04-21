package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sphere implements Geometry {
    /**
     * center point of sphere
     */
    private Point3D center;

    /**
     * radius of sphere
     */
    private double radius;

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
<<<<<<< HEAD
        List<Point3D> ret = new LinkedList<>();

        if(center.equals(ray.getP0())){ // Ray head is the center of the sphere
            return List.of(center.add(ray.getDir().scale(radius)));
        }

        Vector u = center.subtract(ray.getP0());
        double tm = Util.alignZero(ray.getDir().dotProduct(u));
        double d=Util.alignZero(Math.sqrt(u.lengthSquared()-tm*tm));

        if (d>=radius) {
            return null;
        }

        double th1 = Util.alignZero(Math.sqrt(radius*radius-d*d));
        double th2 = Util.alignZero(-1*Math.sqrt(radius*radius-d*d));
        if (tm-th1>0){
            Point3D pnt1 = ray.getPoint(tm-th1);
            ret.add(pnt1);
        }
        if (tm-th2>0){
            Point3D pnt2 = ray.getPoint(tm-th2);
            ret.add(pnt2);
        }
        return ret.isEmpty()? null: ret;
=======

        //TODO::Implement that method
        return null;
>>>>>>> 93992e13110d7d640804cfe41ebad53fe96406ec
    }
}
