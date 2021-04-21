package primitives;

import java.text.DecimalFormat;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class Point3D {
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
        return (this.x.coord - pnt.x.coord) * (this.x.coord - pnt.x.coord) +
                (this.y.coord - pnt.y.coord) * (this.y.coord - pnt.y.coord) +
                (this.z.coord - pnt.z.coord) * (this.z.coord - pnt.z.coord);
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
}
