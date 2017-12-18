package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

class MessageHandler {

    private static final Target logger = new SharedLogger(Client.class);

    private Button eventButton = null;
    private final Map<String, EventTrigger> triggers;

    MessageHandler() {
        this.triggers = new HashMap<>();
        this.triggers.put("opponentConnected", new OpponentConnectedTrigger());
        this.triggers.put("shot", new OpponentShotTrigger());
        this.triggers.put("Connection", new ConnectionEndTigger());8

        //TODO: react to end!
    }

    void setCurrentEventButton(Button eventButton) {
        this.eventButton = eventButton;
    }

    void handle(Message message) throws IllegalStateException{
        if(eventButton == null) {
            throw new IllegalStateException("there is no object on which there can be fire event on");
        }

        String header = message.getHeader();
        if(!triggers.containsKey(header)) {
            logger.error("message header: " + header +" is unknown");
            return;
        }

        triggers.get(header).fire(eventButton, message.getStatement());
    }
}
