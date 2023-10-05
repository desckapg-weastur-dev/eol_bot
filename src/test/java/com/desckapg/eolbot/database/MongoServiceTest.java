package com.desckapg.eolbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@NoArgsConstructor
class MongoServiceTest {

    private static MongoService mongoService;

    @BeforeAll
    static void loadDotenvAndMongo() {
        Dotenv dotenv = Dotenv.load();
        mongoService = new MongoService(
                dotenv.get("MONGO_USER"),
                dotenv.get("MONGO_PWD"),
                dotenv.get("MONGO_HOST"),
                dotenv.get("MONGO_PORT"),
                dotenv.get("MONGO_DB")
        );
    }

    @Test
    void testDBConnection() {
        Assertions.assertNotNull(mongoService.getMongoClient(), "Couldn't connect to MongoDB!");
    }

    @Test
    void testDBExisting() {
        Assertions.assertNotNull(mongoService.getDatabase(), "Database not exist!");
    }

    @AfterAll
    static void closeConnection() {
        mongoService.getMongoClient().close();
    }

}
