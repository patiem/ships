package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentWithdrawEvent;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class ConnectionEndTrigger implements EventTrigger {

    private static final Target logger = new SharedLogger(Client.class);

    @Override
    public void fire(Button button, String messageStatement) {
        Platform.runLater(() -> button.fireEvent(new OpponentWithdrawEvent()));
        logger.info("WIN because 2 client leave");

    }
}
