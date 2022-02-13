package org.avalon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Tokens {
    private final static Logger LOGGER = LogManager.getLogger(Tokens.class);

    public static final String botUsername = "avalon_rover_bot";

    //chiavi private
    private static String botToken = null;

    public static String getBotToken() {
        if (botToken == null) {
            LOGGER.info("Inserisci il token del Bot Telegram: ");
            botToken = new Scanner(System.in).nextLine();
        }
        return botToken;
    }
}
