package space.harbour.java.hw11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import org.bson.Document;

public class ChatHandler extends Thread {
    private final ChatServer server;
    private final Socket client;
    public String name;
    private PrintWriter out;


    public ChatHandler(ChatServer server, Socket client) {
        this.server = server;
        this.client = client;
    }

    public void run() {
        MongoExecutorChat executor = new MongoExecutorChat();
        executor.execRetrieveChatHistory();

        try {
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()))) {
            name = in.readLine();
            setName(name);

            while (true) {
                String str = in.readLine();
                if (str == null) {
                    break;
                }
                server.broadcast(getName() + " says: " + str);

                // Store message to database with timestamp
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Document docMessage = new Document("name", getName())
                        .append("message", str)
                        .append("timestamp", timestamp);
                executor.execStoreMessage(docMessage);

                if (str.trim().equals("BYE")) {
                    break;
                }
            }
            client.close();
            server.chatDisconnected(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }
}
