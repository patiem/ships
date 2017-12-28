package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.LooseEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

class LoseTrigger implements EventTrigger {

  @Override
  public void fire(Button button, String messageStatement) {
    Platform.runLater(() -> button.fireEvent(new LooseEvent()));
  }
}
