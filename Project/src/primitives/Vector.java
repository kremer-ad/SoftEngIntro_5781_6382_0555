package primitives;

import java.util.Objects;

import static java.lang.Math.sqrt;

public class Vector {

    private Point3D head;

    public Point3D getHead() {
        return head;
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D temp = new Point3D(x, y, z);
        if (temp.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head = temp;
    }

    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(x, y, z);
        if (temp.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head = temp;
    }

    public Vector(Point3D head) {
        if (head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
    }

    public Vector add(Vector vec) {
        return new Vector(this.head.add(vec));
    }

    public Vector subtract(Vector vec) {
        return this.head.subtract(vec.head);
    }

    public Vector scale(double d) {
        return new Vector(this.head.mult(d));
    }

    public double dotProduct(Vector vec) {
        return this.head.x.coord * vec.head.x.coord +
                this.head.y.coord * vec.head.y.coord +
                this.head.z.coord * vec.head.z.coord;
    }

    public Vector crossProduct(Vector vec) {
        return new Vector(this.head.y.coord * vec.head.z.coord - this.head.z.coord * vec.head.y.coord,
                this.head.z.coord * vec.head.x.coord - this.head.x.coord * vec.head.z.coord,
                this.head.x.coord * vec.head.y.coord - this.head.y.coord * vec.head.x.coord);
    }

    public double lengthSquared() {
        return this.head.distanceSquared(Point3D.ZERO);
    }

    public double length() {
        return this.head.distance(Point3D.ZERO);
    }

    public Vector normalize() { // BEWARE! SHALLOW ASSIGNING!!!
        this.head = this.head.mult(1D/this.length());
        return this;
    }

    public Vector normalized() {
        Vector ret = new Vector(this.head);
        return ret.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Vector)) return false;
        Vector other = (Vector) o;
        return this.head.equals(other.head);
    }

    @Override
    public String toString() {
        return "Vec{" + head + '}';
    }
}
