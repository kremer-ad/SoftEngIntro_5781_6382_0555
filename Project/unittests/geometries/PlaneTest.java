package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Plane class
 *
 * @author Yosef & Aaron
 */
public class PlaneTest {

    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================

        // TC01: Vertices on a same line
        try {
            new Plane(new Point3D(0, 0, 1),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a plane with vertices on a same line");
        } catch (IllegalArgumentException e) {
        }

        // TC02: Converging points
        try {
            new Plane(new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a plane with Converging points");
        } catch (IllegalArgumentException e) {
        }

    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point3D)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plane", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
}