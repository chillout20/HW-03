package classwork4;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.Serializable;

public class Person implements Jsonable {
    private String name;
    private Integer age;
    private String gender;
    private Boolean hasKids;
    private String phone;
    private String[] experience;

    Person(String name, int age, String gender, boolean kids, String phone, String[] experience) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hasKids = kids;
        this.phone = phone;
        this.experience = experience;
    }

    @Override
    public String toJsonString() {
        return toJsonObject().toString();
    }

    @Override
    public JsonObject toJsonObject() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (String s: experience) arrayBuilder.add(s);
        JsonArray jExperence = arrayBuilder.build();

        JsonObject json = Json.createObjectBuilder()
                .add("name", name)
                .add("age", age)
                .add("gender", gender)
                .add("has_kids", hasKids)
                .add("phone", phone)
                .add("experience", jExperence)
                .build();
        return json;
    }

    @Override
    public JsonObject fromJsonString(String hiddenClass)
    {
        return null;
    }

    public static void main(String[] args) {
        String[] e = {"Harbour.Space", "REMY Robotics"};
        Person p = new Person("Vasilii", 40, "Male", false, "123-456-789", e);
        System.out.println(p.toJsonString());
    }
}