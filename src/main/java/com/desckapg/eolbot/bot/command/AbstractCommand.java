package com.desckapg.eolbot.bot.command;

import com.pengrad.telegrambot.model.Update;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand {

    @Getter
    private final String name;

    protected AbstractCommand(String name) {
        this.name = name;
    }


    public void handle(Update update) {
        List<String> args = Arrays.stream(update.message().text().split(" "))
                .skip(1)
                .toList();
        execute(update, args);
    }

    public abstract void execute(Update update, List<String> args);

}
