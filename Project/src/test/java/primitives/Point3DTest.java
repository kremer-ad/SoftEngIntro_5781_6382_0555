package primitives;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Point3DTest {

    @Test
    public void add() {
        Point3D p = new Point3D(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point addition
        assertEquals("add() Point + Vector does not work correctly",Point3D.ZERO,p.add(new Vector(-1, -2, -3)));
    }

    @Test
    public void subtract() {
        Point3D p = new Point3D(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: test point submission
        assertEquals("subtract() Point - Vector does not work correctly", new Vector(1, 1, 1),new Point3D(2, 3, 4).subtract(p));
        
    }
}