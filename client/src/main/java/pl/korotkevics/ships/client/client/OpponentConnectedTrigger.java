package pl.korotkevics.ships.client.client;

import pl.korotkevics.ships.client.gui.events.OpponentConnectedEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * Enable to fire event reacting to opponent connecting.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
class OpponentConnectedTrigger implements EventTrigger {

  @Override
  public void fire(final Button button, final String messageStatement) {
    Platform.runLater(() -> button.fireEvent(new OpponentConnectedEvent()));
  }
}
