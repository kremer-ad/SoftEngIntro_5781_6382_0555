package elements;

import elements.lights.AmbientLight;
import elements.lights.SpotLight;
import geometries.Cylinder;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.imageRenderer.Render;
import renderer.rayTracers.RayTracerBasic;
import scene.Scene;

import static org.junit.Assert.assertEquals;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
public class CameraTest {

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
     */

    @Test
    public void testConstructRayThroughPixel() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);

        // ============ Equivalence Partitions Tests ==============
        // TC01: 3X3 Corner (0,0)
        assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-2, -2, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 0));

        // TC02: 4X4 Corner (0,0)
        assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-3, -3, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 0, 0));

        // TC03: 4X4 Side (0,1)
        assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-1, -3, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 0));

        // TC04: 4X4 Inside (1,1)
        assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-1, -1, 10)),
                camera.setViewPlaneSize(8, 8).constructRayThroughPixel(4, 4, 1, 1));

        // =============== Boundary Values Tests ==================
        // TC11: 3X3 Center (1,1)
        assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(0, 0, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 1));

        // TC12: 3X3 Center of Upper Side (0,1)
        assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(0, -2, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 1, 0));

        // TC13: 3X3 Center of Left Side (1,0)
        assertEquals("Bad ray", new Ray(Point3D.ZERO, new Vector(-2, 0, 10)),
                camera.setViewPlaneSize(6, 6).constructRayThroughPixel(3, 3, 0, 1));

        camera = new Camera(new Point3D(0, 0, 1), new Vector(0, 0, 1), new Vector(0, -1, 0)).setDistance(10);

    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        Scene scene = new Scene("Test scene"); //
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(150, 0, 150), new Point3D(-150, 0, 150), new Point3D(-150, 0, -150)) //
                        .setMaterial(new Material().setKS(0.8).setNShininess(60)), //
                new Triangle(new Point3D(150, 0, 150), new Point3D(150, 0, -150), new Point3D(-150, 0, -150)) //
                        .setMaterial(new Material().setKS(0.8).setNShininess(60)), //
                new Sphere(new Point3D(0, 30, 0), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)), //
                new Cylinder(new Ray(new Point3D(70, 0, 0), new Vector(0, 1, 0)), 20, 50)
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30))
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKL(4E-4).setKQ(2E-5));

        Camera camera = new Camera(new Point3D(0, 0, 2000), new Vector(1, 0, 0), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);


        ImageWriter imageWriter = new ImageWriter("transform scene", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        Point3D pnt = camera.calcPointOnSphere(0, 0, Point3D.ZERO);
        camera.lookAtTransform(pnt, Point3D.ZERO, new Vector(0, 1, 0));

        render.renderImage();
        render.writeToImage();
    }

}



