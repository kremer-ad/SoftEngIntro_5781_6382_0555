package elements;

import org.json.simple.JSONObject;
import primitives.Color;
import primitives.Serializable;

public class AmbientLight extends Light implements Serializable {


    // note that color.scale return a new object
    public AmbientLight(Color color, double k) {
        super(color.scale(k));
    }

    public AmbientLight() {
        super(new Color(Color.BLACK));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("intensity", this.getIntensity().toJSON());
        ret.put("type", "ambient-light");
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.getIntensity().load((JSONObject) json.get("intensity"));
        return this;
    }
}
