package com.desckapg.eolbot.bot;

import com.desckapg.eolbot.EOLBot;
import com.desckapg.eolbot.bot.command.AbstractCommand;
import com.desckapg.eolbot.bot.command.LangsCommand;
import com.desckapg.eolbot.bot.command.ListCommand;
import com.desckapg.eolbot.bot.command.SetlangCommand;
import com.desckapg.eolbot.database.UserService;
import com.desckapg.eolbot.external.EOLService;
import com.google.common.collect.ImmutableMap;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotService {

    private final Logger logger;

    @Getter
    private TelegramBot bot;

    private ImmutableMap<String, AbstractCommand> commands;

    public BotService(String botToken) {
        this.logger = LogManager.getLogger("BOT");
        loadLocalization();
        startBot(botToken);
        loadCommands();
    }

    private void loadLocalization() {
        Localization.load();
    }

    private void loadCommands() {
        commands = ImmutableMap.of(
                "/list", new ListCommand(bot, "list"),
                "/langs", new LangsCommand(bot, "langs"),
                "/setlang", new SetlangCommand(logger, bot, "setlang")
        );
    }

    private void startBot(String botToken) {
        bot = new TelegramBot(botToken);
        bot.setUpdatesListener(updates -> {
            updates.stream()
                    .filter(update -> update.message() != null)
                    .forEach(update -> {
                        String text = update.message().text();
                        Long senderID = update.message().chat().id();
                        if (text == null) {
                            bot.execute(new SendMessage(senderID,
                                    Localization.getMessage("en", "common.wrong_message_type"))
                            );
                            return;
                        }
                        if (text.startsWith("/")) {
                            String cmdName = text.split(" ")[0];
                            AbstractCommand cmd = commands.get(cmdName);
                            if (cmd == null) {
                                bot.execute(new SendMessage(senderID,
                                        Localization.getMessage("en", "command.not_exist"))
                                );
                            } else {
                                logger.info("Handled command {} from {}", cmdName, senderID);
                                cmd.handle(update);
                            }
                        }
                    });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


}
