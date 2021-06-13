package renderer;

import elements.Camera;
import elements.lights.AmbientLight;
import elements.lights.DirectionalLight;
import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
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

    @Test
    public void wheelTest() throws IOException {
            Scene scene = new Scene("Test scene") //
                    .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

            Camera camera = new Camera(new Point3D(0, 0, 5000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                    .setViewPlaneSize(800, 800)
                    .setDistance(5000); //
            Material reflectionMat = new Material()
                    .setKD(.5D)
                    .setKS(.5D)
                    .setNShininess(100)
                    .setKR(.6D);
            Material refractionMat = new Material()
                    .setKD(1D)
                    .setKS(1D)
                    .setNShininess(100)
                    .setKT(.98D);
            Material regularMat = new Material()
                    .setKD(1D)
                    .setKS(1D)
                    .setNShininess(100);
            Color woodColor = new Color(153, 101, 21);


            Intersectable floor = (Plane) new Plane(Point3D.ZERO, new Vector(0, 1, 0))
                    .setEmission(new Color(java.awt.Color.BLACK))
                    .setMaterial(reflectionMat)
                    .move(new Vector(0, -50, 0));
            Wheel wheel = new Wheel(100D, 30D, 80D, 12)
                    .setMaterial(regularMat)
                    .setEmission(woodColor);
            wheel.move(new Vector(200, 0, 0));
            Intersectable pyramid = new Pyramid(new Polygon(
                    new Point3D(36.602540378444, 0, 136.60254037844),
                    new Point3D(136.60254037844, 0, -36.602540378444),
                    new Point3D(-36.602540378444, 0, -136.60254037844),
                    new Point3D(-136.60254037844, 0, 36.602540378444)
            ), new Point3D(0, 200, 0))
                    .setMaterial(regularMat)
                    .setEmission(new Color(java.awt.Color.BLUE))
                    .move(new Vector(0, -50, 0));
            Intersectable sphere = new Sphere(Point3D.ZERO, 100)
                    .setEmission(new Color(java.awt.Color.GREEN))
                    .setMaterial(regularMat)
                    .move(new Vector(-100, 0, 0));
            Intersectable hat = new Pyramid(new Polygon(
                    new Point3D(36.602540378444, 0, 136.60254037844),
                    new Point3D(136.60254037844, 0, -36.602540378444),
                    new Point3D(-36.602540378444, 0, -136.60254037844),
                    new Point3D(-136.60254037844, 0, 36.602540378444)
            ), new Point3D(0, 200, 0)).setMaterial(refractionMat)
                    .setEmission(new Color(java.awt.Color.orange))
                    .move(new Vector(-100, 80, 0));


            //Vector wheelDist = new Vector(new Point3D(0, 4000, -4000)).scale(-1);
            camera.lookAtTransform(new Point3D(0, 4000, -4000), wheel.getPosition());

            wheel.move(new Vector(0, 50, -300));
            sphere.move(new Vector(-100, 50, 50));
            hat.move(new Vector(-100, 0, 50));


            scene.geometries.add(floor, wheel, hat, sphere, pyramid);
//        scene.lights.add(new SpotLight(new Color(400, 1020, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
//                .setKL(0.00001).setKQ(0.000005));
            scene.lights.add(new DirectionalLight(new Color(500, 500, 500), new Vector(1, -1, -1)));
            // scene.lights.add(new PointLight(new Color(255, 255, 255), wheel.getPosition()).setKL(0.00001).setKQ(0.00001));
            ImageWriter imageWriter = new ImageWriter("wheel", 1000, 1000);
            this.render.setImageWriter(imageWriter) //
                    .setCamera(camera) //
                    .setRayTracer(new RayTracerBasic(scene));
            render.renderImage();
            render.writeToImage();

            //  Vector angleSpeed = new Vector(0D, 0D, -14.4D);

        }
    }
