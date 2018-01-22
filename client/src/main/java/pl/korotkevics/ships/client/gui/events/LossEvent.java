package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when player loses the game.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-19
 */
public class LossEvent extends Event {
  public static final EventType<LossEvent> GAME_LOSS = new EventType<>(Event.ANY, "GAME_LOSS");
  
  public LossEvent() {
    super(GAME_LOSS);
  }
}
