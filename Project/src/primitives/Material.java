package primitives;

import org.json.simple.JSONObject;

/**
 * represent material of the object for reflection calculation
 */
public class Material implements Serializable {
    public double kS;
    public double kD;
    public int nShininess;

    public Material() {
        kS = 0;
        kD = 0;
        nShininess = 1;

    }

    public Material setKS(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setKD(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("kS", this.kS);
        ret.put("kD", this.kD);
        ret.put("type", "Material");
        ret.put("nShininess", this.nShininess);
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.kD = (double) json.get("kD");
        this.kS = (double) json.get("kS");
        this.nShininess = (int) json.get("nShininess");
        return this;
    }
}
