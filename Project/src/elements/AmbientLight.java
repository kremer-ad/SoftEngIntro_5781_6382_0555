package elements;

import org.json.simple.JSONObject;
import primitives.Color;
import primitives.Serializable;

import javax.sql.rowset.serial.SerialJavaObject;

public class AmbientLight implements Serializable {
    /**
     * Refill lighting intensity
     */
    public Color intensity;

    public Color getIntensity() {
        return intensity;
    }

    public AmbientLight(Color color, double k) {
        this.intensity = new Color(color);
        this.intensity.scale(k);
    }

    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("intensity", intensity.toJSON());
        ret.put("type", "ambient-light");
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.intensity.load((JSONObject) json.get("intensity"));
        return this;
    }
}
