package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {

    /**
     * point on plane
     */
    private Point3D q0;

    /**
     * normal vector of plane
     */
    private Vector normal;

    /**
     *
     * @return the normal value
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     *
     * @return point on the plane
     */
    public Point3D getQ0() {
        return q0;
    }

    /**
     *
     * @param q0 point on the plane
     * @param normal the normal of that plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     *
     * @param q0 first pont on the plane
     * @param q1 second point on the plane
     * @param q2 third point on the plane
     * @exception IllegalArgumentException Thrown when the points on the same line
     */
    public Plane( Point3D q0, Point3D q1, Point3D q2) {
        if(q0.subtract(q1).normalized()==q0.subtract(q2).normalized()){
            throw new IllegalArgumentException("The Points cant be on the same line");
        }
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
