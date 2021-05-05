package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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
     * @param angle - gets radian angle
     * @return this
     */

    public Camera rotate(double angle) {
        if (Util.alignZero(Math.cos(angle)) == 0) { //avoiding zero vector creation when cos(angle)=0
            this.vUp = this.vRight.scale(Math.sin(angle));
        }
        else if (Util.alignZero(Math.sin(angle)) == 0) { //avoiding zero vector creation when sin(angle)=0
            this.vUp = this.vUp.scale(Math.cos(angle));
        }
        else { // Rodriguez' rotation formula
            this.vUp = this.vUp.scale(Util.alignZero(Math.cos(angle))).add(this.vRight.scale(Util.alignZero(Math.sin(angle))));
        }
        this.vRight = this.vTo.crossProduct(this.vUp); // set vRight

        return this;
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
        if (xJ != 0) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        return new Ray(position, pIJ.subtract(position));
    }
}
