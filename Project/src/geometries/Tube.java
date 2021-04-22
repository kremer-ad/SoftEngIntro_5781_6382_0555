package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Infinity Tube
 */
public class Tube implements Geometry {
    /**
     * The axis of the Tube
     */
    protected Ray axisRay;

    /**
     * tha radius of the Tube
     */
    protected double radius;

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * @param axisRay The axis of the Tube
     * @param radius  tha radius of the Tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D pnt) {

        /*
         * Calculate normal for point P on tube, using normal equation:
         * t=v*(p-p0)
         * O=p0+t*v
         * normal=normalize(P-O)
         *
         */
        double t = axisRay.getDir().dotProduct(pnt.subtract(axisRay.getP0()));
        if (t == 0) { /* point is facing the head of the tube's ray */
            return pnt.subtract(axisRay.getP0()).normalize();
        }
        //return pnt.subtract(axisRay.getP0().add(axisRay.getDir().scale(t))).normalize();
        return pnt.subtract(axisRay.getPoint(t)).normalize();
    }

    @Override
    public String toString() {
        return "Tube{" + axisRay + radius + '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        //for better performances -> when need to do power we saving to temp instead of calling function twice
        double t1, t2;

        //the vectors and points that we need (insted of calling many functions many times we will call them once)
        Point3D rayOrigin = ray.getP0();
        Point3D tubeOrigin = this.axisRay.getP0();
        Vector rayDirection = ray.getDir();
        Vector tubeDirection = this.axisRay.getDir();

        //some equation variables
        double m = tubeDirection.dotProduct(tubeOrigin.subtract(tubeOrigin))/tubeDirection.lengthSquared();
        double n = rayDirection.lengthSquared()/tubeDirection.lengthSquared();

        //discriminant variables
        double a = rayDirection.lengthSquared() + 2 * n * rayDirection.dotProduct(tubeDirection) + n * n * tubeDirection.lengthSquared();
        double b = (-2) * m * tubeDirection.dotProduct(rayDirection) - 2 * m * n * tubeDirection.lengthSquared();
        double c = m*m*tubeDirection.lengthSquared()-this.radius*this.radius;

        if (!rayOrigin.equals(Point3D.ZERO)) {
            Vector rayOriginVec = new Vector(rayOrigin);
            b += 2 * rayDirection.dotProduct(rayOriginVec) + 2 * n * tubeDirection.dotProduct(rayOriginVec);
            c+=(-2)*m*tubeDirection.dotProduct(rayOriginVec);
            c+=rayOriginVec.lengthSquared();
        }
        if (!tubeOrigin.equals(Point3D.ZERO)) {
            Vector tubeOriginVec = new Vector(tubeOrigin);
            b += (-2) * tubeOriginVec.dotProduct(rayDirection) - 2 * n * tubeDirection.dotProduct(tubeOriginVec);
            c += 2 * m * tubeOriginVec.dotProduct(tubeDirection);
            c+=tubeOrigin.distanceSquared(Point3D.ZERO);

        }
        if (!tubeOrigin.equals(Point3D.ZERO) && !rayOrigin.equals(Point3D.ZERO)) {
            Vector tubeOriginVec = new Vector(tubeOrigin);
            Vector rayOriginVec = new Vector(rayOrigin);

            c+=(-2)*rayOriginVec.dotProduct(tubeOriginVec);
        }

        //calculate the discriminant
        double discriminant=b*b-4*a*c;
        if(discriminant<0){
            return null;
        }
        LinkedList<Point3D> ret= new LinkedList<>();
        double result1=((-b+Math.sqrt(discriminant))/(2*a));
        double result2=((-b-Math.sqrt(discriminant))/(2*a));

        ret.add(ray.getPoint(result1));
        ret.add(ray.getPoint(result2));
        return ret;
    }
}
