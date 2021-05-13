package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Serializable;
import primitives.Vector;

/**
 * interface for light with source
 */
public interface LightSource extends Serializable {
    /**
     * get the intensity in specific point
     * @param p the point
     * @return the intensity at that point
     */
    Color getIntensity(Point3D p);

    /**
     * get the direction of the ray that will intersect with the given point
     * @param p the point of intersection
     * @return the direction of the given ray relative to the light source
     */
    Vector getL(Point3D p);
}
