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
        this.wheels[0] = new Wheel(125D, 50, 100, 12).setEmission(emission).setMaterial(material);
        this.wheels[0].move(new Vector(0, 125, 0));
        this.wheels[1] = new Wheel(125D, 50, 100, 12).setEmission(emission).setMaterial(material);
        this.wheels[1].move(new Vector(800, 125, 0));
        this.wheels[2] = new Wheel(125D, 50, 100, 12).setEmission(emission).setMaterial(material);
        this.wheels[2].move(new Vector(0, 125, 300));
        this.wheels[3] = new Wheel(125D, 50D, 100, 12).setEmission(emission).setMaterial(material);
        this.wheels[3].move(new Vector(800, 125, 300));

        //the wheel holders
        Geometry[] holders = {
                new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 20, 800).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 20, 800).setEmission(emission).setMaterial(material)
        };
        holders[0].move(new Vector(0, 125, 0));
        holders[1].move(new Vector(0, 125, 300));

        Geometries wheelsGeos = new Geometries(wheels);
        wheelsGeos.add(holders);
        wheelsGeos.setCollider(new BoxCollider(new Point3D(400,125,150),new Point3D(1000,300,1000)));

        this.data.add(wheelsGeos);

        //the body of the machine
        Box[] body = {
                new Box(new Point3D(800, 30, 250), new Point3D(400, 140, 175)).setEmission(emission).setMaterial(material),
                new Box(new Point3D(780, 100, 240), new Point3D(400, 205, 175)).setEmission(emission).setMaterial(material),
                new Box(new Point3D(800, 30, 250), new Point3D(400, 270, 175)).setEmission(emission).setMaterial(material)
        };
       data.add(body);
        //the kiyor of  thr machine
        Geometry[] kiyor = {
                new Cylinder(new Ray(new Point3D(25, 285, 75), new Vector(0, 1, 0)), 20, 100).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(775, 285, 75), new Vector(0, 1, 0)), 20, 100).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(25, 285, 275), new Vector(0, 1, 0)), 20, 100).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(775, 285, 275), new Vector(0, 1, 0)), 20, 100).setEmission(emission).setMaterial(material),
                new Cylinder(new Ray(new Point3D(400, 285, 175), new Vector(0, 1, 0)), 130, 60).setEmission(emission).setMaterial(material)
        };
        Geometries kiyorGeos = new Geometries(kiyor)
                .setCollider(new BoxCollider(new Point3D(400,325,175),new Point3D(1000,300,400)));

        data.add(kiyorGeos);

        //the head of the kiyor
        data.add(new Box(new Point3D(800, 30, 250), new Point3D(400, 400, 175)).setEmission(emission).setMaterial(material));

        this.data.setCollider(new BoxCollider(new Point3D(400, 150, 150), 2000));


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
