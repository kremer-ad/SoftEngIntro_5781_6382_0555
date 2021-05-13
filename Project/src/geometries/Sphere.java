package geometries;

import org.json.simple.JSONObject;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

public class Sphere extends Geometry {
    /**
     * center point of sphere
     */
    private Point3D center;

    /**
     * radius of sphere
     */
    private double radius;

    /**
     * sphere constructor
     *
     * @param center center point of sphere
     * @param radius radius of sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D pnt) {

        return pnt.subtract(center).normalize();
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" + center + radius + '}';
    }

    /**
     * Calculate intersection points for a sphere and a ray, using equation:
     * u=O-P0
     * tm=v*u
     * d=sqrt(|u|^2-tn^2)
     * th=sqrt(r^2-d^2)
     * t1,2=tm+-th
     * Pi=P0+ti*v
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (center.equals(ray.getP0())) { // Ray head is the center of the sphere
            return List.of(new GeoPoint(this, center.add(ray.getDir().scale(radius))));
        }

        Vector u = center.subtract(ray.getP0());
        double tm = Util.alignZero(ray.getDir().dotProduct(u));
        double d = Util.alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        if (d >= radius) { // no points to return
            return null;
        }

        double th1 = Util.alignZero(Math.sqrt(radius * radius - d * d));
        double th2 = Util.alignZero(-1 * Math.sqrt(radius * radius - d * d));

        if (tm - th1 <= 0 && tm - th2 <= 0) // no points to return
        {
            return null;
        }

        List<GeoPoint> ret = new LinkedList<>();
        if (tm - th1 > 0) {
            GeoPoint gPnt1 = new GeoPoint(this, ray.getPoint(tm - th1));
            ret.add(gPnt1);
        }
        if (tm - th2 > 0) {
            GeoPoint gPnt2 = new GeoPoint(this, ray.getPoint(tm - th2));
            ret.add(gPnt2);
        }
        return ret;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = super.toJSON();
        ret.put("type", "Sphere");
        ret.put("center", center.toJSON());
        ret.put("radius", this.radius);
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        super.load(json);
        this.center = (Point3D) this.center.load((JSONObject) json.get("center"));
        this.radius = (double) json.get("radius");
        return this;
    }
    public Geometry getEmission(Color color) {
        return this.setEmission(color);
    }
}
