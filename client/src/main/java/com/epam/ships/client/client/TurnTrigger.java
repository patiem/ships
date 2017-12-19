package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.TurnChangeEvent;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.scene.control.Button;

public class TurnTrigger implements EventTrigger {

    private static final Target logger = new SharedLogger(Client.class);

    @Override
    public void fire(Button button, String messageStatement) {
        if(messageStatement.isEmpty()) {
            logger.info("I start");
            button.fireEvent(new TurnChangeEvent());
        }
    }
}
