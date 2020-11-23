package space.harbour.java.hw10;

import com.mongodb.BasicDBObject;
import com.mongodb.Function;
import java.io.FileNotFoundException;
import org.bson.Document;

public class Movie {
    public static void main(String[] args) throws FileNotFoundException {
        MongoExecutor executor = new MongoExecutor();

        // Insert
        Document shawshank = new Document("title", "The Shawshank Redemption")
                .append("year", "1997")
                .append("genre", "drama");
        executor.execStoreMovie(shawshank);

        // Find
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append("genre", "drama");
        Function<Document, String> handler = document -> String.valueOf(document);

        executor.execFindOne("java", "movies", searchQuery, handler);
    }
}
