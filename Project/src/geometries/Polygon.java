package geometries;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import primitives.*;

import java.util.List;
import java.util.stream.Collectors;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
            return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point3D point) {
        return plane.getNormal();
    }

    /**
     * search for all intersection points within maxDistance from ray's head
     *
     * @param ray
     * @param maxDistance
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if(!isIntersectingCollider(ray,maxDistance)){
            return null;
        }
        //there is two option, 1: the ray have intersection point with the plane, 2 the ray is on the plane
        //check option 1
        List<GeoPoint> ret = plane.findGeoIntersections(ray, maxDistance);
        if (ret == null) {//we must check if ret is null before we running on the list
            return null;
        }
        ret.removeIf(gPt -> !this.isOn(gPt.point, ray));//remove all points outside of the polygon
        return ret.size() == 0 ? null : List.of(new GeoPoint(this, ret.get(0).point));
    }

    /**
     * check if a point is on the polygon
     *
     * @param pt the point to check
     * @return is the point on the polygon
     */
    private boolean isOn(Point3D pt, Ray ray) {
        Vector[] normals = new Vector[this.vertices.size()];//all the normals for the formula
        Vector[] polygonVectors = new Vector[this.vertices.size()];//all the vectors that we can build from the first vertex
        Point3D headPoint = ray.getP0();
        for (int i = 0; i < polygonVectors.length; i++) {//calculate the vectors
            polygonVectors[i] = this.vertices.get(i).subtract(headPoint);
        }
        for (int i = 0; i < normals.length - 1; i++) {//calculate the normals (we not normalizing because it not necessary)
            normals[i] = polygonVectors[i].crossProduct(polygonVectors[i + 1]);
        }
        //calculate the last normal
        normals[normals.length - 1] = polygonVectors[polygonVectors.length - 1].crossProduct(polygonVectors[0]);
        Vector pointVector = pt.subtract(headPoint);//calculating the vector from the first vertex to the given point
        boolean isPositive = pointVector.dotProduct(normals[0]) > 0;//checking the saign of the first product
        for (Vector normal : normals) {//check the product of all the normals with the point vector (calculating also the first one so we can check if it 0)
            if ((normal.dotProduct(pointVector) > 0 ^ isPositive) || alignZero(normal.dotProduct(pointVector)) == 0) {//to check if the products have different sign we can use XOR
                return false;
            }
        }
        return true;
    }


    public Intersectable move(Vector x) {
        super.move(x);
        //move all the vertices of the polygon
        this.vertices = this.vertices.stream().map(v -> v.add(x)).collect(Collectors.toList());
        //next, we want to move the plane
        this.plane.move(x);
        return this;
    }

    /**
     * get the center of the polygon
     *
     * @return the center of the polygon
     */
    public Point3D getCenter() {
        //centroid of polygon : sum(vertices)/n
        Point3D sum = Point3D.ZERO;
        for (var v : this.vertices) {
            if (!v.equals(Point3D.ZERO)) {
                sum = sum.add(new Vector(v));
            }
        }
        if (sum.equals(Point3D.ZERO)) {
            return Point3D.ZERO;
        }
        return (new Vector(sum)).scale(1 / vertices.size()).getHead();
    }


    @Override
    public JSONObject toJSON() {
        JSONObject ret = super.toJSON();
        ret.put("type", "Polygon");
        ret.put("plane", this.plane.toJSON());
        JSONArray vertices = new JSONArray();
        for (Point3D pt : this.vertices) {
            vertices.add(pt.toJSON());
        }
        ret.put("vertices", vertices);
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        super.load(json);
        this.plane.load((JSONObject) json.get("plane"));
        JSONArray jsonVertices = (JSONArray) json.get("vertices");
        Point3D[] vertices = new Point3D[jsonVertices.size()];
        for (int i = 0; i < vertices.length; i++) {
            JSONObject currentJson = (JSONObject) jsonVertices.get(i);
            vertices[i] = (Point3D) Point3D.ZERO.load(currentJson);
        }
        this.vertices = List.of(vertices);
        return this;
    }

    @Override
    public Intersectable rotate(Vector euler) {
        this.vertices = this.vertices.stream().map(v -> v.rotate(euler)).collect(Collectors.toList());
        this.plane = new Plane(this.vertices.get(0), this.vertices.get(1), this.vertices.get(2));
        return this;
    }
    
}
