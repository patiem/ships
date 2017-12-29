package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentConnectedEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * @author Magda
 * @since 2017-12-17
 */

class OpponentConnectedTrigger implements EventTrigger {

  @Override
  public void fire(final Button button, final String messageStatement) {
    Platform.runLater(() -> button.fireEvent(new OpponentConnectedEvent()));
  }
}
