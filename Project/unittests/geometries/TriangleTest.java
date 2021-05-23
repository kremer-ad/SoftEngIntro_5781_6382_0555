package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Triangle class
 *
 * @author Yosef & Aaron
 */
public class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point3D)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tri = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plane", new Vector(sqrt3, sqrt3, sqrt3), tri.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Triangle tl = new Triangle(
                new Point3D(1d, 0, 0),
                new Point3D(2d, 2d, 0),
                new Point3D(3d, 0, 0)
        );
        Point3D pnt = new Point3D(0, 0, 3d);
        // TC01: Inside triangle
        List<Point3D> result = tl.findIntersections(new Ray(pnt, new Vector(2d, 1d, -3d)));
        assertEquals("Inside triangle", List.of(new Point3D(2, 1, 0)), result);
        // TC02: Outside against edge

        result = tl.findIntersections(new Ray(pnt, new Vector(1d, 1d, -3d)));
        assertNull("Outside against edge", result);

        // TC03: Outside against vertex
        result = tl.findIntersections(new Ray(pnt, new Vector(2d, 3d, -3d)));
        assertNull("Outside against vertex", result);

        // =============== Boundary Values Tests ==================

        // TC04: On edge
        result = tl.findIntersections(new Ray(pnt, new Vector(2d, 0, -3d)));
        assertNull("On edge", result);

        // TC05: In vertex
        result = tl.findIntersections(new Ray(pnt, new Vector(1d, 0, -3d)));
        assertNull("In vertex", result);

        // TC06: On edge's continuation
        result = tl.findIntersections(new Ray(pnt, new Vector(3d, 4d, -3d)));
        assertNull("On edge's continuation", result);
    }

    @Test
    public void someTest(){
        Triangle t=new Triangle(
          new Point3D(4,-4,4),
          new Point3D(-3,3,5),
          new Point3D(5,2,5)
        );
        Ray r=new Ray(new Point3D(0,0,0),new Vector(0.9,1.9,4.9));
        System.out.println(t.findIntersections(r).size());
    }
}