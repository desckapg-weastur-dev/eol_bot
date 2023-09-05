package com.desckapg.eolbot.util;

import java.util.Locale;

public class StringUtil {

    public static String capitalize(String word, Locale locale) {
        return word.substring(0, 1).toUpperCase(locale) + word.substring(1);
    }

}
