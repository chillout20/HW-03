package space.harbour.java.hw11;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.io.PrintWriter;
import org.bson.Document;

public class MongoExecutorChat {
    MongoClient client;
    MongoDatabase mongoDatabase;

    public MongoExecutorChat() {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://chillout20:chillout20Password"
                + "@cluster0.2bao5.mongodb.net/<dbname>?retryWrites=true&w=majority");
        // You can access the Atlas MongoDB with the following credential:
        // https://cloud.mongodb.com/v2/5fbcaf4e3471a17a9e16c769#clusters
        // Email: duc.havithailand@gmail.com
        // Password: Harbour.Space2020
        client = new MongoClient(uri);
        mongoDatabase = client.getDatabase("chatHistory");
    }

    public void execRetrieveChatHistory(PrintWriter out) {
        MongoDatabase mongoDatabase = client.getDatabase("chatHistory");
        MongoCollection<Document> mongoCollection =
                mongoDatabase.getCollection("chatHistoryCollection");
        FindIterable<Document> result = mongoCollection
                .find(new Document())
                .sort(new BasicDBObject("timestamp", 1));

        MongoCursor<Document> iterator = result.iterator();

        while (iterator.hasNext()) {
            Document message = iterator.next();
            out.println(message.getString("name")
                    + " said: " + message.getString("message"));
        }
    }

    public void execStoreMessage(Document document) {
        MongoCollection<Document> mongoCollection =
                mongoDatabase.getCollection("chatHistoryCollection");
        mongoCollection.insertOne(document);
    }
}
