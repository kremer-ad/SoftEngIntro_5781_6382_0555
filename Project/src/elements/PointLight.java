package elements;

import org.json.simple.JSONObject;
import primitives.Color;
import primitives.Point3D;
import primitives.Serializable;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    protected Point3D position;
    private double kC;
    private double kL;
    private double kQ;

    /**
     * create point light with intensity and position
     *
     * @param intensity the light intensity
     * @param position  the light position
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        this.kC = 1D;
        this.kL = 0D;
        this.kQ = 0D;
    }

    @Override
    public Color getIntensity(Point3D p) {
        //the denominator of the model from the presentation
        double denominator = this.kC + kL * this.position.distance(p) + kQ * this.position.distanceSquared(p);
        return this.getIntensity().scale(1 / denominator);
    }

    @Override
    public Vector getL(Point3D p) {
        return this.position.subtract(p).normalize();
    }

    /**
     * Builder setters
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = super.toJSON();
        ret.put("position", this.position.toJSON());
        ret.put("kC", this.kC);
        ret.put("kL", this.kL);
        ret.put("kQ", this.kQ);
        ret.put("type", "PointLight");
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        super.load(json);
        this.position = (Point3D) this.position.load((JSONObject) json.get(position));//points are not overwritten by the load method due to their final variables
        this.kQ = (double) json.get("kQ");
        this.kC = (double) json.get("kC");
        this.kL = (double) json.get("kL");
        return this;
    }
}
