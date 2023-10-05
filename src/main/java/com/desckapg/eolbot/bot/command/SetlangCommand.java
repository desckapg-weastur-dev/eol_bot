package com.desckapg.eolbot.bot.command;

import com.desckapg.eolbot.EOLBot;
import com.desckapg.eolbot.bot.Localization;
import com.desckapg.eolbot.database.UserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SetlangCommand extends AbstractCommand {

    private final TelegramBot bot;

    private final Logger logger;

    private final UserService userService;

    public SetlangCommand(Logger logger, TelegramBot bot, String name) {
        super(name);
        this.bot = bot;
        this.logger = logger;
        this.userService = EOLBot.getUserService();
    }


    @Override
    public void execute(Update update, List<String> args) {
        Long senderID = update.message().chat().id();
        userService.getUserAsync(senderID).thenAccept(user -> {
            if (args.isEmpty()) {
                bot.execute(new SendMessage(senderID,
                        Localization.getMessage(user.getLanguage(), "command.setlang.usage"))
                );
                return;
            }
            String langCode = args.get(1);
            if (!Localization.getLanguages().contains(langCode)) {
                bot.execute(new SendMessage(senderID,
                        Localization.getMessage(user.getLanguage(), "command.setlang.unknown_lang"))
                );
                return;
            }
            user.setLanguage(langCode);
            bot.execute(new SendMessage(senderID,
                    Localization.getMessage(user.getLanguage(), "command.setlang.success"))
            );
        }).handle((result, ex) -> {
            if (ex != null) {
                logger.error("Handled exception on executing", ex);
            }
            return result;
        });
    }
}
