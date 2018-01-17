package pl.korotkevics.ships.client.client;

import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * Enable to fire event reacting changing turn.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class TurnTrigger implements EventTrigger {

  private static final Target logger = new SharedLogger(Client.class);

  @Override
  public void fire(final Button button, final Message message) {
    if (message.getStatement().isEmpty()) {
      Platform.runLater(() -> button.fireEvent(new TurnChangeEvent()));
    }
  }
}
