package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Pyramid extends Geometry {

    Polygon base;
    List<Triangle> edges;

    /**
     * create pyramid with base and the point of the pyramid top
     *
     * @param base     the pyramid base
     * @param topPoint the pyramid top
     */
    public Pyramid(Polygon base, Point3D topPoint) {
        this.base = base;
        this.edges = new ArrayList<Triangle>(base.vertices.size());
        //create triangle from each 2 near vertices of the base to the topPoint
        for (int i = 0; i < base.vertices.size() - 1; i++) {
            edges.add(new Triangle(base.vertices.get(i), base.vertices.get(i + 1), topPoint));
        }
        edges.add(new Triangle(base.vertices.get(0), base.vertices.get(base.vertices.size() - 1), topPoint));
    }

    @Override
    public Vector getNormal(Point3D pnt) {
        if (getPlane(pnt) == null) {
            return this.base.plane.getNormal();
        }
        return getPlane(pnt).getNormal();
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> ret = new LinkedList<GeoPoint>();
        appendIfNotNull(ret, base.findGeoIntersections(ray));
        for (var edge : edges) {
            appendIfNotNull(ret, edge.findGeoIntersections(ray));
        }
        if (ret.size() == 0) {
            return null;
        } else {
            System.out.print("");
        }
        return ret.stream().map(gp -> new GeoPoint(this, gp.point)).collect(Collectors.toList());
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return null;
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> ret = this.findGeoIntersections(ray);
        if (ret == null) {
            return null;
        }
        return ret.stream().map(gp -> gp.point).collect(Collectors.toList());
    }

    @Override
    public Intersectable rotate(Vector euler) {
        this.base.rotate(euler);
        for (var edge : this.edges) {
            edge.rotate(euler);
        }
        return this;
    }

    @Override
    public Intersectable move(Vector x) {
        base.move(x);
        for (var edge : edges) {
            edge.move(x);
        }
        return this;
    }

    private void appendIfNotNull(List<GeoPoint> lst, List<GeoPoint> toAdd) {
        if (toAdd != null) {
            lst.addAll(toAdd);
        }
    }

    /**
     * get point on the pyramid and return the plane that the point lay on
     *
     * @param pt the point on the pyramid
     * @return the plain on the pyramid that the point lay on
     */
    private Plane getPlane(Point3D pt) {
        if (isOn(pt, this.base.plane)) {
            return this.base.plane;
        }
        for (var edge : this.edges) {
            if (isOn(pt, edge.plane)) {
                return edge.plane;
            }
        }
        return null;
    }

    private boolean isOn(Point3D pt, Plane plane) {
        //we need to check the point from 3angles so we can be sure that the ray is not on the plane
        return isIntersecting(new Ray(pt.add(new Vector(-1, -1, 0)), new Vector(1, 1, 0)), pt, plane)
                || isIntersecting(new Ray(pt.add(new Vector(-1, 0, 0)), new Vector(1, 0, 0)), pt, plane)
                || isIntersecting(new Ray(pt.add(new Vector(-1, -1, -1)), new Vector(1, 1, 1)), pt, plane);

    }

    /**
     * check if ray intersecting plane in spesific point
     *
     * @param ray   the ray
     * @param pt    the point
     * @param plane the plane
     * @return is the p[lane intersecting the rat at the specific given point
     */
    private boolean isIntersecting(Ray ray, Point3D pt, Plane plane) {
        var intersections = plane.findIntersections(ray);
        if (intersections != null && intersections.get(0).equals(pt)) {
            return true;
        }
        return false;
    }

}
