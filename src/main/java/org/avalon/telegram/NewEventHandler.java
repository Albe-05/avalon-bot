package org.avalon.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NewEventHandler {

    private static final Logger LOGGER = LogManager.getLogger(NewEventHandler.class);

    protected static HashMap<Long, List<String>> eventCreation = new HashMap<>();
    // list[0] titolo, [1] descrizione, [2] luogo definito tra una serie di luoghi già definiti da me
    // per contenere gli utenti che stanno creando un evento
    // in quanto per il nome dell'evento e gli altri parametri non sono comandi

    private static final List<String> locations = Arrays.asList("Caserma Fincato", "Da un altra parte");

    public SendMessage newEvent(Message message){
        eventCreation.put(message.getChatId(), new ArrayList<>());
        return new SendMessage(message.getChatId().toString(), "Inviami il nome dell'evento...");
    }

    public SendMessage eventUpdate(Message message){

        try{
            List<String> eventInfo = eventCreation.get(message.getChatId());

            if (eventInfo.size() == 0){
                eventCreation.get(message.getChatId()).add(0, message.getText());
                return new SendMessage(message.getChatId().toString(), "Inviami la descrizione dell'evento...");

            } else if (eventInfo.size() == 1){
                eventCreation.get(message.getChatId()).add(1, message.getText());

                SendMessage sendMessage = new SendMessage(message.getChatId().toString(),
                        "Scegli il luogo dell'evento da quelli disponibili");

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); //crea oggetto tastiera

                List<KeyboardRow> keyboard = new ArrayList<>(); //crea la lista delle righe della tastiera

                KeyboardRow row1 = new KeyboardRow(); //crea una riga e la riempie con tutte le colonne
                row1.add(locations.get(0));

                KeyboardRow row2 = new KeyboardRow();
                row2.add(locations.get(1));

                keyboard.add(row1); //mette tutti i luoghi su più linee, se ce ne sono tanti diventa impossible
                keyboard.add(row2);

                replyKeyboardMarkup.setKeyboard(keyboard);

                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                return sendMessage;
            } else if (eventInfo.size() == 2){
                eventCreation.get(message.getChatId()).add(2, message.getText());
                ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(Boolean.TRUE);
                SendMessage sendMessage = new SendMessage(message.getChatId().toString(),
                        "Perfetto evento in creazione, eccone riassunto:\nTitolo: " + eventInfo.get(0) +
                                "\nDescrizione: " + eventInfo.get(1) +
                                "\nLuogo: " + eventCreation.get(message.getChatId()).get(2));
                sendMessage.setReplyMarkup(replyKeyboardRemove);

                //avvia il Thread per la creazione dell'evento
                List<String> event = eventCreation.remove(message.getChatId());
                EventCreation eventCreation = new EventCreation(event, message.getChatId());
                Thread thread = new Thread(eventCreation);
                thread.start();
                LOGGER.info("Thread per la creazione dell'evento avviato");

                return sendMessage;
            }
            return new SendMessage(message.getChatId().toString(), "Damnnnn: qualcosa è andato storto");
        } catch (IndexOutOfBoundsException e){
            LOGGER.error(e.getMessage());
            return new SendMessage(message.getChatId().toString(), "Damnnnn: qualcosa è andato storto");
        }
    }
}
