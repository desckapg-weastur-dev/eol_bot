package com.desckapg.eolbot.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LocalizationTest {

    @BeforeAll
    static void loadLangFiles() {
        Localization.load();
    }

    @Test
    void testGettingCustomMessage() {
        Assertions.assertNotEquals(
                "Bot only work with text messages!",
                Localization.getMessage("en", "common.wrong_message_type")
        );
    }

}
