package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.OpponentShotEvent;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

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
