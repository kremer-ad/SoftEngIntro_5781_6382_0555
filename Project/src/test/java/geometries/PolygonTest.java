/**
 *
 */

package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Testing Polygons
 *
 * @author Dan
 *
 */

public class PolygonTest {
    /**
     * Test method for
     * {@link Polygon#Polygon(Point3D...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {
        }

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {
        }

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {
        }

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {
        }

    }

    /**
     * Test method for {@link Polygon#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to triangle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    /**
     * Test method for {@link Polygon#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // TC01: Simple check - there is an intersection point
        //basic square
        Polygon pl = new Polygon(
                new Point3D(1d, 4d, 0),
                new Point3D(4d, 4d, 0),
                new Point3D(4d, 0, 0),
                new Point3D(1d, 0, 0)
        );

        Point3D pnt = new Point3D(0, 0, 4d);
        // TC01: Inside triangle
        List<Point3D> result = pl.findIntersections(new Ray(pnt, new Vector(2d, 2d, -4d)));
        assertEquals("Inside polygon", List.of(new Point3D(2d, 2d, 0)), result);
        // TC02: Outside against edge

        result = pl.findIntersections(new Ray(pnt, new Vector(5d, 2d, -4d)));
        assertNull("Outside against edge", result);

        // TC03: Outside against vertex
        result = pl.findIntersections(new Ray(pnt, new Vector(7d, 7d, -4d)));
        assertNull("Outside against vertex", result);

        // =============== Boundary Values Tests ==================

        // TC04: On edge
        result = pl.findIntersections(new Ray(pnt, new Vector(4d, 2, -4d)));
        assertNull("On edge", result);

        // TC05: In vertex
        result = pl.findIntersections(new Ray(pnt, new Vector(4d, 4d, -4d)));
        assertNull("In vertex", result);

        // TC06: On edge's continuation
        result = pl.findIntersections(new Ray(pnt, new Vector(7d, 8d, -4d)));
        assertNull("On edge's continuation", result);
    }
       /* Ray intersectRay=new Ray(new Point3D(0.5,0.5,-1),new Vector(0,0,1));
        List<Point3D> intersections = pl.findIntersections(intersectRay);
        Point3D intersectionPoint = intersections==null?null:intersections.get(0);
        assertEquals("The basic check fail, not getting the intersection point",new Point3D(0.5,0.5,0),intersectionPoint);

        //TC02: There is no intersection point with the polygon but there is an intersection point with the plane
        Ray notInteractRay = new Ray(new Point3D(30,52,-1),new Vector(0,0,1));
        assertNull("Not returning null when the ray is intersecting with the plane and not intersecting with the polygon", pl.findIntersections(notInteractRay));

        //TC03: The ray intersecting with the edge of the polygon
        Ray edgeIntersectRay=new Ray(new Point3D(0,0.5,-1),new Vector(0,0,1));
        assertNull("Not returning null when the ray in intersecting with the edge of the polygon", pl.findIntersections(edgeIntersectRay));
    }*/
}