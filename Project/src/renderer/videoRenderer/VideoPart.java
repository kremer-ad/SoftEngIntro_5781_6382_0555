package renderer.videoRenderer;

import elements.Camera;
import scene.Scene;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class VideoPart implements Callable<BufferedImage[]> {
    private Scene scene;
    private Camera camera;

    @Override
    public BufferedImage[] call() throws Exception {
        return new BufferedImage[0];
    }
}
