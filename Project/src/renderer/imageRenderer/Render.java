package renderer.imageRenderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import renderer.ImageWriter;
import renderer.rayTracers.RayTracerBase;

import java.awt.image.BufferedImage;
import java.util.MissingResourceException;

public class Render {
    protected ImageWriter writer;
    protected Camera camera;
    protected RayTracerBase rayTracer;

    public Render() {

    }

    /*getters*/
    public ImageWriter getWriter() {
        return writer;
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
        checkForVariables();

        for (int i = 0; i < writer.getNx(); i++) {
            for (int j = 0; j < writer.getNy(); j++) {
                Ray ray = camera.constructRayThroughPixel(writer.getNx(), writer.getNy(), i, j);
                Color colorToWrite = rayTracer.traceRay(ray);
                writer.writePixel(i, j, colorToWrite);
            }
        }

    }

    protected void checkForVariables() {
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

    public BufferedImage getBufferedImage() {
        //check that the writer exists
        if (this.writer == null) {
            throw new MissingResourceException("The writer value cant be null", "ImageWriter", "writer");
        }
        return writer.getImage();
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        writer = imageWriter;
        return this;
    }

    /**
     * render one pixel to the image writer
     * @param x the pixel number from the right
     * @param y the pixel number from the top
     */
    protected void renderPixel(int x,int y){
        Ray[] rays = camera.constructRaysThroughPixel(writer.getNx(), writer.getNy(), x, y, 1);
        writer.writePixel(x, y, rayTracer.traceRay(rays[0]));
    }
}
