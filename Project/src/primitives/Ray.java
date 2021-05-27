package primitives;

import geometries.Intersectable.GeoPoint;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class Ray implements Serializable {
    /**
     * beginning point of ray
     */
    private Point3D p0;

    /**
     * normalized direction vector of ray,
     */
    private Vector dir;


    /**
     * delta to ensure that point won't shade itself
     */
    protected static final double DELTA = 0.1;


    /**
     * ray constructor
     *
     * @param p0  beginning point of ray
     * @param dir gets direction vector of ray and normalize it
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    public Ray(Point3D head, Vector direction, Vector normal) {
        Vector delta = normal.scale(normal.dotProduct(direction) >= 0 ? DELTA : -DELTA);
        this.p0 = head.add(delta);
        this.dir = direction.normalized();
    }

    // Getters
    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    /**
     * Gets point on ray
     *
     * @param t scale
     * @return point
     */
    public Point3D getPoint(double t) {
        if (dir.getHead().mult(t).equals(Point3D.ZERO)) {
            return p0;
        }
        return p0.add(dir.scale(t));
    }

    /**
     * get the closest point to the head of the ray from a given points list
     *
     * @param points the list to compare
     * @return the closest point to the origin of the ray
     */
    public Point3D findClosestPoint(List<Point3D> points) {

        if (points.size() == 0) {
            return null;
        }

        List<GeoPoint> geoList =
                points.stream()
                        .map(pnt -> new GeoPoint(null, pnt))
                        .collect(Collectors.toList());

        return findClosestGeoPoint(geoList).point;
    }


    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {

        if (geoPoints.size() == 0) {
            return null;
        }

        GeoPoint ret = geoPoints.get(0);
        for (var gPnt : geoPoints) {
            //we using distanceSquared instead of distance because it faster then distance and for that check it will give the same results according to algebra
            if (this.p0.distanceSquared(ret.point) > this.p0.distanceSquared(gPnt.point)) {
                ret = gPnt;
            }
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray other = (Ray) o;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString() {
        return "Ray{" + p0 + dir + '}';
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("type", "Ray");
        ret.put("p0", this.p0.toJSON());
        ret.put("dir", this.dir.toJSON());
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.p0 = (Point3D) p0.load((JSONObject) json.get("p0"));
        dir.load((JSONObject) json.get("dir"));
        return this;
    }

    public Ray rotate(Vector euler) {
        this.dir.rotate(euler);
        return this;
    }


}
