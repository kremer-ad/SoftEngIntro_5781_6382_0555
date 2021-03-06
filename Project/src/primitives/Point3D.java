package primitives;

import org.json.simple.JSONObject;

import java.text.DecimalFormat;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Point3D implements Serializable {
    /**
     * the point x axis data
     */
    final Coordinate x;
    /**
     * the point y axis data
     */
    final Coordinate y;
    /**
     * the point z axis data
     */
    final Coordinate z;

    /**
     * getters
     */
    public double getX() {
        return x.coord;
    }

    public double getY() {
        return y.coord;
    }

    public double getZ() {
        return z.coord;
    }

    /**
     * point with the values (0,0,0)
     */
    static public final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * Create new point
     *
     * @param x the point x axis
     * @param y the point y axis
     * @param z the point z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @param x the x axis of the point
     * @param y the y axis of the point
     * @param z the z axis of the point
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * add vector to point
     *
     * @param vec to add
     * @return point
     */
    public Point3D add(Vector vec) {
        return new Point3D(this.x.coord + vec.getHead().x.coord,
                this.y.coord + vec.getHead().y.coord,
                this.z.coord + vec.getHead().z.coord);
    }

    /**
     * subtract point from vector
     *
     * @param pnt to subtract
     * @return vector
     */
    public Vector subtract(Point3D pnt) {
        return new Vector(this.x.coord - pnt.x.coord,
                this.y.coord - pnt.y.coord,
                this.z.coord - pnt.z.coord);
    }

    /**
     * calculate squared distance between two points, using linear algebra formula
     *
     * @param pnt
     * @return squared distance
     */
    public double distanceSquared(Point3D pnt) {
        return alignZero((this.x.coord - pnt.x.coord) * (this.x.coord - pnt.x.coord) +
                (this.y.coord - pnt.y.coord) * (this.y.coord - pnt.y.coord) +
                (this.z.coord - pnt.z.coord) * (this.z.coord - pnt.z.coord));
    }

    /**
     * return distance, using squared distance function
     *
     * @param pnt
     * @return
     */
    public double distance(Point3D pnt) {
        return sqrt(distanceSquared(pnt));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Point3D)) return false;
        Point3D pnt = (Point3D) o;
        return this.x.equals(pnt.x) && this.y.equals(pnt.y) && this.z.equals(pnt.z);
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s)",
                new DecimalFormat("#.##").format(x.coord),
                new DecimalFormat("#.##").format(y.coord),
                new DecimalFormat("#.##").format(z.coord));
    }

    /**
     * handler internal function, multiplication between point3D and scalar (double)
     *
     * @param multiplier
     * @return point3D
     */
    Point3D mult(double multiplier) {
        return new Point3D(this.x.coord * multiplier, this.y.coord * multiplier, this.z.coord * multiplier);
    }


    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("type", "Point3d");
        ret.put("x", x);
        ret.put("y", y);
        ret.put("z", z);
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        double x = (double) json.get("x");
        double y = (double) json.get("y");
        double z = (double) json.get("z");
        return new Point3D(x, y, z);
    }

    /**
     * rotate the current point with euler vector, return new point
     *
     * @param euler the rotation vector
     * @return the rotated point
     */
    public Point3D rotate(Vector euler) {

        double[] result = {this.x.coord, this.y.coord, this.z.coord};
        double angle;
        double firstParam, secondParam;//we need to save data from result before we change it
        //rotate around z axis
        /*
         |cos ?? ???sin ??   0| |x|   |x cos ?? ??? y sin ??|   |x'|
         |sin ??  cos ??   0| |y| = |x sin ?? + y cos ??| = |y'|
         | 0      0      1| |z|   |        z        |   |z'|
         */
        if (!isZero(euler.getHead().getZ())) {
            angle = degreeToSimpleRadians(euler.getHead().getZ());
            firstParam = result[0];
            secondParam = result[1];
            result[0] = alignZero(Math.cos(angle)) * firstParam - alignZero(Math.sin(angle)) * secondParam;
            result[1] = alignZero(Math.sin(angle)) * firstParam + alignZero(Math.cos(angle)) * secondParam;
        }
        //rotate around y axis
        /*
        | cos ??    0   sin ??| |x|   | x cos ?? + z sin ??|   |x'|
        |   0      1       0| |y| = |         y        | = |y'|
        |???sin ??    0   cos ??| |z|   |???x sin ?? + z cos ??|   |z'|
         */
        if (!isZero(euler.getHead().getY())) {
            firstParam = result[0];
            secondParam = result[2];
            angle = degreeToSimpleRadians(euler.getHead().getY());
            result[0] = alignZero(Math.cos(angle)) * firstParam + alignZero(Math.sin(angle)) * secondParam;
            result[2] = alignZero(Math.cos(angle)) * secondParam - alignZero(Math.cos(angle)) * firstParam;
        }
        //rotate around x axis
        /*
        |1     0           0| |x|   |        x        |   |x'|
        |0   cos ??    ???sin ??| |y| = |y cos ?? ??? z sin ??| = |y'|
        |0   sin ??     cos ??| |z|   |y sin ?? + z cos ??|   |z'|
         */
        if (!isZero(euler.getHead().getX())) {
            firstParam = result[1];
            secondParam = result[2];
            angle = degreeToSimpleRadians(euler.getHead().getX());
            result[1] = alignZero(Math.cos(angle)) * firstParam - alignZero(Math.sin(angle)) * secondParam;
            result[2] = alignZero(Math.sin(angle)) * firstParam + alignZero(Math.cos(angle)) * secondParam;
        }
        //the calculation change the position pof the point -> fix it
        double distanceFromZero = this.distance(Point3D.ZERO);//first get the actual distance
        //now set the distance of the point
        Point3D resultPt = new Point3D(result[0], result[1], result[2]);
        if (resultPt.equals(Point3D.ZERO)) {
            return resultPt;
        }
        Vector vecPt = new Vector(resultPt).normalize().scale(distanceFromZero);// distVec= norm(v)*dist

        return new Point3D(vecPt.getHead().x, vecPt.getHead().y, vecPt.getHead().z);
    }

    /**
     * transform the degrees to radians (also set the degree between -180 and 180
     *
     * @param angle the degree
     * @return the radians
     */
    private double degreeToSimpleRadians(double angle) {
        angle %= 360;
        if (angle > 180) {
            angle -= 360;
        }
        return Math.toRadians(angle);
    }
}
