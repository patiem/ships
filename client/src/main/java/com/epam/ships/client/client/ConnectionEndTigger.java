package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentWithdrawEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class ConnectionEndTigger implements EventTrigger {
    @Override
    public void fire(Button button, String messageStatement) {
        Platform.runLater(() -> button.fireEvent(new OpponentWithdrawEvent()));
        System.out.println("WIN because 2 client leave");

    }
}
