package elements;

import primitives.Color;

/**
 * light abstract class
 */
abstract class Light {

    /**
     * Refill lighting intensity
     */
    private Color intensity;

    protected Light(Color intensity){
        this.intensity=intensity;
    }

    public Color getIntensity() {
        return intensity;
    }

    public Light setIntensity(Color intensity) {
        this.intensity = intensity;
        return this;
    }
}
