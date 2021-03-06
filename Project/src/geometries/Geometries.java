package geometries;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable, Serializable {
    /**
     * A collection of geometries shapes
     */
    List<Intersectable> shapes;

    public Geometries() {
        shapes = new LinkedList<Intersectable>();
    }

    /**
     * the collider of the geometries
     */
    private BoxCollider collider;

    /**
     * constructor - gets collection of geometries
     *
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        shapes = new LinkedList<Intersectable>();
        for (Intersectable geo : geometries) {
            shapes.add(geo);
        }
    }

    /**
     * add more geometries to shapes collection
     *
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            shapes.add(geo);
        }
    }

    /**
     * Implement interface function
     *
     * @param ray the interacting ray
     * @return all intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (!this.isIntersectingCollider(ray, maxDistance)) {
            return null;
        }
        List<GeoPoint> ret = new LinkedList<GeoPoint>();
        for (Intersectable shape : shapes) {
            var geoIntersection = shape.findGeoIntersections(ray, maxDistance);
            if (geoIntersection == null) {
                continue;
            } // if there is no intersections points - continue

            for (var gPnt : geoIntersection) {
                ret.add(gPnt);
            }
        }
        return ret.isEmpty() ? null : ret;
    }

    @Override
    public Intersectable move(Vector x) {
        if (this.collider != null) {
            this.collider.move(x);
        }
        //move all the shapes in the collections
        for (var shape : shapes) {
            shape.move(x);
        }
        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        JSONArray data = new JSONArray();
        for (Intersectable sh : this.shapes) {
            Serializable shape = ((Serializable) sh);
            JSONObject shapeJson = shape.toJSON();
            data.add(shapeJson);
        }
        ret.put("type", "Geometries");
        ret.put("data", data);
        return ret;
    }

    public Intersectable rotate(Vector euler) {
        for (var geometry : this.shapes) {
            geometry.rotate(euler);
        }
        return this;
    }

    @Override
    public BoxCollider getCollider() {
        return this.collider;
    }


    @Override
    public Serializable load(JSONObject json) {
        this.shapes = new LinkedList<Intersectable>();
        JSONArray data = (JSONArray) json.get("data");
        for (Object obj : data) {
            this.add(loadGeometry((JSONObject) obj));
        }
        return this;
    }

    /**
     * load all the given geometry from json object
     *
     * @param json json object that present geometry
     * @return the geometry
     */
    private Intersectable loadGeometry(JSONObject json) {
        String type = (String) json.get("type");
        Intersectable ret = null;
        //for each geometry that we have -> create an instance and then load the data from the json object
        switch (type) {
            case "Cylinder":
                Cylinder cylinder = new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 1, 1);
                ret = (Intersectable) cylinder.load(json);
                break;
            case "Tube":
                Tube tube = new Tube(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 1);
                ret = (Intersectable) tube.load(json);
                break;
            case "Plane":
                Plane plane = new Plane(Point3D.ZERO, new Vector(1, 0, 0));
                ret = (Intersectable) plane.load(json);
                break;
            case "Triangle":
                Triangle triangle = new Triangle(Point3D.ZERO, new Point3D(0, 0, 1), new Point3D(0, 1, 0));
                ret = (Intersectable) triangle.load(json);
                break;
            case "Polygon":
                Polygon polygon = new Polygon(Point3D.ZERO, new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(0, 1, 1));
                ret = (Intersectable) polygon.load(json);
                break;
            case "Sphere":
                Sphere sphere = new Sphere(Point3D.ZERO, 1);
                ret = (Intersectable) sphere.load(json);
                break;
        }
        return ret;
    }

    /**
     * setter
     *
     * @param collider the collider to set
     * @return this
     */
    public Geometries setCollider(BoxCollider collider) {
        this.collider = collider;
        return this;
    }


}
