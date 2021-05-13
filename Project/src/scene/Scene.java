package scene;

import elements.*;
import geometries.Geometries;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import primitives.Color;
import primitives.Point3D;
import primitives.Serializable;
import primitives.Vector;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.BASE_FILES_PATH;

public class Scene implements Serializable {
    public String name = "Untitled";
    public Color background = new Color(Color.BLACK);
    public AmbientLight ambientLight = new AmbientLight();
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<LightSource>();

    //for saving files
    private static final String FOLDER_PATH = BASE_FILES_PATH + "/json";

    public Scene(String name) {
        this.name = name;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
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
     * load a scene from json file
     *
     * @param fileName the json file name
     * @return scene with the scene data from the json file
     * @throws ParseException using JSONParser
     * @throws IOException    using JSONParser
     */
    public static Scene LoadJSON(String fileName) throws ParseException, IOException {
        Object obj = new JSONParser().parse(new FileReader(FOLDER_PATH + "/" + fileName));
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
        ret.put("type", "Scene");
        ret.put("name", this.name);
        ret.put("geometries", geometries.toJSON());
        ret.put("ambient-light", ambientLight.toJSON());
        ret.put("background", background.toJSON());
        JSONArray lights = new JSONArray();
        for (var light : lights) {
            lights.add(((Serializable) light).toJSON());
        }
        ret.put("lights", lights);
        return ret;
    }

    @Override
    public Serializable load(JSONObject json) {
        this.background.load((JSONObject) json.get("background"));
        this.geometries.load((JSONObject) json.get("geometries"));
        this.ambientLight.load((JSONObject) json.get("ambient-light"));
        this.name = (String) json.get("name");
        this.loadAllLights((JSONArray) json.get("lights"));
        return this;
    }

    /**
     * load the lights data from the given json array
     *
     * @param json array that contains all the lights data
     */
    private void loadAllLights(JSONArray json) {
        for (int i = 0; i < json.size(); i++) {
            String type = ((JSONObject) json.get(i)).get("type").toString();
            switch (type) {
                case "DirectionalLight":
                    DirectionalLight toAddDirectional = new DirectionalLight(Color.BLACK, new Vector(1D, 1D, 1D));
                    toAddDirectional.load((JSONObject) json.get(i));
                    lights.add(toAddDirectional);
                    break;
                case "SpotLight":
                    SpotLight toAddSpot = new SpotLight(Color.BLACK, Point3D.ZERO, new Vector(1D, 1D, 1D));
                    toAddSpot.load((JSONObject) json.get(i));
                    lights.add(toAddSpot);
                    break;
                case "PointLight":
                    PointLight toAddPoint = new PointLight(Color.BLACK, Point3D.ZERO);
                    toAddPoint.load((JSONObject) json.get(i));
                    lights.add(toAddPoint);
                    break;
            }
        }
    }
}
