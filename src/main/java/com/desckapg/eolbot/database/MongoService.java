package com.desckapg.eolbot.database;

import com.desckapg.eolbot.database.pojo.BotUser;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.LinkedList;
import java.util.List;

public class MongoService {

    private final Logger logger;

    private CodecRegistry codecRegistry;

    @Getter
    private MongoClient mongoClient;

    @Getter
    private MongoDatabase database;

    @Getter
    private MongoCollection<BotUser> usersCollection;

    public MongoService(String user, String pwd, String host, String port, String dbName) {
        this.logger = LogManager.getLogger("MONGO");
        configurePOJO();
        attemptConnection(user, pwd, host, port, dbName);
        createCollections();
    }

    private void attemptConnection(String user, String pwd,
                                   String host, String port, String dbName) {
        String connectionURI =
                String.format("mongodb://%s:%s@%s:%s/?authSource=admin",
                        user, pwd, host, port
                );
        logger.info("Connecting to MongoDB");
        this.mongoClient = MongoClients.create(connectionURI);
        this.database = mongoClient
                .getDatabase(dbName)
                .withCodecRegistry(codecRegistry);
        logger.info("Connect to MongoDB was established");
    }

    /**
     * users -> subscription (inner document)
     * notifies -> TODO
     */

    private void createCollections() {
        List<String> collections = new LinkedList<>();
        database.listCollectionNames().forEach(collections::add);

        if (!collections.contains("users")) {
            logger.info("Creating 'users' collection");
            database.createCollection("users");
        }
        if (!collections.contains("notifies")) {
            logger.info("Creating 'notifies' collection");
            database.createCollection("notifies");
        }
        this.usersCollection = this.database.getCollection("users", BotUser.class);
    }

    private void configurePOJO() {
        ClassModel<BotUser> userModel = ClassModel.builder(BotUser.class)
                .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                .build();
        CodecProvider codecProvider = PojoCodecProvider.builder()
                .register(userModel)
                .build();
        this.codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(codecProvider)
        );
    }


}
