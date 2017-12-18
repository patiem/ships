package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

class MessageHandler {

    private Button eventButton = null;
    private final Map<String, EventTrigger> triggers;

    MessageHandler() {
        this.triggers = new HashMap<>();
        this.triggers.put("opponentConnected", new OpponentConnectedTrigger());
        this.triggers.put("shot", new OpponentShotTrigger());

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
        triggers.get(header).fire(eventButton, message.getStatement());
    }
}
