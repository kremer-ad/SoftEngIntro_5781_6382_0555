package geometries;

import primitives.*;

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

        /**
         * Calculate normal for point P on tube, using normal equation:
         * t=v*(p-p0)
         * O=p0+t*v
         * normal=normalize(P-O)
         *
         */
        double t = axisRay.getDir().dotProduct(pnt.subtract(axisRay.getP0()));
        if (t==0){ /* point is facing the head of the tube's ray */
            return pnt.subtract(axisRay.getP0()).normalize();
        }
        return pnt.subtract(axisRay.getP0().add(axisRay.getDir().scale(t))).normalize();
    }

    @Override
    public String toString() {
        return "Tube{" + axisRay + radius + '}';
    }
}
