package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {

    /**
     * location of camera
     */
    private Point3D p0;

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
    public Point3D getP0() {
        return p0;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    /**
     * constructor: gets vTo and vUp and generate vRight if values are valid
     *
     * @param p0  - camera's location
     * @param vTo
     * @param vUp
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vTo = vTo.normalized();
        this.vUp = vUp.normalized();

        // check if valid
        if (vTo.dotProduct(vUp) != 0) {
            throw new IllegalArgumentException("Non-vertical vectors");
        }
        vRight = vTo.crossProduct(vUp); // set vRight value
    }

    /**
     * Builder element to set viewPlans's width and height
     *
     * @param width
     * @param height
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
     * @param distance
     * @return this
     */
    public Camera setDistance(double distance) {
        this.distance = distance;

        return this;
    }

    /**
     * Generate a ray from camera to a middle of a given pixel
     *
     * @param nX - number of pixels for width
     * @param nY - number of pixels for height
     * @param j  - number of column in row
     * @param i  - number of row in column
     * @return
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        // calculate image center: pC=p0+d*vTo
        Point3D pCenter = p0.add(vTo.scale(distance));

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

        return new Ray(p0, pIJ.subtract(p0));
    }

}
