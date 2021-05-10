package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Serializable;
import primitives.Vector;

public abstract class Geometry implements Intersectable, Serializable {

    protected Color emmission = Color.BLACK;

    public Color getEmmission() {
        return emmission;
    }

    public Geometry setEmmission(Color emmission) {
        this.emmission = emmission;
        return this;
    }

    /**
     * gets geometries normal
     * @param pnt
     * @return geometries normal
     */
    public abstract Vector getNormal(Point3D pnt);

}
