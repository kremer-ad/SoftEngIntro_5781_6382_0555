package geometries;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import primitives.Point3D;
import primitives.Ray;
import primitives.Serializable;
import primitives.Vector;

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
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> ret = new LinkedList<GeoPoint>();
        for (Intersectable shape : shapes) {
            var geoIntersection = shape.findGeoIntersections(ray);
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
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        JSONArray data = new JSONArray();
        for (Intersectable sh : this.shapes) {
            Serializable shape = ((Serializable) sh);
            JSONObject shapeJson = shape.toJSON();
            data.add(shapeJson);
        }
        ret.put("type", "geometries");
        ret.put("data", data);
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.shapes = new LinkedList<Intersectable>();
        JSONArray data = (JSONArray) json.get("data");
        for (Object obj : data) {
            this.add(handleSerialization((JSONObject) obj));
        }
        return this;
    }

    private Intersectable handleSerialization(JSONObject json) {
        String type = (String) json.get("type");
        Intersectable ret = null;
        //for each geometry that we have -> create an instance and then load the data from the json object
        switch (type) {
            case "cylinder":
                Cylinder cylinder = new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 1, 1);
                ret = (Intersectable) cylinder.load(json);
                break;
            case "tube":
                Tube tube = new Tube(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 1);
                ret = (Intersectable) tube.load(json);
                break;
            case "plane":
                Plane plane = new Plane(Point3D.ZERO, new Vector(1, 0, 0));
                ret = (Intersectable) plane.load(json);
                break;
            case "triangle":
                Triangle triangle = new Triangle(Point3D.ZERO, new Point3D(0, 0, 1), new Point3D(0, 1, 0));
                ret = (Intersectable) triangle.load(json);
                break;
            case "polygon":
                Polygon polygon = new Polygon(Point3D.ZERO, new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(0, 1, 1));
                ret=(Intersectable) polygon.load(json);
                break;
            case "sphere":
                Sphere sphere = new Sphere(Point3D.ZERO, 1);
                ret = (Intersectable) sphere.load(json);
                break;
        }
        return ret;
    }
}
