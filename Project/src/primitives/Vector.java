package primitives;

import org.json.simple.JSONObject;

public class Vector implements Serializable {

    static public final double ACCURACY = 0.00001;
    /**
     * the head of the vector
     */
    private Point3D head;

    public Point3D getHead() {
        return head;
    }

    /**
     * vector constructor
     *
     * @param x the head x axis
     * @param y the head x axis
     * @param z the head x axis
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D head = new Point3D(x, y, z);
        if (head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head = head;
    }

    /**
     * vector constructor
     *
     * @param x the head x value
     * @param y the head y value
     * @param z the head z value
     */
    public Vector(double x, double y, double z) {
        Point3D head = new Point3D(x, y, z);
        if (head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head = head;
    }

    /**
     * vector constructor
     *
     * @param head the head of vector
     */
    public Vector(Point3D head) {
        if (head.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("WRONG INPUT: cannot receive zero vector");
        }
        this.head = head;
    }

    /**
     * vectors addition
     *
     * @param vec
     * @return sum of vectors
     */
    public Vector add(Vector vec) {
        return new Vector(this.head.add(vec));
    }

    /**
     * vectors subtraction
     *
     * @param vec
     * @return subtract of vectors
     */
    public Vector subtract(Vector vec) {
        return this.head.subtract(vec.head);
    }

    /**
     * vector multiplication by scalar
     *
     * @param d
     * @return multiplied vector
     */
    public Vector scale(double d) {
        return new Vector(this.head.mult(d));
    }

    /**
     * scalar multiplication between two vectors
     *
     * @param vec
     * @return scalar
     */
    public double dotProduct(Vector vec) {
        return this.head.x.coord * vec.head.x.coord +
                this.head.y.coord * vec.head.y.coord +
                this.head.z.coord * vec.head.z.coord;
    }

    /**
     * vector multiplication between two vectors
     *
     * @param vec
     * @return vector
     */
    public Vector crossProduct(Vector vec) {
        // check if vectors are parallel
        /**if (Math.abs(this.normalized().dotProduct(vec.normalized())) > 1d - ACCURACY)
         {
         throw new IllegalArgumentException("cannot implement crossProduct() for parallel vectors");
         }**/

        return new Vector(this.head.y.coord * vec.head.z.coord - this.head.z.coord * vec.head.y.coord,
                this.head.z.coord * vec.head.x.coord - this.head.x.coord * vec.head.z.coord,
                this.head.x.coord * vec.head.y.coord - this.head.y.coord * vec.head.x.coord);
    }

    /**
     * squared length of vector
     *
     * @return squared length
     */
    public double lengthSquared() {
        return this.head.distanceSquared(Point3D.ZERO);
    }

    /**
     * length of vector
     *
     * @return length
     */
    public double length() {
        return this.head.distance(Point3D.ZERO);
    }

    /**
     * normalize vector
     *
     * @return normalize the vector himself and return it
     */
    public Vector normalize() { // BEWARE! SHALLOW ASSIGNING!!!
        this.head = this.head.mult(1D / this.length());
        return this;
    }

    /**
     * normalize vector
     *
     * @return a new vector is return
     */
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

    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("type", "Vector");
        ret.put("x", head.getX());
        ret.put("y", head.getY());
        ret.put("z", head.getZ());
        return ret;
    }

    @Override
    public Vector load(JSONObject json) {
        double x = (double) json.get("x");
        double y = (double) json.get("y");
        double z = (double) json.get("z");
        this.head = new Point3D(x, y, z);
        return this;
    }

    /**
     * rotate the vector with the given euler angle vector (degrees)
     *
     * @param euler the rotation vector
     * @return this
     */
    public Vector rotate(Vector euler) {
      this.head = this.head.rotate(euler);
      return this;
    }

}
