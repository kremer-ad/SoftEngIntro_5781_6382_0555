package renderer;

import elements.Camera;
import elements.lights.AmbientLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.rayTracers.RayTracerBasic;
import scene.Scene;

import java.io.IOException;

public class AntialiasingRenderTest {

    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500);

    private Render render = new AntialiasingRenderer();

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() throws IOException {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right
        ImageWriter imageWriter = new ImageWriter("antialiasing render test", 1000, 1000);
        //
        render.setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }
}
