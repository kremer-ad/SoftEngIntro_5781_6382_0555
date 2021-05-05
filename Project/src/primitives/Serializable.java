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
     * load the json object data into that object
     * @param json the json object to load
     */
    void  load(JSONObject json);

}
