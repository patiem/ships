package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when player destroys a ship.
 *
 * @author Magdalena Aarsman
 * @since 2017-01-19
 */
public class ShipDestroyedEvent extends Event {
  public static final EventType<ShipDestroyedEvent> SHIP_DESTROYED = new EventType<>(Event
      .ANY, "SHIP_DESTROYED");

  public ShipDestroyedEvent() {
    super(SHIP_DESTROYED);
  }
}
