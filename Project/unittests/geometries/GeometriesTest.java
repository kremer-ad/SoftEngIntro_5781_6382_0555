package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Geometries class
 * @author Yosef & Aaron
 */

public class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Geometries geos = new Geometries(
                /*new Tube(new Ray(new Point3D(5d, 0, 0), new Vector(0, 1d, 0)), 1),*/
                new Sphere(new Point3D(2d, 2d, 0), 1d),
                new Triangle(new Point3D(-3d, 1d, 0), new Point3D(-3d, 4d, 1d), new Point3D(-3d, 4d, -1d)),
                new Polygon(new Point3D(5d, 4d, -1d), new Point3D(5d, 4d, 1d), new Point3D(5d, 1d, 1d),new Point3D(5d, 1d, 0)));

        //Point3D triP = new Point3D(-3d, 2d, 0);
        //Point3D sphP1 = new Point3D(1d, 2d, 0);
        //Point3D sphP2 = new Point3D(3d, 2d, 0);
        //Point3D tbP1 = new Point3D(4d, 2d, 0);
        //Point3D tbP2 = new Point3D(6d, 2d, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some of the shapes are intersects
        List<Point3D> result = geos.findIntersections(new Ray(new Point3D(0, 2d, 0),
                new Vector(1d, 0, 0)));
        assertEquals("Some of the shapes are intersects", 3, result.size());

        // =============== Boundary Values Tests ==================
        // TC02: All shapes are intersects
        result = geos.findIntersections(new Ray(new Point3D(-4d, 2d, 0),
                new Vector(1d, 0, 0)));
        assertEquals("All shapes are intersects", 4, result.size());

        // TC03: One shape only intersects
        result = geos.findIntersections(new Ray(new Point3D(-1d, 2d, 0),
                new Vector(-1d, 0, 0)));
        assertEquals("One shape only intersects", 1, result.size());

        // TC04: No shape is intersects
        result = geos.findIntersections(new Ray(new Point3D(8d, 2d, 0),
                new Vector(1d, 0, 0)));
        assertNull("No shape is intersects", result);

        // TC05: Collection is empty
        geos = new Geometries();
        result = geos.findIntersections(new Ray(new Point3D(-1d, 2d, 0),
                new Vector(-1d, 0, 0)));
        assertNull("Collection is empty", result);
    }
}