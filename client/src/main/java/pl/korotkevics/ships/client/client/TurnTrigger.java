package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

/**
 * Enable to fire event reacting changing turn.
 *
 * @author Magdalena Aarsman
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
