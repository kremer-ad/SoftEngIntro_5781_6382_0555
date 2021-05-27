package elements;

import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.alignZero;


public class Camera {

    /**
     * location of camera
     */
    private Point3D position;

    /**
     * three directory vectors for camera
     */
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;

    /**
     * three variables to define view plane of camera
     */
    private double width;
    private double height;
    private double distance;

    /**
     * getters
     */
    public Point3D getPosition() {
        return position;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    /**
     * constructor: gets vTo and vUp and generate vRight if values are valid
     *
     * @param position - camera's location
     * @param vTo      the forward direction vector
     * @param vUp      the up direction vector
     */
    public Camera(Point3D position, Vector vTo, Vector vUp) {
        this.position = position;
        this.vTo = vTo.normalized();
        this.vUp = vUp.normalized();

        // check if valid
        if (vTo.dotProduct(vUp) != 0) {
            throw new IllegalArgumentException("Non-vertical vectors");
        }
        vRight = this.vTo.crossProduct(this.vUp); // set vRight value
    }

    /**
     * Builder element to set viewPlans's width and height
     *
     * @param width  the view plane width
     * @param height the view plane width
     * @return this
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;

        return this;
    }

    /**
     * Builder element to set viewPlans's distance from camera
     *
     * @param distance the view plane distance from the camera
     * @return this
     */
    public Camera setDistance(double distance) {
        this.distance = distance;

        return this;
    }

    /**
     * Rotate camera about vTo using "Rodriguez' rotation formula"
     *
     * @param angle - gets degrees angle
     * @return this
     */

    public Camera rotate(double angle) {
        angle = Math.toRadians(angle);
        if (alignZero(Math.cos(angle)) == 0) { //avoiding zero vector creation when cos(angle)=0
            this.vUp = this.vRight.scale(Math.sin(angle));
        } else if (alignZero(Math.sin(angle)) == 0) { //avoiding zero vector creation when sin(angle)=0
            this.vUp = this.vUp.scale(Math.cos(angle));
        } else { // Rodriguez' rotation formula
            this.vUp = this.vUp.scale(alignZero(Math.cos(angle))).add(this.vRight.scale(alignZero(Math.sin(angle))));
        }
        this.vRight = this.vTo.crossProduct(this.vUp); // set vRight

        return this;
    }

    /**
     * receive two points: lookAt point for the point we want to look at
     * and lookFrom for the point we want to look from it,
     * than a transformation is done,
     * all direction vector are redefined according to last position of camera.
     *
     * @param lookFrom
     * @param lookAt
     * @return
     */
    public Camera lookAtTransform(Point3D lookFrom, Point3D lookAt) {
            // if old vUp and new vTo already orthogonal, set only vTo and vRight
            if (vUp.dotProduct(lookFrom.subtract(lookAt)) == 0) {
                this.vTo = lookAt.subtract(lookFrom).normalized();
                this.vRight = this.vTo.crossProduct(this.vUp);
                position = lookFrom;
                return this;
            }

            Ray ray = new Ray(lookFrom, vUp.scale(-1d));
            // if camera is under plane - set ray direction again
            if (new Plane(position, vUp).findGeoIntersections(ray) == null) {
                ray = new Ray(lookFrom, vUp);
            }

            Point3D intersectionWithPlane = new Plane(position, vUp).findGeoIntersections(ray).get(0).point;
            // if intersection point is equal to lookAt point - set only vUp and vTo
            if (intersectionWithPlane.equals(lookAt)) {
                this.vTo = lookAt.subtract(lookFrom).normalized();
                this.vUp = vRight.crossProduct(vTo);
                return this;
            }
        // set new directions
        this.vRight = lookAt.subtract(intersectionWithPlane).crossProduct(vUp).normalized();
        this.vTo = lookAt.subtract(lookFrom).normalized();
        this.vUp = vRight.crossProduct(vTo);
        position = lookFrom;
        return this;
    }

    /**
     * receive to angles (degrees) and point to look at,
     * and calculate new point on sphere to look from,
     * sphere's radius is the distance between camera's current position and lookAt point
     *
     * @param theta
     * @param phi
     * @param lookAt
     * @return
     */
    public Point3D calcPointOnSphere(double theta, double phi, Point3D lookAt) {
        theta = Math.toRadians(theta);
        phi = Math.toRadians(phi);

        double radius = position.distance(lookAt);
        double x = radius * Math.cos(phi) * Math.sin(theta);
        double y = radius * Math.sin(phi) * Math.sin(theta);
        double z = radius * Math.cos(theta);
        return new Point3D(x, y, z);
    }

    /**
     * Generate a ray from camera to a middle of a given pixel
     *
     * @param nX - number of pixels for width
     * @param nY - number of pixels for height
     * @param j  - number of column in row
     * @param i  - number of row in column
     * @return A ray from the camera to the given pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        // calculate image center: pC=p0+d*vTo
        Point3D pCenter = position.add(vTo.scale(distance));

        // calculate ratio (pixel width and height)
        double rY = height / nY;
        double rX = width / nX;
        // calculate pixel[i,j] center
        double yI = -(i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        // in case yI or xJ are zero exception will be thrown
        // to avoid it we will handle them step by step
        Point3D pIJ = pCenter;
        if (!Util.isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        return new Ray(position, pIJ.subtract(position));
    }
}
