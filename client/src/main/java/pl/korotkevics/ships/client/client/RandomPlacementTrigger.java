package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.RandomPlacementEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to asking for random fleet.
 *
 * @author Magdalena Aarsman
 * @since 2017-01-15
 */
class RandomPlacementTrigger implements EventTrigger {
  @Override
  public void fire(final Button button, final Message message) {
    Platform.runLater(() ->
        button.fireEvent(new RandomPlacementEvent(message.getFleet())));
  }
  
  @Override
  public String provideDescription() {
    return "Random fleet placement has been applied. Someone is lazy!";
  }
}

