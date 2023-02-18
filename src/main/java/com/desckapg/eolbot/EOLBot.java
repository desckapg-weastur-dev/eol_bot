package com.desckapg.eolbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import io.github.cdimascio.dotenv.Dotenv;

public final class EOLBot {

    private EOLBot() {
    }

    public static void main(final String[] args) {
        setupBot(Dotenv.load());
    }

    private static void setupBot(final Dotenv dotenv) {
        final TelegramBot bot = new TelegramBot(dotenv.get("eol_bot_token"));
        bot.setUpdatesListener(updates -> {
            updates.stream().filter(update -> update.message() != null)
                    .forEach(update -> {
                        final Message message = update.message();
                        final SendMessage request = new SendMessage(
                                message.chat().id(), message.text()
                        );
                        bot.execute(request);
                    });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

}
