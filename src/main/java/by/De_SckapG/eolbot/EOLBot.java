package by.De_SckapG.eolbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import io.github.cdimascio.dotenv.Dotenv;

public class EOLBot {

    public static void main(String[] args) {
        setupBot();
    }

    private static void setupBot() {
        TelegramBot bot = new TelegramBot(Dotenv.load().get("eol_bot_token"));
        bot.setUpdatesListener(updates -> {
            updates.stream().filter(update -> update.message() != null).forEach(update -> {
                Message message = update.message();
                SendMessage request = new SendMessage(message.chat().id(), message.text());
                bot.execute(request);
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

}
