package elements.lights;

import org.json.simple.JSONObject;
import primitives.Color;
import primitives.Serializable;

/**
 * light abstract class
 */
abstract class Light implements Serializable {

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

    @Override
    public JSONObject toJSON() {
        JSONObject ret=new JSONObject();
        ret.put("type","Light");
        ret.put("intensity",this.intensity.toJSON());
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.intensity.load((JSONObject) json.get("intensity"));
        return this;
    }
}
