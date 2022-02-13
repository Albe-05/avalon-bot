package org.avalon.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class EventCreation implements Runnable {

    private final String titolo;
    private final String descrizione;
    private final String luogo;
    private final Long chatId;

    public EventCreation(List<String> eventInfo, Long chatId) {
        this.titolo = eventInfo.get(0);
        this.descrizione = eventInfo.get(1);
        this.luogo = eventInfo.get(2);
        this.chatId = chatId;
    }

    @Override
    public void run() {
        //TODO: metodo per creare l'evento su google calendar con parametri il titolo, la descrizione e il luogo
        //del luogo possiamo anche avere il link con google maps perché tanto sono fissi

        if (luogo.contains("Da un altra parte")){
            //metodo(titolo, descrizione)
        } else {
            //metodo(titolo, descrizione, luogo)
        }

        new BotHandler().sendMessage(new SendMessage(chatId.toString(), "Evento forse creato"));
    }
}
