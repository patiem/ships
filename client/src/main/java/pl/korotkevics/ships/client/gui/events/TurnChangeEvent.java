package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when turn has to be changed.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
public class TurnChangeEvent extends Event {
  public static final EventType<TurnChangeEvent> TURN_EVENT = new EventType<>(Event.ANY,
                                                                                 "TURN_EVENT");
  
  public TurnChangeEvent() {
    super(TURN_EVENT);
  }
}
