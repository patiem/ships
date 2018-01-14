package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.RandomPlacementEvent;

public class RandomPlacementTrigger implements EventTrigger {
  @Override
  public void fire(Button button, String messageStatement) {
    Platform.runLater(() ->
        button.fireEvent(new RandomPlacementEvent()));
  }
}
