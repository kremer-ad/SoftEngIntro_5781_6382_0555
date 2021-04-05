package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Tube class
 * @author Yosef & Aaron
 */
public class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point3D)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tb = new Tube(new Ray(new Point3D(1,0,0),new Vector(0,0,1d)), 1d);

        assertEquals("Bad normal to plane", new Vector(0,1d,0), tb.getNormal(new Point3D(1d, 1d, 3d)));

        // =============== Boundary Values Tests ==================
        // TC10: Point is facing the head of the tube's ray
        Point3D facingPnt = new Point3D(1d, 1d, 0);
        assertEquals("Bad normal to plane", new Vector(0,1d,0), tb.getNormal(new Point3D(1d, 1d, 0)));

    }
}