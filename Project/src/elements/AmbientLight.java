package elements;

import primitives.Color;

public class AmbientLight {
    public Color intensity;

    public AmbientLight(Color color,double k){
        this.intensity=new Color(color);
        this.intensity.scale(k);
    }
}
