package org.avalon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.avalon.telegram.BotHandler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Starting...");

        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new BotHandler());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
