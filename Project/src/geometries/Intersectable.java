package geometries;

import primitives.BoxCollider;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Intersectable {

    /**
     * default function for old code to support tests
     *
     * @param ray
     * @return intersection points list
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * search for all intersections between ray and geometries
     *
     * @param ray
     * @return intersection GeoPoints list
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * search for all intersections in distance's range
     *
     * @param ray
     * @param maxDistance
     * @return
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);


    /**
     * moving the shape in the given translation vector
     *
     * @param x the translation vector
     * @return this
     */
    Intersectable move(Vector x);

    /**
     * rotate the shape with the given euler angle vector (degrees)
     *
     * @param euler the rotation vector
     * @return this
     */
    Intersectable rotate(Vector euler);

    /**
     * get the closest intersection of the given ray with the Intersectable
     *
     * @param ray the ray to trace
     * @return the closest intersection point
     */
    default GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return intersections.stream().min(Comparator.comparingDouble(pt1 -> ray.getP0().distanceSquared(pt1.point))).orElse(null);
    }

    /**
     * get the collider of the intersectable shape (not have to be a parameter of the class)
     * @return the collider
     */
    BoxCollider getCollider();

    /**
     * PDS class that contain data about a point and its geometry
     * created to gain efficient
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * gain two parameters for initialization
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && point.equals(geoPoint.point);
        }


    }

    default boolean isIntersectingCollider(Ray ray, double distance){
        if(this.getCollider()==null){
            return true;
        }
        return this.getCollider().isIntersecting(ray,distance);
    }

}
