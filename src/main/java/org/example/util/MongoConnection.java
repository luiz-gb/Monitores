package org.example.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    private static final String URI = "mongodb://admin:admin@localhost:27017";
    private static final MongoClient mongoClient = MongoClients.create(URI);

    public static MongoDatabase getDatabase () {
        return mongoClient.getDatabase("monitoria");
    }
}
