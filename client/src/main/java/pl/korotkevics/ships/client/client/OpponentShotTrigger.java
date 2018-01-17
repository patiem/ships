package pl.korotkevics.ships.client.client;

import pl.korotkevics.ships.client.gui.events.OpponentShotEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * Enable to fire event reacting to opponent shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
class OpponentShotTrigger implements EventTrigger {
  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() ->
        button.fireEvent(new OpponentShotEvent(Integer.valueOf(message.getStatement()))));
  }
}
