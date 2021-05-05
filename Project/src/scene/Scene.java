package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import  java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Scene {
    public String name = "Untitled";
    public Color background = Color.BLACK;
    public AmbientLight ambientLight=new AmbientLight(Color.BLACK,0);
    public Geometries geometries=new Geometries();

    public Scene(String name) {
        this.name = name;
    }
/* setters*/
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * load a scene from josn file
     * @param fileName the json file name
     * @return scene with the scene data from the json file
     * @throws ParseException
     * @throws IOException
     */
    public static Scene LoadJSON(String fileName) throws ParseException, IOException {
        Object obj=new JSONParser().parse(new FileReader(fileName));
        JSONObject jo = (JSONObject) obj;
        Scene ret=new Scene((String) jo.get("name"));
        //TODO:: finish that method and implement Serializable to all the geometries
        return ret;
    }
}
