package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentShotEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class OpponentShotTrigger implements EventTrigger {
    @Override
    public void fire(Button button, String messageStatement) {
        Platform.runLater(() -> button.fireEvent(new OpponentShotEvent(Integer.valueOf(messageStatement))));
        System.out.println("shot index: " + messageStatement);
    }
}
