package classwork4;

import javax.json.JsonObject;

public interface Jsonable {

    public String toJsonString();
    public JsonObject toJsonObject();


    public JsonObject fromJsonString(String hiddenClass);
    //public String fromJsonObject();
}
