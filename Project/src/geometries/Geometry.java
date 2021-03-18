package geometries;

import primitives.Point3D;
import primitives.Vector;

public interface Geometry {
    /**
     * gets geometries normal
     * @param pnt
     * @return geometries normal
     */
    public Vector getNormal(Point3D pnt);
}
