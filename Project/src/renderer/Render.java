package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

public class Render {
    private ImageWriter writer;
    private Scene scene;
    private Camera camera;
    private RayTracerBase rayTracer;

    public Render() {

    }

    /*getters*/
    public ImageWriter getWriter() {
        return writer;
    }

    public Scene getScene() {
        return scene;
    }

    public Camera getCamera() {
        return camera;
    }

    public RayTracerBase getRayTracer() {
        return rayTracer;
    }

    /*setters*/
    public Render setWriter(ImageWriter writer) {
        this.writer = writer;
        return this;
    }

    public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * render the image
     */
    public void renderImage() {
        //check that we have all the resources for rendering the image
        if (this.camera == null) {
            throw new MissingResourceException("The camera value cant be null", "Camera", "camera");
        }
        //if (this.scene == null) {
        //    throw new MissingResourceException("The scene value cant be null", "Scene", "scene");
        //}
        if (this.writer == null) {
            throw new MissingResourceException("The writer value cant be null", "ImageWriter", "writer");
        }
        if (this.rayTracer == null) {
            throw new MissingResourceException("The rayTracer value cant be null", "RayTracerBase", "rayTracer");
        }

        for (int i = 0; i < writer.getNx(); i++) {
            for (int j = 0; j < writer.getNy(); j++) {
                Ray ray = camera.constructRayThroughPixel(writer.getNx(), writer.getNy(), i, j);
                Color colorToWrite = rayTracer.traceRay(ray);
                writer.writePixel(i, j, colorToWrite);
            }
        }

    }

    /**
     * print a grid on the image
     *
     * @param interval the distance between two line in the greed
     * @param color    the color of the greed
     */
    public void printGrid(int interval, Color color) {
        //check that the writer exists
        if (this.writer == null) {
            throw new MissingResourceException("The writer value cant be null", "ImageWriter", "writer");
        }

        //print horizontal lines:
        for (int i = 0; i < this.writer.getNy(); i += interval) {
            for (int j = 0; j < writer.getNx(); j++) {
                this.writer.writePixel(j, i, color);
            }
        }

        //print vertical lines
        for (int i = 0; i < this.writer.getNx(); i += interval) {
            for (int j = 0; j < this.writer.getNy(); j++) {
                this.writer.writePixel(i, j, color);
            }
        }
    }

    /**
     * write the rendered data into an image
     */
    public void writeToImage() {
        //check that the writer exists
        if (this.writer == null) {
            throw new MissingResourceException("The writer value cant be null", "ImageWriter", "writer");
        }
        writer.writeToImage();
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        writer = imageWriter;
        return this;
    }
}
