package geometries;

import org.json.simple.JSONObject;
import primitives.*;

import java.util.List;

public class Wheel implements Intersectable, Serializable {
    private Geometries shapes;
    private Point3D position;
    private BoxCollider collider;

    public Wheel(double radius, double height, double hole, int sticksNumber) {
        Geometry outerRing = new Ring(new Ray(Point3D.ZERO, new Vector(0, 0, 1)), radius, hole, height);
        Geometry[] sticks = new Cylinder[sticksNumber];
        for (int i = 1; i <= sticks.length; i++) {
            Ray axisStick = new Ray(Point3D.ZERO, new Vector(1, 0, 0));
            axisStick.rotate(new Vector(0D, 0D, i * (360D / sticksNumber)));
            sticks[i - 1] = new Cylinder(axisStick, 2, hole);
        }
        Geometry innerBall = new Sphere(Point3D.ZERO, 20D);
        this.position = Point3D.ZERO;
        this.shapes = new Geometries();
        shapes.add(outerRing, innerBall);
        shapes.add(sticks);
        this.collider = new BoxCollider(this.position, Math.max(height, radius * 2));//due to rotations problem the box will be always cube
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return shapes.findGeoIntersections(ray, maxDistance);
    }

    @Override
    public Intersectable move(Vector x) {
        if (this.collider != null) {
            this.collider.move(x);
        }
        this.position = this.position.add(x);
        shapes.move(x);
        return this;
    }

    public Point3D getPosition() {
        return this.position;
    }

    @Override
    public Intersectable rotate(Vector euler) {
        if (!position.equals(Point3D.ZERO)) {
            Point3D tempPos = this.position;
            this.move(new Vector(position).scale(-1D));
            shapes.rotate(euler);
            this.move(new Vector(tempPos));
            return this;
        }
        shapes.rotate(euler);
        return this;
    }

    @Override
    public BoxCollider getCollider() {
        return collider;
    }

    @Override
    public JSONObject toJSON() {
        return shapes.toJSON();
    }

    @Override
    public Serializable load(JSONObject json) {
        shapes.load(json);
        return this;
    }

    public Wheel setMaterial(Material material) {
        for (var shape : this.shapes.shapes) {
            ((Geometry) shape).setMaterial(material);
        }
        return this;
    }

    public Wheel setEmission(Color color) {
        for (var shape : this.shapes.shapes) {
            ((Geometry) shape).setEmission(color);
        }
        return this;
    }

    public Wheel setCollider(BoxCollider collider) {
        this.collider = collider;
        return this;
    }

    public Geometries getShapes() {
        return this.shapes;
    }
}
