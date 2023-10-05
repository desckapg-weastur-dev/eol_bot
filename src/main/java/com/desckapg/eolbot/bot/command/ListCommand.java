package com.desckapg.eolbot.bot.command;

import com.desckapg.eolbot.EOLBot;
import com.desckapg.eolbot.bot.Localization;
import com.desckapg.eolbot.database.UserService;
import com.desckapg.eolbot.database.pojo.BotUser;
import com.desckapg.eolbot.external.EOLService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ListCommand extends AbstractCommand {

    private final TelegramBot bot;

    private final EOLService eolService;
    private final UserService userService;

    public ListCommand(TelegramBot bot, String name) {
        super(name);
        this.bot = bot;
        this.eolService = EOLBot.getEolService();
        this.userService = EOLBot.getUserService();
    }

    @Override
    public void execute(Update update, List<String> args) {
        eolService.getAllTechnologies().thenAccept(technologies -> {

            if (!args.isEmpty()) {
                String pattern = args.get(1).toLowerCase(Locale.US);
                technologies = technologies.stream()
                        .filter(s -> s.contains(pattern))
                        .collect(Collectors.toList());
            }

            Long senderID = update.message().chat().id();

            BotUser user = userService.getUser(senderID);

            if (technologies.isEmpty()) {
                bot.execute(new SendMessage(senderID,
                        Localization.getMessage(user.getLanguage(), "command.list.noTechnologies"))
                );
                return;
            }

            StringBuilder answerBuilder = new StringBuilder();
            for (String technology : technologies) {
                answerBuilder.append(technology).append('\n');
            }
            bot.execute(new SendMessage(update.message().chat().id(),
                    answerBuilder.toString())
            );
        });
    }

}
