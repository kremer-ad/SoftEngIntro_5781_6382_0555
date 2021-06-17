package renderer.videoRenderer;

import elements.Camera;
import elements.lights.AmbientLight;
import elements.lights.DirectionalLight;
import elements.lights.PointLight;
import elements.lights.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.imageRenderer.Render;
import renderer.rayTracers.RayTracerBasic;
import scene.Scene;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

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
        Geometry cylinder = new Cylinder(new Ray(Point3D.ZERO, new Vector(1, 0, 1)), 100D, 50D);
        cylinder.setEmission(new Color(java.awt.Color.RED)) //
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));
        scene.geometries.add(cylinder);
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

        Vector angleSpeed = new Vector(0D, 3, 0D);

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

    @Test
    public void rotatingWheelTest() throws IOException {
        Scene scene = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Color woodColor = new Color(153, 101, 21);
        Material material = new Material().setKD(.5D).setKS(.5D).setNShininess(100);
        double wheelHole = 80;
        Geometry outerRing = new Ring(new Ray(Point3D.ZERO, new Vector(0, 0, 1)), 100D, wheelHole, 50D);
        outerRing.setEmission(woodColor) //
                .setMaterial(material);
        Geometry[] sticks = new Cylinder[12];
        for (int i = 1; i <= sticks.length; i++) {
            Ray axisStick = new Ray(Point3D.ZERO, new Vector(1, 0, 0));
            axisStick.rotate(new Vector(0D, 0D, i * (360D / 12D)));
            sticks[i - 1] = new Cylinder(axisStick, 2, wheelHole).setEmission(woodColor).setMaterial(material);
        }
        Geometry innerBall = new Sphere(Point3D.ZERO, 20D).setEmission(woodColor).setMaterial(material);

        Geometry floor = new Plane(new Point3D(1, -30, 0), Point3D.ZERO, new Point3D(300, -30, 1)).setEmission(new Color(java.awt.Color.WHITE)).setMaterial(material);

        Geometries wheel = new Geometries();
        wheel.add(outerRing, innerBall);
        wheel.add(sticks);

        Wheel wheel2 = new Wheel(100,50,80,12);

        scene.geometries.add(wheel2/*, floor*/);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0).scale(.5D), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(500, 300, 0).reduce(2), new Point3D(100, 50, 50))//
                .setKL(0.00001).setKQ(0.000001));

        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);


        ImageWriter imageWriter = new ImageWriter("rotating wheel", 1000, 1000);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();


        Vector angleSpeed = new Vector(0D, 0D, -14.4D);
        BufferedImage[] images = new BufferedImage[25];
        for (int i = 0; i < images.length; i++) {
            scene.geometries.rotate(angleSpeed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("rotate wheel video test", images, 25);
    }

    @Test
    public void presentEx7Test() throws IOException {
        Scene scene = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.15));

        Camera camera = new Camera(new Point3D(300, 5000, -5000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
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

        wheel.move(new Vector(400, 50, -300));
        sphere.move(new Vector(-100, 50, 50));
        hat.move(new Vector(-100, 0, 50));



        scene.geometries.add(floor, wheel, hat, sphere, pyramid);
//        scene.lights.add(new SpotLight(new Color(400, 1020, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
//                .setKL(0.00001).setKQ(0.000005));
        scene.lights.add(new DirectionalLight(new Color(500, 500, 500), new Vector(1, -1, -1)));
        // scene.lights.add(new PointLight(new Color(255, 255, 255), wheel.getPosition()).setKL(0.00001).setKQ(0.00001));
        ImageWriter imageWriter = new ImageWriter("test", 1000, 1000);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();

        Vector angleSpeed = new Vector(0D, 0D, 10D);
        Vector movementSpeed = new Vector(-1250D/(25*4), 0, 0);
        BufferedImage[] images = new BufferedImage[200];
        for (int i = 0; i < 50; i++) {
            camera.lookAtTransform(camera.getPosition(), wheel.getPosition());
            wheel.rotate(angleSpeed);
            wheel.move(movementSpeed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        Point3D position = camera.getPosition();
        for (int i = 50; i < 200; i++) {
            camera.lookAtTransform(position.add(movementSpeed),wheel.getPosition());
            Point3D pnt = camera.calcPointOnSphere(i+90,(i-49)*0.1,wheel.getPosition());
            camera.lookAtTransform(pnt, wheel.getPosition());
            wheel.rotate(angleSpeed);
            wheel.move(movementSpeed);
            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }
        VideoWriter.generateVideo("shapes combination test", images, 25);
    }

    @Test
    public void TestTransformSphereRoute() throws IOException {
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
                new Cylinder(new Ray(new Point3D(70,0,0),new Vector(0,1,0)),20,50)
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

        BufferedImage[] images = new BufferedImage[70];

        double angleSpeed = 5D;
        Vector vec= camera.getVUp();
        double radius = camera.getPosition().distance(Point3D.ZERO);
        for (int i = 0; i < images.length; i++) {

            Point3D pnt = camera.calcPointOnSphere(i*angleSpeed,i*0.5, Point3D.ZERO);

            //camera.rotate(i*0.05);
            camera.lookAtTransform(new Point3D(2000,50,2000),new Point3D(i,50,i*4));

            render.renderImage();
            images[i] = deepCopy(render.getBufferedImage());
            System.out.println("finish " + (i + 1) + "/" + images.length);
        }

        VideoWriter.generateVideo("transform video test", images, 25);

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


}
