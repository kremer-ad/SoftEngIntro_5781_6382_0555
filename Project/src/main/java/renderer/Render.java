package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

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
        /*
        //some parameters for scaling the image when the given image is not at the same size like the view plane of the camera
        int imageXScale = (int) (writer.getNx() / camera.getWidth());//the scale size at the x axis
        int imageYScale = (int) (writer.getNy() / camera.getHeight());//the scale size at the y axis
        int writerX = 0;//the actual pointer for the image x coordinate
        int writerY = 0;//the actual pointer for the image y coordinate
        //run through all the rays in the view plane of the camera or until reach the boundary of the writer
        for (int i = 0; i < camera.getWidth() && i < writer.getNx(); i++) {
            writerY = 0;//reset the y pointer so we will print new
            for (int j = 0; j < camera.getHeight() && j < writer.getNy(); j++) {
                Ray ray = camera.constructRayThroughPixel((int) Math.round(camera.getWidth()), (int) Math.round(camera.getHeight()), i, j);
                Color colorToWrite = rayTracer.traceRay(ray);
                for (int k = 0; k < imageXScale; k++) {
                    for (int o = 0; o < imageYScale; o++) {
                        writer.writePixel(writerX + k, writerY + o, colorToWrite);
                    }
                }
                writerY += imageYScale;
            }
            writerX += imageXScale;
        }
        */

        for (int i = 0; i < writer.getNx(); i++) {
            for (int j = 0; j < writer.getNy(); j++) {
                Ray ray = camera.constructRayThroughPixel(writer.getNx(), writer.getNy(), j, i);
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
