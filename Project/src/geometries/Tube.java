package geometries;

import primitives.*;

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
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" + axisRay + radius + '}';
    }
}
