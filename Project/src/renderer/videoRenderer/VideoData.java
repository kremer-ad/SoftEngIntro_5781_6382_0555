package renderer.videoRenderer;

import elements.Camera;
import scene.Scene;

class VideoData {
    public Scene scene;
    public int frameNumber;
    public Camera camera;

    public VideoData(){}

    public VideoData(Scene scene, int frameNumber, Camera camera) {
        this.scene = scene;
        this.frameNumber = frameNumber;
        this.camera = camera;
    }

    public VideoData setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public VideoData setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
        return this;
    }

    public VideoData setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }
}
