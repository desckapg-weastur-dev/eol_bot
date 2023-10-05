package com.desckapg.eolbot.bot;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Localization {

    private static final List<String> LANGUAGES = List.of("en", "ru");

    private static final Map<String, ResourceBundle> LANG_RESOURCES = Maps.newHashMap();

    public static void load() {
        for (String langCode : LANGUAGES) {
            LANG_RESOURCES.put(langCode, ResourceBundle.getBundle("bot", new Locale(langCode)));
        }
    }

    public static List<String> getLanguages() {
        return LANGUAGES;
    }

    public static String getMessage(String lang, String key) {
        ResourceBundle langResource = LANG_RESOURCES.get(lang);
        if (!langResource.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Wrong localization key - %s", key));
        }
        return langResource.getString(key);
    }



}
