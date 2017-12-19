package com.epam.ships.client.client;

import javafx.scene.control.Button;

/**
 * @author Magda
 * @since 2017-12-17
 */

interface EventTrigger {
    void fire(final Button button, final String messageStatement);
}
