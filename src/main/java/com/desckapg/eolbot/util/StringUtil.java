package com.desckapg.eolbot.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StringUtil {

    public static String capitalize(String word, Locale locale) {
        return word.substring(0, 1).toUpperCase(locale) + word.substring(1);
    }

    public static List<String> parseCommandArgs(String test, int argsCount) {
        return Arrays.stream(test.split(test))
                .limit(argsCount)
                .toList();
    }

}
