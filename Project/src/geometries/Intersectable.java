package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Intersectable {

    /** default function for old code to support tests
     * @param ray
     * @return intersection points list
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                                : geoList   .stream()
                                            .map(gp -> gp.point)
                                            .collect(Collectors.toList());
    }

    /**
     * search for all intersections between ray and geometries
     * @param ray
     * @return intersection GeoPoints list
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * search for all intersections in distance's range
     * @param ray
     * @param maxDistance
     * @return
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);


    /**
     * moving the shape in the given translation vector
     * @param x the translation vector
     * @return this
     */
    Intersectable move(Vector x);

    /**
     * rotate the shape with the given euler angle vector (degrees)
     * @param euler the rotation vector
     * @return this
     */
    Intersectable rotate(Vector euler);

    /**
     * PDS class that contain data about a point and its geometry
     * created to gain efficient
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * gain two parameters for initialization
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

}
