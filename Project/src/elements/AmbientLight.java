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

    // note that color.scale return a new object
    public AmbientLight(Color color, double k) {
        this.intensity = color.scale(k);
    }

    public AmbientLight() {
        this.intensity = new Color(Color.BLACK);
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
