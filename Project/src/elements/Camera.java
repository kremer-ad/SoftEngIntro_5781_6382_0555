package elements;

import geometries.Intersectable;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


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
        return lookAtTransform(lookFrom, lookAt, new Vector(0, 1, 0));
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

    public Camera lookAtTransform(Point3D lookFrom, Point3D lookAt, Vector normal) {

        // if old vUp and new vTo already orthogonal, set only vTo and vRight
        if (vUp.dotProduct(lookFrom.subtract(lookAt)) == 0) {
            this.vTo = lookAt.subtract(lookFrom).normalized();
            this.vRight = this.vTo.crossProduct(this.vUp);
            position = lookFrom;
            return this;
        }

        // if new vTo orthogonal to normal
        if (lookAt.subtract(lookFrom).dotProduct(normal) == 0) {
            vTo = lookAt.subtract(lookFrom).normalized();
            vUp = normal.normalized();
            vRight = vTo.crossProduct(vUp);
            position = lookFrom;
            return this;
        }

        // if intersection point is equal to lookAt point - set only vUp and vTo
        if (lookAt.subtract(lookFrom).normalized().equals(normal.normalized()) ||
                lookAt.subtract(lookFrom).normalized().equals(normal.normalized().scale(-1))) {
            this.vTo = lookAt.subtract(lookFrom).normalized();
            this.vUp = vRight.crossProduct(vTo);
            position = lookFrom;
            return this;
        }

        Ray ray = new Ray(lookFrom, normal.scale(-1d));
        List<Intersectable.GeoPoint> intersections = new Plane(lookAt, normal).findGeoIntersections(ray);

        // if camera is under plane - set ray direction again
        if (intersections == null) {
            ray = new Ray(lookFrom, normal);
            intersections = new Plane(lookAt, normal).findGeoIntersections(ray);
        }

        // set new directions
        this.vRight = lookAt.subtract(intersections.get(0).point).crossProduct(normal).normalized();
        this.vTo = lookAt.subtract(lookFrom).normalized();
        this.vUp = this.vRight.crossProduct(this.vTo);
        position = lookFrom;
        return this;
    }


    /**
     * receive two angles (degrees) and point to look at,
     * and calculate new point on sphere to look from,
     * sphere's radius is the distance between camera's current position and lookAt point
     *
     * @param theta
     * @param phi
     * @param lookAt
     * @return
     */
    public Point3D calcPointOnSphere(double phi, double theta, Point3D lookAt) {
        theta = Math.toRadians(theta);
        phi = Math.toRadians(phi);

        double radius = position.distance(lookAt);

        double z = radius * Math.cos(phi) * Math.sin(theta);
        double x = radius * Math.sin(phi) * Math.sin(theta);
        double y = radius * Math.cos(theta);
        return new Point3D(x, y, z);
    }

    public Point3D calcPointOnVector(Vector vec, double t) {
        if (t == 0) {
            return this.position;
        }
        return new Ray(position, vec).getPoint(t);
        //return new Point3D(radius * Math.cos(Math.toRadians(j)), 30, radius * Math.sin(Math.toRadians(j)));

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
        return this.constructRaysThroughPixel(nX, nY, j, i, 1)[0];
        /*
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
        */

    }

    /**
     * Generate a rays from camera to a given pixel
     * <p>
     * the first ray pixel always hit the middle
     * the next 4 will hit the pixel edges
     * the other will hit the pixel randomly
     *
     * @param nX     - number of pixels for width
     * @param nY     - number of pixels for height
     * @param j      - number of column in row
     * @param i      - number of row in column
     * @param amount - the amount of rays to construct
     * @return A ray from the camera to the given pixel
     */
    public Ray[] constructRaysThroughPixel(int nX, int nY, int j, int i, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("cant throw less then one ray threw the pixel");
        }
        Ray[] ret = new Ray[amount];

        // calculate image center: pC=p0+d*vTo
        Point3D pCenter = position.add(vTo.scale(distance));

        //calculate the middle of the pixel and put it at the start of the array
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
        ret[0] = new Ray(position, pIJ.subtract(position));

        //start putting the edges of the pixel in the array
        //top right
        //Point3D pIJCenter = pIJ;
        if (amount > 1) {
            ret[1] = new Ray(position, moveInPixel(pIJ, 1, 1, rX, rY).subtract(position));
        }
        //bottom right
        if (amount > 2) {
            ret[2] = new Ray(position, moveInPixel(pIJ, 1, 0, rX, rY).subtract(position));
        }
        //bottom left
        if (amount > 3) {
            ret[3] = new Ray(position, moveInPixel(pIJ, 0, 0, rX, rY).subtract(position));
        }
        //top left
        if (amount > 4) {
            ret[4] = new Ray(position, moveInPixel(pIJ, 0, 1, rX, rY).subtract(position));
        }

        //start throwing random rays on the pixel and add them to the array
        for (int k = 5; k < amount; k++) {
            ret[k] = new Ray(position, moveInPixel(pIJ, Math.random(), Math.random(), rX, rY).subtract(position));
        }

        return ret;
    }

    /**
     * returm point thet moved in the pixel boundary
     *
     * @param pt the middle of the pixel
     * @param x  the ratio to move in x [0,1]
     * @param y  the ration to move in y [0,1]
     * @param rX the pixel width
     * @param rY the pixel height
     * @return the moved point
     */
    private Point3D moveInPixel(Point3D pt, double x, double y, double rX, double rY) {
        Point3D ret = pt;
        double moveX = rX * (x - 0.5);
        double moveY = rY * (y - 0.5);
        if (!isZero(moveX)) {
            ret = ret.add(vRight.scale(moveX));
        }
        if (!isZero(moveY)) {
            ret = ret.add(vUp.scale(moveY));
        }
        return ret;
    }
}
