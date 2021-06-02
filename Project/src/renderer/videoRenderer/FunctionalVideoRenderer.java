package renderer.videoRenderer;

import elements.Camera;
import renderer.ImageWriter;
import renderer.Render;
import renderer.rayTracers.RayTracerBasic;
import scene.Scene;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionalVideoRenderer {
    /**
     * the video scene
     */
    private Scene scene;
    /**
     * the camera of the video
     */
    private Camera camera;
    /**
     * the video length in frames
     */
    private int length;
    /**
     * the video fps
     */
    private int fps = 25;
    /**
     * the file name
     */
    private String name;
    /**
     * the video algorithm
     */
    private Function<VideoData, VideoData> algorithm;
    /**
     * run each cycle of the loop after setting the video data
     */
    private Consumer<VideoData> trackFunction;
    /**
     * the image width
     */
    private int nX;
    /**
     * the image height
     */
    private int nY;

    public FunctionalVideoRenderer setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public FunctionalVideoRenderer setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public FunctionalVideoRenderer setLength(int length) {
        this.length = length;
        return this;
    }

    public FunctionalVideoRenderer setName(String name) {
        this.name = name;
        return this;
    }

    public FunctionalVideoRenderer(int nX, int nY) {
        this.nX = nX;
        this.nY = nY;
    }

    public FunctionalVideoRenderer setFps(int fps) {
        this.fps = fps;
        return this;
    }

    public FunctionalVideoRenderer setAlgorithm(Function<VideoData, VideoData> algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public FunctionalVideoRenderer setTrackFunction(Consumer<VideoData> trackFunction) {
        this.trackFunction = trackFunction;
        return this;
    }

    public void renderVideo() throws IOException {
        if (this.camera == null) {
            throw new MissingResourceException("there is missing element for running this method", "Camera", "camera");
        }
        if (this.scene == null) {
            throw new MissingResourceException("there is missing element for running this method", "Scene", "scene");
        }
        if (this.algorithm == null) {
            throw new MissingResourceException("there is missing element for running this method", "Algorithm", "algorithm");
        }

        VideoData data = new VideoData()
                .setCamera(this.camera)
                .setScene(this.scene);
        BufferedImage[] videoImages = new BufferedImage[length];
        Render renderer = new Render()
                .setCamera(this.camera)
                .setRayTracer(new RayTracerBasic(this.scene))
                .setImageWriter(new ImageWriter("temp", this.nX, this.nY));
        for (int i = 0; i < this.length; i++) {
            data.setFrameNumber(i);
            data = algorithm.apply(data);
            renderer.renderImage();
            videoImages[i] = deepCopy(renderer.getBufferedImage());
            if (trackFunction != null) {
                trackFunction.accept(data);
            }
        }
        VideoWriter.generateVideo(this.name, videoImages, this.fps);
    }


    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
