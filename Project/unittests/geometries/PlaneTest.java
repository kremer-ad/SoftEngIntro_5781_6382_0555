package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plane", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point3D(1, 0, 0), new Vector(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============

        // **** Group: Ray neither orthogonal nor parallel
        // ****
        // ****

        // TC01: Ray intersects the plane (1 points)
        List<Point3D> result = plane.findIntersections(new Ray(new Point3D(0, 1d, 0),
                new Vector(2d, -1d, 0)));

        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses plane", List.of(new Point3D(2d, 0, 0)), result);

        // TC01: Ray does not intersects the plane (0 points)
        result = plane.findIntersections(new Ray(new Point3D(0, 1d, 0), new Vector(2d, 1d, 0)));
        assertNull("Ray does not crosses plane", result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // ****
        // ****

        // TC11: ray is included in the plane (0 points)
        result = plane.findIntersections(new Ray(new Point3D(3d, 0, 0), new Vector(2d, 0, 0)));
        assertNull("Ray is included in the plane", result);

        // TC12: ray is not included in the plane (0 points)
        result = plane.findIntersections(new Ray(new Point3D(0, 1d, 0), new Vector(2d, 0, 0)));
        assertNull("Ray is not included in the plane", result);

        // **** Group: Ray is orthogonal to the plane
        // ****
        // ****

        // TC21: ray is before the plane (1 points)
        result = plane.findIntersections(new Ray(new Point3D(2d, -1d, 0), new Vector(0, 2d, 0)));

        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray is orthogonal before plane", List.of(new Point3D(2d, 0, 0)), result);

        // TC22: ray is in plane (0 points)
        result = plane.findIntersections(new Ray(new Point3D(2d, 0, 0), new Vector(0, 2d, 0)));
        assertNull("Ray is orthogonal in plane", result);

        // TC23: ray is after the plane (0 points)
        result = plane.findIntersections(new Ray(new Point3D(2d, 1d, 0), new Vector(0, 2d, 0)));
        assertNull("Ray is orthogonal after plane", result);

        // TC30: Ray is neither orthogonal nor parallel to plane and begins at the plane (0 points)
        result = plane.findIntersections(new Ray(new Point3D(2d, 0, 0), new Vector(2d, -1d, 0)));
        assertNull("Ray is orthogonal neither orthogonal nor parallel in plane", result);

        // TC40: Ray is neither orthogonal nor parallel and begins in Q point (0 points)
        result = plane.findIntersections(new Ray(new Point3D(1d, 0, 0), new Vector(1d, 1d, 0)));
        assertNull("Ray begins at Q point", result);
    }
}
