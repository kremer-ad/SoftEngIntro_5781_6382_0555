package renderer;

import elements.Camera;
import elements.lights.AmbientLight;
import elements.lights.DirectionalLight;
import elements.lights.PointLight;
import elements.lights.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import scene.Scene;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import static primitives.Util.*;

public class VideoRendererTest {
    @Test
    public void movingDiamondTest() throws IOException {
        Scene scene = setScenePyramid();
        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);

        ImageWriter imageWriter = new ImageWriter("pre moving pyramid", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        Vector speed = new Vector(20, 0, 0);
        scene.geometries.move(new Vector(-500, 0, 0));
        BufferedImage[] images = new BufferedImage[50];
        for (int i = 0; i < images.length; i++) {
            scene.geometries.move(speed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("video test", images, 25);
    }

    @Test
    public void rotatingCameraTest() throws IOException {
        Scene scene = setScenePyramid();
        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);

        camera.rotate(90);

        ImageWriter imageWriter = new ImageWriter("pre moving pyramid", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        double angleSpeed = 1.8D;
        //scene.geometries.move(new Vector(-500, 0, 0));
        BufferedImage[] images = new BufferedImage[100];
        for (int i = 0; i < images.length; i++) {
            camera.rotate(angleSpeed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("rotate camera video test", images, 25);

    }

    @Test
    public void rotatingCylinderTest() throws IOException {
        Scene scene = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Geometry ring = new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 1)), 100D, 50D);
        ring.setEmission(new Color(java.awt.Color.RED)) //
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));
        scene.geometries.add(ring);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0).scale(.5D), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(500, 300, 0).reduce(2), new Point3D(100, 50, 50))//
                .setKL(0.00001).setKQ(0.000001));

        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);


        ImageWriter imageWriter = new ImageWriter("rotating cylinder", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        Vector angleSpeed = new Vector(0D, 10.8D, 0D);
        //scene.geometries.move(new Vector(-500, 0, 0));
        BufferedImage[] images = new BufferedImage[25];
        for (int i = 0; i < images.length; i++) {
            scene.geometries.rotate(angleSpeed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("rotate cylinder video test", images, 25);
    }

    @Test
    public void rotatingPyramidTest() throws IOException {
        Scene scene = setScenePyramid();

        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);

        scene.geometries.rotate(new Vector(90D, 0D, 0D));
        Vector angleSpeed = new Vector(0D, 10.8D, 0D);

        ImageWriter imageWriter = new ImageWriter("rotating pyramid", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));


        BufferedImage[] images = new BufferedImage[100];
        for (int i = 0; i < images.length; i++) {
            scene.geometries.rotate(angleSpeed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("rotate pyramid video test", images, 25);
    }

    @Test
    public void rotatingTriangleTest() throws IOException {
        Scene scene = new Scene("test scene")
                .setBackground(Color.BLACK)
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), .15D));
        Geometry floor = new Triangle(
                new Point3D(100, 100, 100),
                new Point3D(100, 30, -100),
                Point3D.ZERO
        ).setEmission(new Color(java.awt.Color.CYAN))
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));
        scene.geometries.add(floor);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0).scale(.5D), new Vector(0, 0, -1)));

        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);

        Vector angleSpeed = new Vector(0D, 10.8D, 0D);

        ImageWriter imageWriter = new ImageWriter("rotating pyramid", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));


        BufferedImage[] images = new BufferedImage[100];
        for (int i = 0; i < images.length; i++) {
            scene.geometries.rotate(angleSpeed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("rotate triangle video test", images, 25);

    }

    private Scene setScenePyramid() {
        Scene scene = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Geometry pyramid1 = new Pyramid(new Polygon(
                new Point3D(36.602540378444, 0, 136.60254037844),
                new Point3D(136.60254037844, 0, -36.602540378444),
                new Point3D(-36.602540378444, 0, -136.60254037844),
                new Point3D(-136.60254037844, 0, 36.602540378444)
        ), new Point3D(0, 200, 0))
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));
        Geometry pyramid2 = new Pyramid(new Polygon(
                new Point3D(36.602540378444, 0, 136.60254037844),
                new Point3D(136.60254037844, 0, -36.602540378444),
                new Point3D(-36.602540378444, 0, -136.60254037844),
                new Point3D(-136.60254037844, 0, 36.602540378444)
        ), new Point3D(0, -200, 0))
                .setEmission(new Color(java.awt.Color.RED)) //
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));
        scene.geometries.add(pyramid1, pyramid2);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0).scale(.5D), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(500, 300, 0).reduce(2), new Point3D(100, 50, 50))//
                .setKL(0.00001).setKQ(0.000001));
        // pyramid1.move(new Vector(0, -30, 0));
        // pyramid2.move(new Vector(0, -30, 0));
        return scene;
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    @Test
    public void TestTransformSphereRoute() throws IOException {
        Scene scene = new Scene("Test scene"); //
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(150, 0, 150), new Point3D(-150, 0, 150), new Point3D(75, 0, 75)) //
                        .setMaterial(new Material().setKS(0.8).setNShininess(60)), //
                new Triangle(new Point3D(150, 0, 150), new Point3D(-70, 0, 70), new Point3D(75, 0, 75)) //
                        .setMaterial(new Material().setKS(0.8).setNShininess(60)), //
                new Sphere(new Point3D(0, 60, 0), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKL(4E-4).setKQ(2E-5));

        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(1, 0, 0), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);


        ImageWriter imageWriter = new ImageWriter("transform scene", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        BufferedImage[] images = new BufferedImage[250];

        double angleSpeed = 1.8D;

        for (int i = 0; i < images.length; i++) {

            Point3D pnt = camera.calcPointOnSphere(75,i*2,Point3D.ZERO);
            camera.lookAtTransform(pnt,Point3D.ZERO);
            //camera.rotate(i);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("transform video test", images, 25);

    }
}
