package org.avalon.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.avalon.Tokens;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;

public class BotHandler extends TelegramLongPollingBot {

    private final Logger LOGGER = LogManager.getLogger(BotHandler.class);

    @Override
    public String getBotUsername() {
        return Tokens.botUsername;
    }

    @Override
    public String getBotToken() {
        return Tokens.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){

            Message message = update.getMessage(); //variabili usate frequentemente
            Long chatId = message.getChatId();
            String text = message.getText().toLowerCase();

            LOGGER.info("Message received from @" + message.getFrom().getUserName() + " text: " + text);

            if (!StartHandler.allowedUser(message)){ //true se è in compagnia, false se non lo è
                sendMessage(new SendMessage(chatId.toString(),
                        "HEYYY, non sei autorizzato a scrivere messaggi qui, ecco il tuo user id: " +
                                update.getMessage().getFrom().getId()));

            } else if (NewEventHandler.eventCreation.containsKey(chatId)){
                sendMessage(new NewEventHandler().eventUpdate(message));

            } else if (text.contains("/new")) { //switch per gestire i comandi
                sendMessage(new NewEventHandler().newEvent(message));

            } else if (text.contains("/start")){
                sendMessage(new StartHandler().start(message));

            } else if (text.contains("ping")){
                sendMessage(new SendMessage(chatId.toString(), "pong"));
            }
        }
    }

    protected void sendMessage(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
