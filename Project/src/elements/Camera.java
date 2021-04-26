package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {

    private Point3D location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;

    public Point3D getLocation() {
        return location;
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

    public Camera(Point3D location, Vector vTo, Vector vUp) {
        this.location = location;
        this.vTo = vTo.normalized();
        this.vUp = vUp.normalized();

        if (vTo.dotProduct(vUp) != 0) {
            throw new IllegalArgumentException("Non-vertical vectors");
        }
        vRight=vUp.crossProduct(vTo); // set vRight value
    }

    public Camera setViewPlaneSize(double width, double height){
        return null;
    }
    public Camera setDistance(double distance){
        return null;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        return null;
    }

    public Camera setVpDistance(int i) {
        return null;
    }

    public Camera setVpSize(int i, int i1) {
        return null;
    }


}
