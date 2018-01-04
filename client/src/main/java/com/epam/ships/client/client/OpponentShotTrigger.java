package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.OpponentShotEvent;
import com.epam.ships.shared.infra.logging.api.Target;
import com.epam.ships.shared.infra.logging.core.SharedLogger;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * Enable to fire event reacting to opponent shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
class OpponentShotTrigger implements EventTrigger {

  private static final Target logger = new SharedLogger(Client.class);

  @Override
  public void fire(final Button button, final String messageStatement) {
    Platform.runLater(() ->
        button.fireEvent(new OpponentShotEvent(Integer.valueOf(messageStatement))));
    logger.info("shot index: " + messageStatement);
  }
}
