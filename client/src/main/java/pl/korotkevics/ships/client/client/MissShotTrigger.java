package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.MissShotEvent;

/**
 * Enable to fire event reacting to missing shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class MissShotTrigger implements EventTrigger {
  @Override
  public void fire(Button button, String messageStatement) {
    Platform.runLater(() -> button.fireEvent(new MissShotEvent()));
  }
}
