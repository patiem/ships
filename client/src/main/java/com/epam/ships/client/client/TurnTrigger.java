package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.TurnChangeEvent;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * @author Magda
 * @since 2017-12-18
 */

class TurnTrigger implements EventTrigger {

  private static final Target logger = new SharedLogger(Client.class);

  @Override
  public void fire(final Button button, final String messageStatement) {
    if (messageStatement.isEmpty()) {
      logger.info("I start");
      Platform.runLater(() -> button.fireEvent(new TurnChangeEvent()));
    }
  }
}
