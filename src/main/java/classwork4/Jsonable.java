package classwork4;

import javax.json.JsonObject;

public interface Jsonable {

    public String toJsonString();
    
    public JsonObject toJsonObject();

    public void  fromJson(String jsonString);
    //public String fromJsonObject();
}
