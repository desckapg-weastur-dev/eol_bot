package com.desckapg.eolbot.bot.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import lombok.Getter;

public abstract class AbstractCommand {

    @Getter
    private final String name;

    protected AbstractCommand(String name) {
        this.name = name;
    }

    public abstract void handle(Update update);

}
