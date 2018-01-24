package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.LossEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to lose the game.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class LoseTrigger implements EventTrigger {

  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() -> button.fireEvent(new LossEvent()));
  }
  
  @Override
  public String provideDescription() {
    return "Loss:( Très mal";
  }
}
