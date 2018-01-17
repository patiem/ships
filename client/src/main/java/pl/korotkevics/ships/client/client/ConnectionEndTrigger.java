package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.OpponentWithdrawEvent;

/**
 * Enable to fire event reacting to opponent withdraw.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class ConnectionEndTrigger implements EventTrigger {

  @Override
  public void fire(final Button button, final String messageStatement) {
    Platform.runLater(() -> button.fireEvent(new OpponentWithdrawEvent()));
  }
}
