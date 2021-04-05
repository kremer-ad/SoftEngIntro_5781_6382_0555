package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Yosef & Aaron
 */
public class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(new Point3D(0, 0, 0), 1d);

        assertEquals("Bad normal to plane", new Vector(1,0,0), sp.getNormal(new Point3D(1, 0, 0)));

    }
}