package geometries;

import primitives.*;

import java.util.List;

public class Machine implements Intersectable {
    Geometries data;
    Wheel[] wheels;

    public Machine(Material material, Color emission) {
        this.data = new Geometries();
        this.wheels = new Wheel[4];

        //set the wheels
        this.wheels[0] = new Wheel(250D, 50, 200, 12).setEmission(emission).setMaterial(material);
        this.wheels[0].move(new Vector(0, 125, 0));
        this.wheels[1] = new Wheel(250D, 50, 200, 12).setEmission(emission).setMaterial(material);
        this.wheels[1].move(new Vector(300, 125, 0));
        this.wheels[2] = new Wheel(250D, 50, 200, 12).setEmission(emission).setMaterial(material);
        this.wheels[2].move(new Vector(0, 125, 300));
        this.wheels[3] = new Wheel(250D, 50D, 200, 12).setEmission(emission).setMaterial(material);
        this.wheels[3].move(new Vector(300, 125, 300));
        this.data.add(wheels);

        //the wheel holders
        Geometry[] holders = {
                new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 20, 300).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 20, 300).setEmission(emission).setMaterial(material)
        };
        holders[0].move(new Vector(0, 125, 0));
        holders[1].move(new Vector(0, 125, 300));
        data.add(holders);

        //the body of the machine
        Box[] body = {
                new Box(new Point3D(225, 30, 400), new Point3D(150, 150, 150)).setEmission(emission).setMaterial(material),
                new Box(new Point3D(210, 100, 385), new Point3D(150, 220, 150)).setEmission(emission).setMaterial(material),
                new Box(new Point3D(225, 30, 400), new Point3D(150, 280, 150)).setEmission(emission).setMaterial(material)
        };
        data.add(body);

        //the kiyor of  thr machine
        Geometry[] kiyor = {
                new Cylinder(new Ray(new Point3D(50, 300, -40), new Vector(0, 1, 0)), 20, 50).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(250, 300, -40), new Vector(0, 1, 0)), 20, 50).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(50, 300, 330), new Vector(0, 1, 0)), 20, 50).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(250, 300, 330), new Vector(0, 1, 0)), 20, 50).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(150, 300, 120), new Vector(0, 1, 0)), 220, 40).setEmission(emission).setMaterial(material)
        };
        data.add(kiyor);

        //the head of the kiyor
        data.add(new Box(new Point3D(225, 50, 400), new Point3D(150, 420, 150)).setEmission(emission).setMaterial(material));

        this.data.setCollider(new BoxCollider(new Point3D(200, 300, 200), 600));


    }

    public Machine setCollider(BoxCollider collider) {
        this.data.setCollider(collider);
        return this;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return this.data.findGeoIntersections(ray, maxDistance);
    }

    @Override
    public Intersectable move(Vector x) {
        return this.data.move(x);
    }

    @Override
    public Intersectable rotate(Vector euler) {
        return this;
    }

    @Override
    public BoxCollider getCollider() {
        return this.data.getCollider();
    }


    /**
     * rotate the wheels at the z axis
     *
     * @param angle the angle to rotate
     */
    public void rotateWheels(double angle) {
        for (var wheel : this.wheels) {
            wheel.rotate(new Vector(0, 0, angle));
        }
    }
}
