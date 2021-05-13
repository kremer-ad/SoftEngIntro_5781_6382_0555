package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for light with source
 */
public interface LightSource {
    /**
     * get the intensity in specific point
     * @param p the point
     * @return the intensity at that point
     */
    Color getIntensity(Point3D p);
    Vector getL(Point3D p);
}
