package elements;

import org.json.simple.JSONObject;
import primitives.Color;
import primitives.Point3D;
import primitives.Serializable;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     * create directional light with intensity and direction
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity,Vector direction) {
        super(intensity);
        this.direction=direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return this.getIntensity();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret=super.toJSON();
        ret.put("direction",this.direction.toJSON());
        ret.put("type","DirectionalLight");
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        super.load(json);
        this.direction.load((JSONObject) json.get("direction"));
        return this;
    }

    @Override
    public Vector getL(Point3D p) {
        return this.direction;
    }
}
