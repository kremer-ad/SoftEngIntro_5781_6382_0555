package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    /**
     * A collection of geometries shapes
     */
    List<Intersectable> shapes;

    public Geometries() {
        shapes = new LinkedList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        shapes = new LinkedList<Intersectable>();
        for (Intersectable geo : geometries) {
            shapes.add(geo);
        }
    }

    /**
     * add more geometries to shapes collection
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            shapes.add(geo);
        }
    }

    /**
     * Implement interface function
     * @param ray
     * @return all intersections points
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> ret = new LinkedList<Point3D>();
        for (Intersectable shape : shapes) {
            List intersection = shape.findIntersections(ray);
            if (intersection==null){continue;} // if there is no intersections points - continue
            for (Point3D pnt : shape.findIntersections(ray)) {
                ret.add(pnt);
            }
        }
        return ret.isEmpty() ? null : ret;
    }
}
