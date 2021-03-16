package primitives;

import java.text.DecimalFormat;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class Point3D {
    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    static public final Point3D ZERO = new Point3D(0, 0, 0);

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    public Point3D add(Vector vec) {
        return new Point3D(this.x.coord + vec.getHead().x.coord,
                this.y.coord + vec.getHead().y.coord,
                this.z.coord + vec.getHead().z.coord);
    }

    public Vector subtract(Point3D pnt) {
        return new Vector(this.x.coord - pnt.x.coord,
                this.y.coord - pnt.y.coord,
                this.z.coord - pnt.z.coord);
    }

    public double distanceSquared(Point3D pnt) {
        return (this.x.coord - pnt.x.coord) * (this.x.coord - pnt.x.coord) +
                (this.y.coord - pnt.y.coord) * (this.y.coord - pnt.y.coord) +
                (this.z.coord - pnt.z.coord) * (this.z.coord - pnt.z.coord);
    }

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
                new DecimalFormat("#.00").format(x),
                new DecimalFormat("#.00").format(y),
                new DecimalFormat("#.00").format(z));
    }

    Point3D mult(double multiplier){
        return new Point3D(this.x.coord*multiplier,this.y.coord*multiplier,this.z.coord*multiplier);
    }
}
