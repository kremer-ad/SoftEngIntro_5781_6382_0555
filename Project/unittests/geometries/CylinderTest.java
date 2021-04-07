package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Yosef & Aaron
 */
public class CylinderTest extends TubeTest {//we extends from tube so we can use the tests in the side of cylinder

    /**
     * Test method for {@link Cylinder#getNormal(Point3D)}.
     */
    @Test
    public void getNormal() {
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
}