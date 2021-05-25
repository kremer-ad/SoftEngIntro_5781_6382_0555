package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Ring extends Geometry {
    Cylinder outerCylinder;
    Cylinder holeCylinder;

    public Ring(Ray direction, double radius, double holeRadius, double height) {//we cant use builder because we cant make setters to cylinder parameters
        if (radius <= holeRadius) {
            throw new IllegalArgumentException("the hole radius cant be bigger then the ring radius");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("the ring height must be positive");
        }
        this.outerCylinder = new Cylinder(direction, radius, height);
        this.holeCylinder = new Cylinder(direction, holeRadius, height);
    }

    public double getHeight() {
        return this.outerCylinder.getHeight();
    }

    public double getRadius() {
        return this.outerCylinder.getRadius();
    }

    public double getHoleRadius() {
        return this.holeCylinder.radius;
    }


    @Override
    public Vector getNormal(Point3D pnt) {
        //first we need to know if the point is on the inner cylinder or on the hole cylinder
        //the point cant be on the bases of the hole so we dont need to check the bases
        //we will assume that the point is on the hole cylinder, calculate the nearest point on the ray to pnt and check if the distance between them will give us the radius of the cylinder
        //Pythagoras -> assumption = sqrt(x^2-r^2)
        double assumptionT = Math.sqrt(holeCylinder.getBasePoint().distanceSquared(pnt) - holeCylinder.getRadius() * holeCylinder.getRadius());
        Point3D assumptionPnt = holeCylinder.axisRay.getPoint(assumptionT);
        if (assumptionPnt.distance(pnt) == this.holeCylinder.getRadius()) {
            //the point is on the hole cylinder -> return the normal but with back direction
            return holeCylinder.getNormal(pnt).scale(-1);
        }

        //now we know that the point is on the outer side of the ring so we can use getNormal og outerCylinder
        return outerCylinder.getNormal(pnt);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> ret = this.findGeoIntersections(ray);
        if (ret == null) {
            return null;
        }
        return ret.stream().map(pt -> pt.point).collect(Collectors.toList());
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<Point3D> outerIntersections = outerCylinder.findIntersections(ray);
        List<Point3D> innerIntersections = holeCylinder.findIntersections(ray);
        //the intersection formula is: (CvH)-(C^H)
        List<Point3D> ret = new LinkedList<Point3D>();
        //there is no intersections -> return null
        if(innerIntersections==null&& outerIntersections==null){
            return null;
        }
        //C == null -> C^H == null -> (CvH)-(C^H) == H
        if (outerIntersections == null) {
            return innerIntersections.stream().map(pt->new GeoPoint(this,pt)).collect(Collectors.toList());
        }
        //H == null -> C^H == null -> (CvH)-(C^H) == C
        if (innerIntersections == null) {
            return outerIntersections.stream().map(pt->new GeoPoint(this,pt)).collect(Collectors.toList());
        }

        ret.addAll(outerIntersections);
        ret.addAll(innerIntersections);
        ret.removeIf(pt -> outerIntersections.contains(pt) && innerIntersections.contains(pt));
        if (ret.size() == 0) {
            return null;
        }
        return ret.stream().map(pt -> new GeoPoint(this, pt)).collect(Collectors.toList());
    }

    public Intersectable move(Vector x) {
        this.outerCylinder.move(x);
        this.holeCylinder.move(x);

        return this;
    }

    @Override
    public Intersectable rotate(Vector euler) {
        this.outerCylinder.rotate(euler);
        this.holeCylinder.rotate(euler);
        return this;
    }
}
