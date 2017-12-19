package com.epam.ships.client.client;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.scene.control.Button;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Magda
 * @since 2017-12-16
 */

class MessageHandler {

    private static final Target logger = new SharedLogger(Client.class);

    private Button eventButton = null;
    private final Map<String, EventTrigger> triggers;

    @Getter
    private boolean endConnectionTriggered;

    MessageHandler() {
        endConnectionTriggered = false;
        this.triggers = new HashMap<>();
        this.triggers.put("opponentConnected", new OpponentConnectedTrigger());
        this.triggers.put("shot", new OpponentShotTrigger());
        this.triggers.put("Connection", new ConnectionEndTrigger());
        this.triggers.put("yourTurn", new TurnTrigger());
    }

    void setCurrentEventButton(Button eventButton) {
        this.eventButton = eventButton;
    }

    void handle(Message message) {
        if(eventButton == null) {
            throw new IllegalStateException("there is no object on which there can be fire event on");
        }

        String header = message.getHeader();

        if(!triggers.containsKey(header)) {
            logger.error("message header: " + header +" is unknown");
            return;
        }
        checkIfEndWillBeTriggered(message);
        triggers.get(header).fire(eventButton, message.getStatement());
    }

    private void checkIfEndWillBeTriggered(Message message) {
        if(message.getStatus().equalsIgnoreCase("end")) {
               endConnectionTriggered = true;
        }
    }
}
