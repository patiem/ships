package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when user perform shot.
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
public class HitShotEvent extends Event {
  public static final EventType<HitShotEvent> HIT_SHOT =
      new EventType<>(Event.ANY, "HIT_SHOT");

  public HitShotEvent() {
    super(HIT_SHOT);
  }
}
