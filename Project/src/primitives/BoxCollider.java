package primitives;

import geometries.Polygon;

public class BoxCollider {
    private final String SCALE_CANT_BE_ZERO_EXCEPTION_TEXT = "the value of scale cant be 0 or have negative number";

    /**
     * the center of the box
     */
    private Point3D position;
    /**
     * the scale of the box
     */
    private Point3D scale;
    private Polygon[] box;


    public BoxCollider(Point3D position, Point3D scale) {
        if (scale.getZ() <= 0 || scale.getY() <= 0 || scale.getX() <= 0) {
            throw new IllegalArgumentException(SCALE_CANT_BE_ZERO_EXCEPTION_TEXT);
        }
        this.position = position;
        this.scale = scale;
        generateBox();
    }

    public BoxCollider(Point3D position, double scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException(SCALE_CANT_BE_ZERO_EXCEPTION_TEXT);
        }
        this.position = position;
        this.scale = new Point3D(scale, scale, scale);
        generateBox();
    }

    public Point3D getPosition() {
        return position;
    }

    public Point3D getScale() {
        return scale;
    }

    public boolean isIntersecting(Ray ray, double maxDistance) {
        for (var plane : box) {
            if (plane.findGeoIntersections(ray, maxDistance) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * move the collider
     *
     * @param x the movment vector
     * @return this
     */
    public BoxCollider move(Vector x) {
        for (var plane : box) {
            plane.move(x);
        }
        return this;
    }

    /**
     * get the vertex according to the sign of the axis
     *
     * @param x is the x axis positive
     * @param y is the x axis positive
     * @param z is the x axis positive
     * @return the vertex according to the shape scale
     */
    private Point3D generateVertex(boolean x, boolean y, boolean z) {
        return new Point3D(
                this.scale.getX() * (x ? 0.5 : -0.5) + this.position.getX(),
                this.scale.getY() * (y ? 0.5 : -0.5) + this.position.getY(),
                this.scale.getZ() * (z ? 0.5 : -0.5) + this.position.getZ()
        );
    }

    private void generateBox() {
        //set the collider box
        this.box = new Polygon[]{
                //right
                new Polygon(
                        generateVertex(true, true, true),
                        generateVertex(true, false, true),
                        generateVertex(true, false, false),
                        generateVertex(true, true, false)
                ),
                //top
                new Polygon(
                        generateVertex(true, true, true),
                        generateVertex(false, true, true),
                        generateVertex(false, true, false),
                        generateVertex(true, true, false)
                ),
                //back
                new Polygon(
                        generateVertex(true, true, true),
                        generateVertex(false, true, true),
                        generateVertex(false, false, true),
                        generateVertex(true, false, true)
                ),
                //front
                new Polygon(
                        generateVertex(true, true, false),
                        generateVertex(false, true, false),
                        generateVertex(false, false, false),
                        generateVertex(true, false, false)
                ),
                //left
                new Polygon(
                        generateVertex(false, false, false),
                        generateVertex(false, false, true),
                        generateVertex(false, true, true),
                        generateVertex(false, true, false)
                ),
                //bottom
                new Polygon(
                        generateVertex(false, false, false),
                        generateVertex(false, false, true),
                        generateVertex(true, false, true),
                        generateVertex(true, false, false)

                )
        };
    }

}
