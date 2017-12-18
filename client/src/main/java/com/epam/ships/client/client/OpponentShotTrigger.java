package com.epam.ships.client.client;

import javafx.scene.control.Button;

public class OpponentShotTrigger implements EventTrigger {
    @Override
    public void fire(Button button, String messageStatement) {
        System.out.println("shot index: " + messageStatement);
    }
}
