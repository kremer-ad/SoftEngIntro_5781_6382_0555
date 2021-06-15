package elements;

import elements.lights.AmbientLight;
import elements.lights.DirectionalLight;
import elements.lights.PointLight;
import elements.lights.SpotLight;
import geometries.*;
import geometries.Pyramid;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.rayTracers.RayTracerBasic;
import renderer.imageRenderer.Render;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);
    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200) //
            .setDistance(1000);

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
    private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))//
                .setKL(0.00001).setKQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2)) //
                .setKL(0.00001).setKQ(0.00000001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereAll() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1).scale(-1)));
        scene1.lights.add(new PointLight(new Color(500, 300, 0).reduce(2), new Point3D(100, 50, 50))//
                .setKL(0.00001).setKQ(0.000001));
        scene1.lights.add(new SpotLight(new Color(500, 300, 0).reduce(2), new Point3D(-75, -75, 50), new Vector(1, 1, -2)) //
                .setKL(0.00001).setKQ(0.00000001));
        ImageWriter imageWriter = new ImageWriter("lightSphereAll", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(300)), //
                triangle2.setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(300)), //
                triangle2.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(300)));
        scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setKL(0.0005).setKQ(0.0005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(300)),
                triangle2.setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(300)));
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)) //
                .setKL(0.0001).setKQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void trianglesAll() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(300)), //
                triangle2.setMaterial(new Material().setKD(0.8).setKS(0.2).setNShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(100, 100, 100), new Vector(0, 0, -1)));
        scene2.lights.add(new SpotLight(new Color(250, 125, 125).scale(2D), new Point3D(-30, -10, -130), new Vector(-2, -2, -1)) //
                .setKL(0.0001).setKQ(0.000005));
        scene2.lights.add(new PointLight(new Color(250, 125, 125), new Point3D(30, 20, -130)) //
                .setKL(0.0005).setKQ(0.0005));
        ImageWriter imageWriter = new ImageWriter("lightTriangleAll", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void ringDirectionalTest() {
        Scene scene = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(500, 500) //
                .setDistance(3000);
        Geometry ring = new Ring(new Ray(new Point3D(0, 0, 0), new Vector(0.1, 0.2, 1)), 50D, 30D, 75D)
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setKD(0.5).setKS(0.5).setNShininess(100));
        scene.geometries.add(ring);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0).scale(.5D), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(500, 300, 0).reduce(2), new Point3D(100, 50, 50))//
                .setKL(0.00001).setKQ(0.000001));
        ring.move(new Vector(0, 5, 0));
        ImageWriter imageWriter = new ImageWriter("ringDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void pyramidLights() {
        Scene scene = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);
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
        scene.geometries.add(pyramid1/*, pyramid2*/);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0).scale(.5D), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(500, 300, 0).reduce(2), new Point3D(100, 50, 50))//
                .setKL(0.00001).setKQ(0.000001));
        pyramid1.move(new Vector(0, -30, 0));
        pyramid2.move(new Vector(0, -30, 0));
        ImageWriter imageWriter = new ImageWriter("pyramid lights", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void cylinderDirectionalTest(){
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

        cylinder.rotate(new Vector(0,60,0));


        ImageWriter imageWriter = new ImageWriter("cylinder", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();;

    }

}
