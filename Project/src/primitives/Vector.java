package primitives;

import java.util.Objects;

import static java.lang.Math.sqrt;

public class Vector {

    private Point3D head = null;

    public Point3D getHead() {
        return head;
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D temp = new Point3D(x, y, z);
        if (temp.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head=temp;
    }

    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(x, y, z);
        if (temp.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head=temp;
    }

    public Vector(Point3D head) {
        if (head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head = head;
    }

    public Vector add(Vector vec) {
        return new Vector(this.head.x.coord + vec.head.x.coord,
                this.head.y.coord + vec.head.y.coord,
                this.head.z.coord + vec.head.z.coord);
    }

    public Vector subtract(Vector vec) {
        return new Vector(this.head.x.coord - vec.head.x.coord,
                this.head.y.coord - vec.head.y.coord,
                this.head.z.coord - vec.head.z.coord);
    }

    public Vector scale(double d) {
        return new Vector(this.head.x.coord * d,
                this.head.y.coord * d,
                this.head.z.coord * d);
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
        return pow(this.head.x.coord) +
                pow(this.head.y.coord)+
                pow(this.head.z.coord);
    }

    public double length() {
        return sqrt(this.lengthSquared());
    }

    public Vector normalize() {
        this.head = this.normalized().head; // BEWARE! SHALLOW ASSIGNING!!!
        return this;
    }

    public Vector normalized() {
        return this.scale(1 / this.length());
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

    // Private methods

    private double pow(double d)
    {
        return d*d;
    }


}
