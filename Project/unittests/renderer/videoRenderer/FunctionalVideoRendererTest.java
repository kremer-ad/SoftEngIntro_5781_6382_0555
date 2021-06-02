package renderer.videoRenderer;

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

import java.io.IOException;

public class FunctionalVideoRendererTest {

    @Test
    public void renderVideoTest() throws IOException {
        Scene scene = setScenePyramid();
        Camera camera = new Camera(new Point3D(0, 0, 3000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(1000, 1000) //
                .setDistance(6000);
        FunctionalVideoRenderer videoRenderer = new FunctionalVideoRenderer(500, 500);
        scene.geometries.move(new Vector(-500D, 0D, 0D));
        videoRenderer.setCamera(camera)
                .setScene(scene)
                .setFps(25)
                .setLength(100)
                .setName("functional video test")
                .setAlgorithm(data -> {
                    data.scene.geometries.move(new Vector(20D, 0D, 0D));
                    return data;
                })
                .setTrackFunction(data -> {
                    System.out.println("finish " + (data.frameNumber+1) + "/" + 100);
                })
                .renderVideo();
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


}