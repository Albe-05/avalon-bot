package org.avalon.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartHandler {
    public SendMessage start(Message message) {
        return new SendMessage(message.getChatId().toString(),
                "Benvenuto rover, usa /new per creare un nuovo evento per la compagnia Avalon");
    }

    public static boolean allowedUser(Message message){ //per verificare che l'utente sia un rover della compagnia Avalon
        message.getFrom().getId(); //TODO: controllare se è un rover
        return true;
    }
}
