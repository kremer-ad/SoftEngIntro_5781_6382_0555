package geometries;

import org.json.simple.JSONObject;
import primitives.Point3D;
import primitives.Serializable;
import primitives.Vector;

public class Triangle extends Polygon{
    /**
     * triangle constructor
     * @param q0 triangle vertex
     * @param q1 triangle vertex
     * @param q2 triangle vertex
     */
    public Triangle(Point3D q0, Point3D q1, Point3D q2) {
        super(q0,q1,q2);
    }

    @Override
    public String toString() {
        return "Triangle{" + super.toString()+ '}';
    }

    @Override
    public JSONObject  toJSON(){
        JSONObject ret=super.toJSON();
        ret.put("type","triangle");
        return ret;
    }

}
