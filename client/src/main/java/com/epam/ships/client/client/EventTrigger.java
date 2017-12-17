package com.epam.ships.client.client;

import javafx.scene.control.Button;

public interface EventTrigger {
    void fire(Button button, String messageStatement);
}
