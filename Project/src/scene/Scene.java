package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import primitives.Serializable;
import static primitives.Util.BASE_FILES_PATH;

public class Scene implements Serializable {
    public String name = "Untitled";
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 0);
    public Geometries geometries = new Geometries();

    //for saving files
    private static final String FOLDER_PATH =BASE_FILES_PATH + "/json";

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
     *
     * @param fileName the json file name
     * @return scene with the scene data from the json file
     * @throws ParseException using JSONParser
     * @throws IOException    using JSONParser
     */
    public static Scene LoadJSON(String fileName) throws ParseException, IOException {
        Object obj = new JSONParser().parse(new FileReader(FOLDER_PATH+"/"+fileName));
        JSONObject json = (JSONObject) obj;
        Scene ret = new Scene("untitled");
        ret.load(json);
        return ret;
    }

    /**
     * save the scene data to json file
     *
     * @param path the file path
     * @throws IOException using FileWriter
     */
    public void SaveJSON(String path) throws IOException {
        FileWriter file = new FileWriter(FOLDER_PATH + '/' + path);
        file.write(this.toJSON().toJSONString());
        file.flush();
        file.close();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject ret = new JSONObject();
        ret.put("type", "scene");
        ret.put("name", this.name);
        ret.put("geometries", geometries.toJSON());
        ret.put("ambient-light", ambientLight.toJSON());
        ret.put("background", background.toJSON());
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.background.load((JSONObject) json.get("background"));
        this.geometries.load((JSONObject) json.get("geometries"));
        this.ambientLight.load((JSONObject) json.get("ambient-light"));
        this.name = (String) json.get("name");
        return this;
    }
}
