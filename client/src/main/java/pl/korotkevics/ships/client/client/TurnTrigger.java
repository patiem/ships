package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting changing turn.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class TurnTrigger implements EventTrigger {
  @Override
  public void fire(final Button button, final Message message) {
    if (message.getStatement().isEmpty()) {
      Platform.runLater(() -> button.fireEvent(new TurnChangeEvent()));
    }
  }
}
