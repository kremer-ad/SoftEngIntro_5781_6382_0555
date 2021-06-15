import elements.Camera;
import elements.lights.AmbientLight;
import elements.lights.DirectionalLight;
import geometries.*;
import primitives.*;
import renderer.imageRenderer.FastRenderer;
import renderer.ImageWriter;
import renderer.rayTracers.RayTracerBasic;
import scene.Scene;

import static java.lang.Integer.parseInt;

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {

    /**
     * Main program to tests initial functionality of the 1st stage
     * 
     * @param args irrelevant here
     */
    public static void main(String[] args) {
        System.out.println("start");
        wheel(parseInt(args[0]));
        System.out.println("finish");
    }

    private static void wheel(int threads) {
        FastRenderer render = new FastRenderer();
        render.setMultithreading(threads);

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
                //.setCollider(new BoxCollider(Point3D.ZERO, new Point3D(Double.POSITIVE_INFINITY, .001, Double.POSITIVE_INFINITY)))
                .move(new Vector(0, -50, 0));
        Wheel wheel = new Wheel(100D, 30D, 80D, 12)
                .setMaterial(regularMat)
                .setEmission(woodColor)
                .setCollider(new BoxCollider(Point3D.ZERO, new Point3D(210D, 210D, 40)).move(new Vector(0, 105, 0)));
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

        Geometries sideShapes = new Geometries();
        sideShapes.add(hat, pyramid, sphere);
        sideShapes.setCollider(new BoxCollider(new Point3D(-50, 100, -50), new Point3D(400, 250, 300)));
        scene.geometries.add(floor, wheel, sideShapes);
        scene.lights.add(new DirectionalLight(new Color(500, 500, 500), new Vector(1, -1, -1)));
        // scene.lights.add(new PointLight(new Color(255, 255, 255), wheel.getPosition()).setKL(0.00001).setKQ(0.00001));
        ImageWriter imageWriter = new ImageWriter("wheel colliders", 1000, 1000);
        render.setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();

        //  Vector angleSpeed = new Vector(0D, 0D, -14.4D);

    }
}
