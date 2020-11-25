package space.harbour.java.hw10;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import space.harbour.java.hw7.Movies;

public class MongoExecutorMovies {
    MongoClient client;
    MongoDatabase mongoDatabase;

    public MongoExecutorMovies() {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://chillout20:chillout20Password"
                        + "@cluster0.2bao5.mongodb.net/<dbname>?retryWrites=true&w=majority");
        // You can access the Atlas MongoDB with the following credential:
        // https://cloud.mongodb.com/v2/5fbcaf4e3471a17a9e16c769#clusters
        // Email: duc.havithailand@gmail.com
        // Password: Harbour.Space2020
        this.client = new MongoClient(uri);
        this.mongoDatabase = client.getDatabase("movies");
    }

    public <T> T execFindOne(String database, String collection,
                             BasicDBObject searchQuery, Function<Document, T> handler) {
        MongoDatabase mongoDatabase = client.getDatabase(database);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        FindIterable<Document> result = mongoCollection.find(searchQuery);
        return handler.apply(result.first());
    }

    public void execStoreMovie(Document document) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("moviesCollection");
        mongoCollection.insertOne(document);
    }

    public Map<Integer, Movies> execRetrieveAllMovies() throws FileNotFoundException {
        MongoCollection<Document> mongoCollection =
                mongoDatabase.getCollection("moviesCollection");
        FindIterable<Document> result = mongoCollection
                .find(new Document());

        MongoCursor<Document> iterator = result.iterator();

        int index = 0;
        Gson g = new Gson();
        Map<Integer, Movies> moviesMap = new HashMap<>();
        while (iterator.hasNext()) {
            Document movieDoc = iterator.next();
            String jsonString = movieDoc.toJson();
            Movies movie = g.fromJson(jsonString, Movies.class);
            moviesMap.put(index, movie);
            index += 1;
        }

        return moviesMap;
    }

    public void execDeleteMovie(Document criteria) {
        MongoCollection<Document> mongoCollection =
                mongoDatabase.getCollection("moviesCollection");
        mongoCollection.deleteOne(criteria);
    }
}
