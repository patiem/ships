package com.epam.ships.client.client;

import com.epam.ships.client.gui.events.HitShotEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * Enable to fire event reacting to opponent shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class HitShotTrigger implements EventTrigger {
  @Override
  public void fire(Button button, String messageStatement) {
    Platform.runLater(() -> button.fireEvent(new HitShotEvent()));
  }
}
