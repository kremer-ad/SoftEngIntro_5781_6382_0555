package primitives;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RayTest {

    @Test
    public void findClosestPoint() {
        //TC01:the closest point is in the middle of the list
        Ray testRay = new Ray(new Point3D(5, 2, 6), new Vector(7, 8, 6));
        Point3D[] middleCheckArr = {
                testRay.getPoint(5000),
                testRay.getPoint(380),
                testRay.getPoint(25),
                testRay.getPoint(48),
                testRay.getPoint(32),
        };
        assertEquals("The point is in the middle of the list", middleCheckArr[2], testRay.findClosestPoint(Arrays.asList(middleCheckArr)));


        //TC02: the list is empty
        assertNull("The list is empty", testRay.findClosestPoint(new ArrayList<Point3D>()));


        //TC03: the point is at the start of the list
        Point3D[] startCheckArr = {
                testRay.getPoint(15),
                testRay.getPoint(380),
                testRay.getPoint(25),
                testRay.getPoint(48),
                testRay.getPoint(32),
        };
        assertEquals("The point is in the start of the list", startCheckArr[0], testRay.findClosestPoint(Arrays.asList(startCheckArr)));


        //TC03: the point is at the start of the list
        Point3D[] endCheckArr = {
                testRay.getPoint(15),
                testRay.getPoint(380),
                testRay.getPoint(25),
                testRay.getPoint(48),
                testRay.getPoint(3),
        };
        assertEquals("The point is in the  end of the list", endCheckArr[4], testRay.findClosestPoint(Arrays.asList(endCheckArr)));
    }
}