package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

public class DispatcherAdapter {

  @Setter
  @Getter
  private Button eventButton;

  <T extends Event> void fireEvent(T event) {
    Platform.runLater(() -> eventButton.fireEvent(event));
  }
}
