package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentConnectedEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * @author Magda
 * @since 2017-12-17
 */

public class OpponentConnectedTrigger implements EventTrigger {

    @Override
    public void fire(Button button, String messageStatement) {
        Platform.runLater(() -> button.fireEvent(new OpponentConnectedEvent()));
    }
}
