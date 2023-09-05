package com.desckapg.eolbot;

import com.desckapg.eolbot.bot.BotService;
import com.desckapg.eolbot.bot.Localization;
import com.desckapg.eolbot.database.MongoService;
import com.desckapg.eolbot.database.UserService;
import com.desckapg.eolbot.external.EOLService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

public final class EOLBot {

    private static Dotenv dotenv;

    @Getter
    private static MongoService mongoService;

    @Getter
    private static EOLService eolService;

    @Getter
    private static UserService userService;

    @Getter
    private static BotService botService;

    public static void main(final String[] args) {
        dotenv = Dotenv.load();
        eolService = new EOLService();
        mongoService = new MongoService(
                dotenv.get("MONGO_USER"),
                dotenv.get("MONGO_PWD"),
                dotenv.get("MONGO_HOST"),
                dotenv.get("MONGO_PORT"),
                dotenv.get("MONGO_DB")
        );
        userService = new UserService();
        botService = new BotService(dotenv.get("EOL_BOT_TOKEN"));
    }


}
