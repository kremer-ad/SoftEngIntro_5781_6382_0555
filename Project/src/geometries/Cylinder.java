package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{

    private double height =0;

    /**
     *
     * @param axisRay The axis of the Tube
     * @param radius tha radius of the Tube
     * @param height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     *
     * @return the height value
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point3D pnt) {
        return super.getNormal(pnt);
    }

    @Override
    public String toString() {
        return "Cylinder{" + super.toString() + height + '}';
    }
}
