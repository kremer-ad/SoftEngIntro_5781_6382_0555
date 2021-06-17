package geometries;

import org.json.simple.JSONObject;
import primitives.Point3D;
import primitives.Ray;
import primitives.Serializable;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Cylinder extends Tube {

    /**
     * Cylinder heights
     */
    private double height;

    /**
     * @param axisRay The axis of the Cylinder
     * @param radius  tha radius of the Cylinder
     * @param height  tha height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * @return the height value
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the center of the bottum base of the cylinder
     */
    public Point3D getBasePoint() {
        return this.axisRay.getP0();
    }

    @Override
    public Vector getNormal(Point3D pnt) {
        Point3D topBaseCenter = this.axisRay.getPoint(this.height);//the center of the top base of the cylinder
        //if the point is on the bottom base make plane normal calculation:
        if (pnt.distance(this.axisRay.getP0()) <= this.radius) {//only on the base the distance between the center of the cylinder and the point is less then the radius, according to the Triangle inequality rule.
            return this.axisRay.getDir().normalized().scale(-1);//the normal is the ray direction*-1 because we want to ger outside of the cylinder
        } else if (pnt.distance(topBaseCenter) <= this.radius) {//the same calculation as before, just for the top base
            return this.axisRay.getDir();
        }
        return super.getNormal(pnt);
    }

    @Override
    public String toString() {
        return "Cylinder{" + super.toString() + height + '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> ret = this.findGeoIntersections(ray);
        if (ret == null) {
            return null;
        }
        return ret.stream().map(pt -> pt.point).collect(Collectors.toList());
    }

    private Point3D planesIntersection(Plane cylinderPlane, Ray ray) {
        List<Point3D> planesIntersections = cylinderPlane.findIntersections(ray);
        if (planesIntersections != null && planesIntersections.get(0).distance(cylinderPlane.getQ0()) < this.radius) {
            return planesIntersections.get(0);
        }
        return null;
    }

    /**
     * check if point is on the cylinder
     * assumption: the point is on the father tube
     *
     * @param pt the point to check
     * @return is the point on the cylinder
     */
    private boolean onCylinder(Point3D pt) {
        //the middle of the cylinder
        Point3D cylinderMid = this.axisRay.getPoint(this.height * 0.5);
        //according to Pythagoras formula a^2+b^2=c^2-> the distance of the point from the middle of the cylinder is sqrt(r^2+(h/2)^2)
        double maxDistance = Math.sqrt(radius * radius + (height / 2) * (height / 2));
        return pt.distance(cylinderMid) < maxDistance;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double distance) {
        if(!isIntersectingCollider(ray,distance)){
            return null;
        }
        List<GeoPoint> ret = super.findGeoIntersections(ray,distance);
        if (ret == null) {
            ret = new LinkedList<GeoPoint>();
        } else {
            System.out.print("");
        }
        Plane bottomPlane = new Plane(this.axisRay.getP0(), this.axisRay.getDir());
        Plane topPlane = new Plane(this.axisRay.getPoint(this.height), this.axisRay.getDir());
        ret.removeIf(point -> !onCylinder(point.point));//remove all the points that out of the cylinder boundary
        Point3D intersectingPoint = planesIntersection(topPlane, ray);
        addIfNotNullOrFar(ret, intersectingPoint, ray.getP0(), distance);
        intersectingPoint = planesIntersection(bottomPlane, ray);
        addIfNotNullOrFar(ret, intersectingPoint, ray.getP0(), distance);
        if (ret.size() == 0) {
            return null;
        }
        return ret;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = super.toJSON();
        ret.put("type", "Cylinder");
        ret.put("height", this.height);
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        super.load(json);
        this.height = (int) json.get("height");
        return this;
    }


    /**
     * add geoPoint to list only if it not null
     *
     * @param lst      the list
     * @param pt       the point to add (converted to geoPoint
     * @param dest     the destination to compare the distance from
     * @param distance teh max distance
     */
    private void addIfNotNullOrFar(List<GeoPoint> lst, Point3D pt, Point3D dest, double distance) {
        if (pt != null && pt.distance(dest) <= distance) {
            lst.add(new GeoPoint(this, pt));
        }
    }

}
