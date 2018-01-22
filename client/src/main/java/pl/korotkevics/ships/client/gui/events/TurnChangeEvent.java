package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when a turn has to be changed (toggled).
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
