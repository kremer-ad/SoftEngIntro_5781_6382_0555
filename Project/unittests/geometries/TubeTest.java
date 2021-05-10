package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Tube class
 *
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
        Tube tb = new Tube(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1d)), 1d);

        assertEquals("Bad normal to plane", new Vector(0, 1d, 0), tb.getNormal(new Point3D(1d, 1d, 3d)));

        // =============== Boundary Values Tests ==================
        // TC10: Point is facing the head of the tube's ray
        Point3D facingPnt = new Point3D(1d, 1d, 0);
        assertEquals("Bad normal to plane", new Vector(0, 1d, 0), tb.getNormal(new Point3D(1d, 1d, 0)));

    }

    @Test
    public void testFindIntersection() {
        Tube tb = new Tube(new Ray(new Point3D(5d, 0, 0), new Vector(0, 1d, 0)), 1);
        Ray ray = new Ray(new Point3D(0, 2d, 0), new Vector(1d, 0, 0));

        assertEquals("basic intersection test",ray.getPoint(4),tb.findIntersections(ray).get(0));
        assertEquals("basic intersection test",ray.getPoint(6),tb.findIntersections(ray).get(1));
        //TC02: The ray parallel to the tube and outside of him
        tb=new Tube(new Ray(new Point3D(3,0,0),new Vector(0,1,0)),20);
        ray = new Ray(new Point3D(0,5,-25),new Vector(0,1,0));
        assertNull("the ray is parallel to the tube",tb.findIntersections(ray));
        //TC03: The ray parallel to the tube and in  inside him
        tb=new Tube(new Ray(new Point3D(3,0,0),new Vector(0,1,0)),20);
        ray = new Ray(new Point3D(0,5,0),new Vector(0,1,0));
        assertNull("the ray is parallel to the tube",tb.findIntersections(ray));
    }
}