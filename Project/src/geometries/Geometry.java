package geometries;

import org.json.simple.JSONObject;
import primitives.*;

public abstract class Geometry implements Intersectable, Serializable {

    protected Color emission = Color.BLACK;
    protected Material material = new Material();

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }



    public Material getMaterial() {
        return material;
    }

    /**
     * gets geometries normal
     * @param pnt
     * @return geometries normal
     */
    public abstract Vector getNormal(Point3D pnt);

    @Override
    public JSONObject toJSON() {
        JSONObject ret=new JSONObject();
        ret.put("type","Geometry");
        ret.put("material",material.toJSON());
        ret.put("emission",emission.toJSON());
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.emission.load((JSONObject) json.get("emission"));
        this.material.load((JSONObject) json.get("material"));
        return this;
    }
}
