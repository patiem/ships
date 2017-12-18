package com.epam.ships.client.client;

import javafx.scene.control.Button;

/**
 * @author Magda
 * @since 2017-12-17
 */

interface EventTrigger {
    void fire(Button button, String messageStatement);
}
