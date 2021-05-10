package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for geometries.Cylinder class
 * @author Yosef & Aaron
 */
public class CylinderTest extends TubeTest {//we extends from tube so we can use the tests in the side of cylinder

    /**
     * Test method for {@link Cylinder#getNormal(Point3D)}.
     */
    @Test
    public void testGetNormal() {
        Cylinder c=new Cylinder(new Ray(Point3D.ZERO,new Vector(0,1,0)),1,1);
        //TC01: check the Tube getNormal for דםצק extreme cases there
        super.getNormal();

        //TC02: the point on the side of the cylinder
        assertEquals("getNormal() bad normal on the side of the cylinder",c.getNormal(new Point3D(0,0.5,1)),new Vector(0,0,1));

        //TC03: the point on the top base of teh cylinder
        assertEquals("getNormal() bad normal on the top base of the cylinder",c.getNormal(new Point3D(0.1,1,0.1)),new Vector(0,1,0));

        //TC04: the point on the bottom base of the cylinder
        assertEquals("getNormal() bad normal on the bottom base of the cylinder",c.getNormal(new Point3D(0.1,0,0.1)),new Vector(0,-1,0));
    }

    @Test
    public void findIntersections() {
        //TC01: all work
        Ray ray=new Ray(new Point3D(0,2,0),new Vector(1,0,0));
        Cylinder cylinder=new Cylinder(new Ray(new Point3D(5,0,0),new Vector(0,1,0)),1,3);
        assertEquals("the ray intersecting the cylinder",ray.getPoint(4),cylinder.findIntersections(ray).get(0));
        assertEquals("the ray intersecting the cylinder",ray.getPoint(6),cylinder.findIntersections(ray).get(1));

        //TC02: the ray intersecting tha bases of the cylinder
        ray=new Ray(new Point3D(5,8,0),new Vector(0,-1,0));
        assertEquals("the ray intersecting with the planes of the cylinder",ray.getPoint(5),cylinder.findIntersections(ray).get(0));
        assertEquals("the ray intersecting with the planes of the cylinder",ray.getPoint(8),cylinder.findIntersections(ray).get(1));

        //TC03: the ray moving above the cylinder but not intersecting it
        ray=new Ray(new Point3D(0,5,0),new Vector(3,1,0));
        assertNull("the ray not intersecting with the cylinder but movingf above it",cylinder.findIntersections(ray));

        //TC04: the ray intersecting the cylinder in it base and at is side
        ray=new Ray(new Point3D(4,-1,0),new Vector(1,1,0));
        assertEquals("the ray intersecting the base and the side of the cylinder - plane intersection",new Point3D(5,0,0),cylinder.findIntersections(ray).get(1));
        assertEquals("the ray intersecting the base and the side of the cylinder - side intersection",new Point3D(6,1,0),cylinder.findIntersections(ray).get(0));

    }
}