package space.harbour.java.hw10;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.mongodb.BasicDBObject;
import com.mongodb.Function;
import hw4.Movies;
import org.bson.Document;

public class Movie {
    public static void main(String[] args) throws FileNotFoundException {
        MongoExecutor executor = new MongoExecutor();

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("src/main/java/" +
                "space/harbour/java/hw7/BladeRunner.json"));

        // Insert
        Document bladeRunner = gson.fromJson(reader, Document.class);
        executor.execStoreMovie(bladeRunner);

        // Find
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append("Title","Blade Runner");
        Function<Document, Movies> function = new readResult();

        executor.execFindOne("java", "movies", searchQuery, function);
    }

    public Function<Document, Movies> readResult(Document document) throws FileNotFoundException {
        Gson gson = new Gson();
        String stringResult = gson.toJson(document);
        Movies result = new Movies();
        result.fillDetails(stringResult);
        return result;
    }
}
