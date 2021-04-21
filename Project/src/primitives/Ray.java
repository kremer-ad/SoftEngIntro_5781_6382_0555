package primitives;

import java.util.Objects;

public class Ray {
    /**
     * beginning point of ray
     */
    private Point3D p0;

    /**
     * normalized direction vector of ray,
     */
    private Vector dir;

    /**
     * ray constructor
     * @param p0 beginning point of ray
     * @param dir gets direction vector of ray and normalize it
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    // Getters
    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    /**
     * Gets point on ray
     * @param t scale
     * @return point
     */
    public Point3D getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray other = (Ray) o;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    @Override
    public String toString() {
        return "Ray{" + p0 + dir + '}';
    }
}
