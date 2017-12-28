package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.WinEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

class WinTrigger implements EventTrigger {
  @Override
  public void fire(Button button, String messageStatement) {
    Platform.runLater(() -> button.fireEvent(new WinEvent()));
  }
}
