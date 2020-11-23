package space.harbour.java.hw10;

import com.mongodb.BasicDBObject;
import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoExecutor {
    MongoClient client;
    MongoDatabase mongoDatabase;

    public MongoExecutor() {
        client = new MongoClient("localhost", 27017);
        mongoDatabase = client.getDatabase("java");
    }

    public <T> T execFindOne(String database, String collection,
                             BasicDBObject searchQuery, Function<Document, T> handler) {
        MongoDatabase mongoDatabase = client.getDatabase(database);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection);
        FindIterable<Document> result = mongoCollection.find(searchQuery);
        return handler.apply(result.first());

    }

    public void execStoreMovie(Document document) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("movies");
        mongoCollection.insertOne(document);
    }
}
