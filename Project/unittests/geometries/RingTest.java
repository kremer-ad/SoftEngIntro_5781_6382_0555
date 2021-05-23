package geometries;

import junit.framework.TestCase;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class RingTest extends TestCase {

    public void testGetNormal() {
    }

    public void testFindIntersections() {
        //TC01: the ray intersecting the ring in 4 places
        Ray ray = new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 0));
        Ray rd1 = new Ray(new Point3D(5, 0, 0), new Vector(0, 1, 0));
        Ring r1 = new Ring(rd1, 3, 1, 8);
        List<Point3D> intersections = r1.findIntersections(ray);
        List<Point3D> expected = List.of(
                new Point3D(2, 1, 0),
                new Point3D(4, 1, 0),
                new Point3D(6, 1, 0),
                new Point3D(8, 1, 0)
        );
        assertEqualsList("there is missing intersection in the 4 points intersection test", expected, intersections);

        //TC02: the ray intersecting from outside in two points
        ray = new Ray(new Point3D(0D, 1D, -2D), new Vector(1D, 0, 1D));
        intersections = r1.findIntersections(ray);
        expected = (new Cylinder(rd1, 3, 8)).findIntersections(ray);
        assertEqualsList("the ray from outside the ring and intersecting in two points", expected, intersections);

        //TC03: the ray starting from inside the ring and intersecting in 3 points
        ray = new Ray(new Point3D(3, 1, 0), new Vector(1, 0, 0));
        intersections = r1.findIntersections(ray);
        expected = List.of(
                new Point3D(4, 1, 0),
                new Point3D(6, 1, 0),
                new Point3D(8, 1, 0)
        );
        assertEqualsList("the ray intersecting the ring in 3 points and starting from inside of the ring", expected, intersections);

        //TC04: the ray intersecting the ring in 2 point and starting from the hole of the ring
        ray = new Ray(new Point3D(5, 1, 0), new Vector(1, 0.2D, 0));
        intersections = r1.findIntersections(ray);
        expected = List.of(
                new Point3D(6, 1.2D, 0),
                new Point3D(8, 1.6D, 0)
        );
        assertEqualsList("the ray intersecting the ring in 2 points and starting at the  hole of the ring", expected, intersections);

        //TC05: the ray starting from inside the ring and intersecting at the edge of the ring
        ray = new Ray(new Point3D(7, 1, 0), new Vector(1, 0, 0));
        intersections = r1.findIntersections(ray);
        expected = List.of(new Point3D(8, 1, 0));
        assertEqualsList("the ray starts from inside the ring and intersecting at the edge of the ring", expected, intersections);

        //TC06: the ray starting from outside the ring, and moving throughout the hole without intersection
        ray = new Ray(new Point3D(5, -1, 0), new Vector(0, 1, 0));
        assertNull("the ray starting from outside the ring, and moving throughout the hole without intersection", r1.findIntersections(ray));

        //TC07: the ray starting from outside the ring and moving outside the hole and not intersecting the ring
        ray = new Ray(new Point3D(0, 0, 0), new Vector(-1D, 3, 7));
        assertNull("the ray starting from outside the ring and moving outside the hole and not intersecting the ring", r1.findIntersections(ray));

        //TC08: the ray starting from outside the ring and intersecting at 3 points in the edges and one point in the base
        ray = new Ray(new Point3D(0, 1, 0), new Vector(1, 1, 0));
        expected = List.of(
                new Point3D(2, 3, 0),
                new Point3D(4, 5, 0),
                new Point3D(6, 7, 0),
                new Point3D(7, 8, 0)
        );
        intersections = r1.findIntersections(ray);
        assertEqualsList("the ray starting from outside the ring and intersecting at 3 points in the edges and one point in the base", expected, intersections);

    }

    public void testFindGeoIntersections() {
    }

    /**
     * test if the lists similar
     *
     * @param msg      the assert message
     * @param expected the first list to compare
     * @param actual   the second list to compare
     */
    private void assertEqualsList(String msg, List expected, List actual) {
        assertTrue(msg, containsAll(expected, actual) && containsAll(actual, expected));
    }

    private boolean containsAll(List list1, List list2) {
        for (var v : list1) {
            if (!list2.contains(v)) {
                return false;
            }
        }
        return true;
    }
}