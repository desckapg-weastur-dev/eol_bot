package com.desckapg.eolbot.bot.command;

import com.desckapg.eolbot.bot.Localization;
import com.desckapg.eolbot.util.StringUtil;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;
import java.util.Locale;

public class LangsCommand extends AbstractCommand {

    private final TelegramBot bot;

    public LangsCommand(TelegramBot bot, String name) {
        super(name);
        this.bot = bot;
    }


    @Override
    public void handle(Update update) {
        List<String> languages = Localization.getLanguages();
        StringBuilder answerBuilder = new StringBuilder();
        for (int i = 0; i < languages.size(); i++) {
            String lang = languages.get(i);
            Locale locale = new Locale(lang);
            answerBuilder.append(String
                    .format("%s (%s)", StringUtil.capitalize(locale
                            .getDisplayLanguage(locale), locale), lang)
            );
            if (i < languages.size() - 1) {
                answerBuilder.append('\n');
            }
        }
        bot.execute(new SendMessage(
                update.message().chat().id(),
                answerBuilder.toString())
        );
    }

}
