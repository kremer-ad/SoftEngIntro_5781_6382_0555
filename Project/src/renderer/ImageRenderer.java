package renderer;

import elements.Camera;
import primitives.Color;
import renderer.rayTracers.RayTracerBase;

import java.awt.image.BufferedImage;

public interface ImageRenderer {
    //simple getters
    Camera getCamera();

    RayTracerBase getRayTracer();

    BufferedImage getBufferedImage();

    //simple setters
    ImageRenderer setCamera(Camera camera);

    ImageRenderer setImageWriter(ImageWriter imageWriter);

    ImageRenderer setRayTracer(RayTracerBase rayTracer);

    void renderImage();

    void writeToImage();

    void printGrid(int interval, Color color);
}
