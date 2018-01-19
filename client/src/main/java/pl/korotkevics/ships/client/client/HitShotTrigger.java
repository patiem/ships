package pl.korotkevics.ships.client.client;

import pl.korotkevics.ships.client.gui.events.HitShotEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to opponent shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class HitShotTrigger implements EventTrigger {
  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() -> button.fireEvent(new HitShotEvent()));
  }
}
