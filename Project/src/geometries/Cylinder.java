package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{

    /**
     * Cylinder heights
     */
    private double height;

    /**
     *
     * @param axisRay The axis of the Cylinder
     * @param radius tha radius of the Cylinder
     * @param height tha height of the Cylinder
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
