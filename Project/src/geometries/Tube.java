package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Infinity Tube
 */
public class Tube implements Geometry {
    /**
     * The axis of the Tube
     */
    protected Ray axisRay;

    /**
     * tha radius of the Tube
     */
    protected double radius;

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * @param axisRay The axis of the Tube
     * @param radius  tha radius of the Tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D pnt) {

        /*
         * Calculate normal for point P on tube, using normal equation:
         * t=v*(p-p0)
         * O=p0+t*v
         * normal=normalize(P-O)
         *
         */
        double t = axisRay.getDir().dotProduct(pnt.subtract(axisRay.getP0()));
        if (t == 0) { /* point is facing the head of the tube's ray */
            return pnt.subtract(axisRay.getP0()).normalize();
        }
        return pnt.subtract(axisRay.getP0().add(axisRay.getDir().scale(t))).normalize();
    }

    @Override
    public String toString() {
        return "Tube{" + axisRay + radius + '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
      return null;
    }
/*
TODO:: finish the intersection code


    private Point3D oneSideIntersection(Ray ray){
        //that method was found in stackoverflow and work.
        //we it have a deep math that we cant explain so we've just copied it
        //https://stackoverflow.com/questions/4078401/trying-to-optimize-line-vs-cylinder-intersection
        Vector AB = this.axisRay.getDir();
        Vector AO = ray.getP0().subtract(this.axisRay.getP0());
        Vector AOxAB = AO.crossProduct(AB);
        Vector VxAB = ray.getDir().crossProduct(AB);

        double ab2 = AB.dotProduct(AB);
        double a = VxAB.dotProduct(VxAB);
        double b = 2 * VxAB.dotProduct(AOxAB);
        double c = AOxAB.dotProduct(AOxAB) - (radius * radius * ab2);

        double d = b * b * 4 * a * c;
        double t = (-b - Math.sqrt(d) / (2 * a));
        if (d < 0||t<0) { //if d<0 there is no intersection, and if t<0 -> the ray is always inside the cylinder
            return null;
        }
        return ray.getPoint(t);
    }

    private Ray getLocalAxisRay(Ray globalRay){
        Vector[] oldBase = new Vector[3];
        Vector[] newBase = {
                new Vector(0,1,0),
                new Vector(1,0,0),
                new Vector(0,0,1)
        };



        return null;
    }
*/
}
