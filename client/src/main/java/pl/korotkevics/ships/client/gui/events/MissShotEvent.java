package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when user miss the shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
public class MissShotEvent extends Event {
  public static final EventType<MissShotEvent> MISS_SHOT = new EventType<>(Event.ANY, "MISS_SHOT");
  
  public MissShotEvent() {
    super(MISS_SHOT);
  }
}
