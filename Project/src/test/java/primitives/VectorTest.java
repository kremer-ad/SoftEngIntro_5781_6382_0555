package primitives;

import org.junit.Test;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Yosef & Aaron
 */

public class VectorTest {

    /**
     * Test method for {@link Vector#add(Vector)}.
     */
    @Test
    public void add() {
    }

    /**
     * Test method for {@link Vector#subtract(Vector)}.
     */
    @Test
    public void subtract() {
    }

    /**
     * Test method for {@link Vector#scale(double)}.
     */
    @Test
    public void scale() {
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}.
     */
    @Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that dot-product calculate is proper
        assertTrue("dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));
        assertEquals("dotProduct() wrong value", v1.dotProduct(v2),-28d,0.00001);
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}.
     */
    @Test
    public void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows("crossProduct() for parallel vectors does not throw an exception",
                IllegalArgumentException.class, () -> v1.crossProduct(v3));
        /*
        try {
            v1.crossProduct(v3);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
        */
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    public void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that squared length of vector is proper
        assertEquals("length() wrong result", new Vector(1, 2, 3).lengthSquared(), 14d, 0.00001);

    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    public void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length of vector is proper
        assertEquals("length() wrong result", new Vector(0, 3, 4).length(), 5d, 0.00001);

    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void normalize() {
        final int   ROUND_ACCURECY=7;
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        Point3D resultHead=new Point3D(1d/Math.sqrt(14d),Math.sqrt(2d/7d),3d/Math.sqrt(14d));



        // ============ Equivalence Partitions Tests ==============
        // TC01: test vector normalization
        assertEquals("normalize() function creates a new vector", vCopy,vCopyNormalize);
        assertEquals("normalize() result is not a unit vector", vCopyNormalize.length(),1d,0.00001);
        assertEquals("normalize() result direction is not in the source direction",resultHead,vCopyNormalize.getHead());

    }

    /**
     * Test method for {@link Vector#normalized()}.
     */
    @Test
    public void normalized() {
        Vector v = new Vector(1, 2, 3);
        Point3D resultHead=new Point3D(1d/Math.sqrt(14d),Math.sqrt(2d/7d),3d/Math.sqrt(14d));

        Vector u = v.normalized();//the result vector
        assertNotEquals("normalized() function does not create a new vector", u,v);
        assertEquals("normalize() result is not a unit vector", u.length(),1d,0.00001);
        assertEquals("normalized() result direction is not in the source direction",resultHead,u.getHead());
    }

    /**
     * Round number with given amount of digits after the dot
     * @param number the number to roynd
     * @param digits the amount of digits after the dot
     * @return
     */
    private double round(double number,int digits){
        return (double)Math.round(number * (double)Math.pow(10,digits)) / (double)Math.pow(10,digits);
    }

}