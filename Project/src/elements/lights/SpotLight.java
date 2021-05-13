package elements.lights;

import elements.LightSource;
import org.json.simple.JSONObject;
import primitives.Color;
import primitives.Point3D;
import primitives.Serializable;
import primitives.Vector;

public class SpotLight extends PointLight implements LightSource {

    private Vector direction;

    /**
     * create spot light with intensity position and direction
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0, getL(p).dotProduct(this.direction)));
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = super.toJSON();
        ret.put("direction", this.direction);
        ret.put("type", "SpotLight");
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        super.load(json);
        this.direction.load((JSONObject) json);
        return this;
    }
}
