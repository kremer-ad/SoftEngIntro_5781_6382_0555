package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Point3D topBaseCenter=this.axisRay.getPoint(this.height);//the center of the top base of the cylinder
        //if the point is on the bottom base make plane normal calculation:
        if(pnt.distance(this.axisRay.getP0())<=this.radius){//only on the base the distance between the center of the cylinder and the point is less then the radius, according to the Triangle inequality rule.
            return this.axisRay.getDir().normalized().scale(-1);//the normal is the ray direction*-1 because we want to ger outside of the cylinder
        }else if(pnt.distance(topBaseCenter)<=this.radius){//the same calculation as before, just for the top base
            return this.axisRay.getDir();
        }
        return super.getNormal(pnt);
    }

    @Override
    public String toString() {
        return "Cylinder{" + super.toString() + height + '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray){
        List result = super.findIntersections(ray);
        return null;
    }
}
