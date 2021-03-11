package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {

    public Point3D getQ0() {
        return q0;
    }



    private Point3D q0 = null;
    private Vector normal = null;

    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    public Plane(Point3D q0,Point3D q1,Point3D q2) {
        this.q0 = q0;
        this.normal = null;
    }

    @Override
    public Vector getNormal(Point3D pnt) {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" + q0 + normal + '}';
    }
}
