package space.harbour.java.hw10;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.bson.Document;

public class Movie {

    public static void main(String[] args) throws FileNotFoundException {
        MongoExecutorMovies executor = new MongoExecutorMovies();

        Gson gson = new Gson();
        JsonReader readerBr = new JsonReader(new FileReader("src/main/java/"
                + "space/harbour/java/hw7/BladeRunner.json"));
        JsonReader readerMr = new JsonReader(new FileReader("src/main/java/"
                + "space/harbour/java/hw7/MysticRiver.json"));
        JsonReader readerSd = new JsonReader(new FileReader("src/main/java/"
                + "space/harbour/java/hw7/StayDown.json"));
        JsonReader readerTsr = new JsonReader(new FileReader("src/main/java/"
                + "space/harbour/java/hw7/TheShawshankRedemption.json"));

        // Insert
        Document bladeRunner = gson.fromJson(readerBr, Document.class);
        Document mysticRiver = gson.fromJson(readerMr, Document.class);
        Document stayDown = gson.fromJson(readerSd, Document.class);
        Document shawshank = gson.fromJson(readerTsr, Document.class);

        executor.execStoreMovie(bladeRunner);
        executor.execStoreMovie(mysticRiver);
        executor.execStoreMovie(stayDown);
        executor.execStoreMovie(shawshank);

        //// Find
        //BasicDBObject searchQuery = new BasicDBObject();
        //searchQuery.append("genre", "drama");
        //Function<Document, String> handler = document -> String.valueOf(document);
        //
        //executor.execFindOne("java", "movies", searchQuery, handler);
    }
}
