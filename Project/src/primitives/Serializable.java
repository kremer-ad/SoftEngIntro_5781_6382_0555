package primitives;

import java.util.Map;
import org.json.simple.JSONObject;

public interface Serializable {
    /**
     * serialized the data to json object
     * @return the serialized json object
     */
    JSONObject toJSON();

    /**
     * load the json object data into that object if can,
     * if the object have finals it wont change the object and will return new object with the loaded data
     * @param json the json object to load
     * @return the loaded object
     */
    Serializable load(JSONObject json);
}
