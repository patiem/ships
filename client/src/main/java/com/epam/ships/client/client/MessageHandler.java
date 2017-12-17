package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    private static final Target logger = new SharedLogger(Client.class);

    private Button eventButton = null;
    private Map<String, EventTrigger> triggers = new HashMap<>();

    public MessageHandler() {
        triggers.put("opponentConnected", new OpponentConnectedTrigger());
    }

    public void setCurrentEventButton(Button eventButton) {
        this.eventButton = eventButton;
    }

    public void handle(Message message) throws IllegalStateException{
        if(eventButton == null) {
            throw new IllegalStateException("there is no object on which there can be fire event on");
        }

        String header = message.getHeader();
        triggers.get(header).fire(eventButton, message.getStatement());
    }
}
