package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.OpponentConnectedEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to opponent connecting.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
class OpponentConnectedTrigger implements EventTrigger {
  
  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() -> button.fireEvent(new OpponentConnectedEvent()));
  }
}
