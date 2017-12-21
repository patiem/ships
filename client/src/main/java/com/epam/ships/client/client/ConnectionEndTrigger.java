package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentWithdrawEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * @author Magda
 * @since 2017-12-18
 */
public class ConnectionEndTrigger implements EventTrigger {

    @Override
    public void fire(final Button button, final String messageStatement) {
        Platform.runLater(() -> button.fireEvent(new OpponentWithdrawEvent()));
    }
}
