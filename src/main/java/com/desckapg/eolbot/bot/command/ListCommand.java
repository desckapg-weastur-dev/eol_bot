package com.desckapg.eolbot.bot.command;

import com.desckapg.eolbot.EOLBot;
import com.desckapg.eolbot.bot.Localization;
import com.desckapg.eolbot.database.UserService;
import com.desckapg.eolbot.database.pojo.User;
import com.desckapg.eolbot.external.EOLService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.Locale;
import java.util.stream.Collectors;

public class ListCommand extends AbstractCommand {

    private final EOLService eolService;
    private final UserService userService;

    public ListCommand(TelegramBot bot, String name) {
        super(bot, name);
        this.eolService = EOLBot.getEolService();
        this.userService = EOLBot.getUserService();
    }

    @Override
    public void handle(Update update) {
        eolService.getAllTechnologies().thenAccept(technologies -> {

            String[] args = update.message().text().split(" ");

            if (args.length > 1) {
                String pattern = args[1].toLowerCase(Locale.US);
                technologies = technologies.stream()
                        .filter(s -> s.contains(pattern))
                        .collect(Collectors.toList());
            }

            Long senderID = update.message().chat().id();

            User user = userService.getUser(senderID);

            if (technologies.isEmpty()) {
                bot.execute(new SendMessage(senderID,
                        Localization.getMessage(user.getLanguage(), "command.list.noTechnologies"))
                );
                return;
            }

            StringBuilder answerBuilder = new StringBuilder();
            for (String technology : technologies) {
                answerBuilder.append(technology);
                answerBuilder.append('\n');
            }
            bot.execute(new SendMessage(update.message().chat().id(),
                    answerBuilder.toString())
            );
        });
    }
}
