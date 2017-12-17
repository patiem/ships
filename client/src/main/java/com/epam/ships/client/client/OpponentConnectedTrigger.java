package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentConnectedEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class OpponentConnectedTrigger implements EventTrigger {

    @Override
    public void fire(Button button, String messageStatement) {
        Platform.runLater(() -> button.fireEvent(new OpponentConnectedEvent()));
    }
}
