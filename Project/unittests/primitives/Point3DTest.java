package primitives;

import org.junit.Test;

import static org.junit.Assert.*;

public class Point3DTest {

    @Test
    public void add() {
        Point3D p1 = new Point3D(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point addition
        assertTrue("Point + Vector does not work correctly",
                Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))));
    }

    @Test
    public void subtract() {
        Point3D p1 = new Point3D(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point submission
        assertTrue("Point - Vector does not work correctly",
                new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)));
    }
}