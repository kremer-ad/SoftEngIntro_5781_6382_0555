package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
     * @return the normal value
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * @return point on the plane
     */
    public Point3D getQ0() {
        return q0;
    }

    /**
     * @param q0     point on the plane
     * @param normal the normal of that plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * @param q0 first pont on the plane
     * @param q1 second point on the plane
     * @param q2 third point on the plane
     * @throws IllegalArgumentException Thrown when the points on the same line
     */
    public Plane(Point3D q0, Point3D q1, Point3D q2) {
        if (q0.subtract(q1).normalized() == q0.subtract(q2).normalized()) {
            throw new IllegalArgumentException("The points cant be on the same line");
        }

        this.q0 = q0;

        /**
         * Calculate normal, using normal equation:
         * v1=q2-q1
         * v2=q1-q0
         * normal=normalize(v1xv2)
         */
        this.normal = q1.subtract(q2).crossProduct(q1.subtract(q0)).normalize();
    }

    @Override
    public Vector getNormal(Point3D pnt) {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" + q0 + normal + '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
