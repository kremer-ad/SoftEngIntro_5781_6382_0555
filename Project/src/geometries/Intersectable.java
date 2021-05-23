package geometries;

import primitives.Point3D;
import primitives.Ray;

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
    List<GeoPoint> findGeoIntersections(Ray ray);

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
