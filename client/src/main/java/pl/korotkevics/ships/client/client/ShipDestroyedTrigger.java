package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.ShipDestroyedEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Trigger event reacting to destroyed ship.
 *
 * @author Magdalena Aarsman
 * @since 2017-01-19
 */
public class ShipDestroyedTrigger implements EventTrigger {
  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() -> button.fireEvent(new ShipDestroyedEvent()));
  }
}
