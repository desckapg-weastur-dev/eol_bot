package com.desckapg.eolbot.bot.command;

import com.desckapg.eolbot.bot.Localization;
import com.desckapg.eolbot.external.EOLService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import lombok.Getter;

import java.util.logging.Logger;

public abstract class AbstractCommand {

    @Getter
    private final String name;

    protected final TelegramBot bot;

    protected AbstractCommand(TelegramBot bot, String name) {
        this.bot = bot;
        this.name = name;
    }

    public abstract void handle(Update update);

}
