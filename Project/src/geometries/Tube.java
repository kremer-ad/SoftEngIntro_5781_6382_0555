//(-b + Math.sqrt(discriminant)) / (2 * a);
package geometries;

//import org.jetbrains.annotations.NotNull;

import org.json.simple.JSONObject;
import primitives.*;
import primitives.Vector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Infinity Tube
 */
public class Tube extends Geometry {
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
        return pnt.subtract(axisRay.getPoint(t)).normalize();
    }

    @Override
    public String toString() {
        return "Tube{" + axisRay + radius + '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> ret=this.findGeoIntersections(ray);
        if(ret==null){
            return null;
        }
        return ret.stream().map(pt->pt.point).collect(Collectors.toList());
    }

    public  void move(Vector x){
        //move the base of the ray and all the tube will move
        this.axisRay = new Ray(this.axisRay.getP0().add(x),this.axisRay.getDir());
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        //for using less functions we storing all teh parameters in other variables
        double rayOriginX = ray.getP0().getX();
        double rayOriginY = ray.getP0().getY();
        double rayOriginZ = ray.getP0().getZ();
        double tubeOriginX = this.axisRay.getP0().getX();
        double tubeOriginY = this.axisRay.getP0().getY();
        double tubeOriginZ = this.axisRay.getP0().getZ();
        double rayDirectionX = ray.getDir().getHead().getX();
        double rayDirectionY = ray.getDir().getHead().getY();
        double rayDirectionZ = ray.getDir().getHead().getZ();
        double tubeDirectionX = this.axisRay.getDir().getHead().getX();
        double tubeDirectionY = this.axisRay.getDir().getHead().getY();
        double tubeDirectionZ = this.axisRay.getDir().getHead().getZ();

        //the discriminate for the quadratic equation
        double discriminant = ((2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX + 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY - 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY + 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY + 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY + 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ - 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ - 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ + 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ) * (2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX + 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY - 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY + 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY + 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY + 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY - 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ - 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ + 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ - 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ + 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ + 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ - 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ)) - 4 * (rayDirectionY * rayDirectionY * tubeDirectionX * tubeDirectionX + rayDirectionZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionX * rayDirectionY * tubeDirectionX * tubeDirectionY + rayDirectionX * rayDirectionX * tubeDirectionY * tubeDirectionY + rayDirectionZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * rayDirectionZ * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + rayDirectionX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + rayDirectionY * rayDirectionY * tubeDirectionZ * tubeDirectionZ) * (rayOriginY * rayOriginY * tubeDirectionX * tubeDirectionX + rayOriginZ * rayOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginY * tubeOriginY * tubeDirectionX * tubeDirectionX + tubeOriginY * tubeOriginY * tubeDirectionX * tubeDirectionX - 2 * rayOriginZ * tubeOriginZ * tubeDirectionX * tubeDirectionX + tubeOriginZ * tubeOriginZ * tubeDirectionX * tubeDirectionX - 2 * rayOriginX * rayOriginY * tubeDirectionX * tubeDirectionY + 2 * rayOriginY * tubeOriginX * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * tubeOriginY * tubeDirectionX * tubeDirectionY - 2 * tubeOriginX * tubeOriginY * tubeDirectionX * tubeDirectionY + rayOriginX * rayOriginX * tubeDirectionY * tubeDirectionY + rayOriginZ * rayOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginX * tubeOriginX * tubeDirectionY * tubeDirectionY + tubeOriginX * tubeOriginX * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * tubeOriginZ * tubeDirectionY * tubeDirectionY + tubeOriginZ * tubeOriginZ * tubeDirectionY * tubeDirectionY - 2 * rayOriginX * rayOriginZ * tubeDirectionX * tubeDirectionZ + 2 * rayOriginZ * tubeOriginX * tubeDirectionX * tubeDirectionZ + 2 * rayOriginX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * tubeOriginX * tubeOriginZ * tubeDirectionX * tubeDirectionZ - 2 * rayOriginY * rayOriginZ * tubeDirectionY * tubeDirectionZ + 2 * rayOriginZ * tubeOriginY * tubeDirectionY * tubeDirectionZ + 2 * rayOriginY * tubeOriginZ * tubeDirectionY * tubeDirectionZ - 2 * tubeOriginY * tubeOriginZ * tubeDirectionY * tubeDirectionZ + rayOriginX * rayOriginX * tubeDirectionZ * tubeDirectionZ + rayOriginY * rayOriginY * tubeDirectionZ * tubeDirectionZ - 2 * rayOriginX * tubeOriginX * tubeDirectionZ * tubeDirectionZ + tubeOriginX * tubeOriginX * tubeDirectionZ * tubeDirectionZ - 2 * rayOriginY * tubeOriginY * tubeDirectionZ * tubeDirectionZ + tubeOriginY * tubeOriginY * tubeDirectionZ * tubeDirectionZ - tubeDirectionX * tubeDirectionX * this.radius * this.radius - tubeDirectionY * tubeDirectionY * this.radius * this.radius - tubeDirectionZ * tubeDirectionZ * this.radius * this.radius);
        //-b for the quadratic equation
        final double Bminus = -2 * rayOriginY * rayDirectionY * tubeDirectionX * tubeDirectionX - 2 * rayOriginZ * rayDirectionZ * tubeDirectionX * tubeDirectionX + 2 * rayDirectionY * tubeOriginY * tubeDirectionX * tubeDirectionX + 2 * rayDirectionZ * tubeOriginZ * tubeDirectionX * tubeDirectionX + 2 * rayOriginY * rayDirectionX * tubeDirectionX * tubeDirectionY + 2 * rayOriginX * rayDirectionY * tubeDirectionX * tubeDirectionY - 2 * rayDirectionY * tubeOriginX * tubeDirectionX * tubeDirectionY - 2 * rayDirectionX * tubeOriginY * tubeDirectionX * tubeDirectionY - 2 * rayOriginX * rayDirectionX * tubeDirectionY * tubeDirectionY - 2 * rayOriginZ * rayDirectionZ * tubeDirectionY * tubeDirectionY + 2 * rayDirectionX * tubeOriginX * tubeDirectionY * tubeDirectionY + 2 * rayDirectionZ * tubeOriginZ * tubeDirectionY * tubeDirectionY + 2 * rayOriginZ * rayDirectionX * tubeDirectionX * tubeDirectionZ + 2 * rayOriginX * rayDirectionZ * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionZ * tubeOriginX * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionX * tubeOriginZ * tubeDirectionX * tubeDirectionZ + 2 * rayOriginZ * rayDirectionY * tubeDirectionY * tubeDirectionZ + 2 * rayOriginY * rayDirectionZ * tubeDirectionY * tubeDirectionZ - 2 * rayDirectionZ * tubeOriginY * tubeDirectionY * tubeDirectionZ - 2 * rayDirectionY * tubeOriginZ * tubeDirectionY * tubeDirectionZ - 2 * rayOriginX * rayDirectionX * tubeDirectionZ * tubeDirectionZ - 2 * rayOriginY * rayDirectionY * tubeDirectionZ * tubeDirectionZ + 2 * rayDirectionX * tubeOriginX * tubeDirectionZ * tubeDirectionZ + 2 * rayDirectionY * tubeOriginY * tubeDirectionZ * tubeDirectionZ;
        //the denominator for the quadratic equation
        final double aTwo = 2 * (rayDirectionY * rayDirectionY * tubeDirectionX * tubeDirectionX + rayDirectionZ * rayDirectionZ * tubeDirectionX * tubeDirectionX - 2 * rayDirectionX * rayDirectionY * tubeDirectionX * tubeDirectionY + rayDirectionX * rayDirectionX * tubeDirectionY * tubeDirectionY + rayDirectionZ * rayDirectionZ * tubeDirectionY * tubeDirectionY - 2 * rayDirectionX * rayDirectionZ * tubeDirectionX * tubeDirectionZ - 2 * rayDirectionY * rayDirectionZ * tubeDirectionY * tubeDirectionZ + rayDirectionX * rayDirectionX * tubeDirectionZ * tubeDirectionZ + rayDirectionY * rayDirectionY * tubeDirectionZ * tubeDirectionZ);
        //no intersection or tangent
        if (discriminant <= 0) {
            return null;
        }

        //there must be 2 intersection points
        List<GeoPoint> ret = new LinkedList<GeoPoint>();//we using linked list so we could remove points if using cylinder
        //add only the positive results to the list
        boolean listEmpty = true;
        double t = (Bminus - Math.sqrt(discriminant)) / aTwo;
        if (t > 0) {
            listEmpty = false;
            ret.add(new GeoPoint(this,ray.getPoint(t)));
        }
        t = (Bminus + Math.sqrt(discriminant)) / aTwo;
        //(-b - Math.sqrt(discriminant)) / (2 * a);
        if (t > 0) {
            listEmpty = false;
            ret.add(new GeoPoint(this,ray.getPoint(t)));
        }
        return listEmpty ? null : ret;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = super.toJSON();
        ret.put("type", "Tube");
        ret.put("radius", this.radius);
        ret.put("axisRay", this.axisRay.toJSON());
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        super.load(json);
        this.radius = (int) json.get("radius");
        this.axisRay.load((JSONObject) json.get("axisRay"));
        return this;
    }
}
