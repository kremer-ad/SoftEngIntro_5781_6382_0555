package renderer;

import elements.Camera;
import elements.lights.AmbientLight;
import elements.lights.DirectionalLight;
import elements.lights.PointLight;
import geometries.Geometry;
import geometries.Polygon;
import geometries.Pyramid;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
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
        pyramid1.move(new Vector(0, -30, 0));
        pyramid2.move(new Vector(0, -30, 0));
        return scene;
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
