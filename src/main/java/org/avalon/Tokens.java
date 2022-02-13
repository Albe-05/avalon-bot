package org.avalon;

import java.util.Scanner;

public class Tokens {

    public static final String botUsername = "avalon_rover_bot";

    //chiavi private
    private static String botToken = null;

    public static String getBotToken() {
        if (botToken == null) {
            System.out.print("Inserisci il token del Bot Telegram: ");
            botToken = new Scanner(System.in).nextLine();
        }
        return botToken;
    }
}
