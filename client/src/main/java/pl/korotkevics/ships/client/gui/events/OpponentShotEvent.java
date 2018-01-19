package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

/**
 * Event firing when opponent performs a shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
public class OpponentShotEvent extends Event {
  public static final EventType<OpponentShotEvent> OPPONENT_SHOT = new EventType<>(Event.ANY,
                                                                                      "OPPONENT_SHOT");
  
  @Getter
  final int shotIndex;
  
  public OpponentShotEvent(int shotIndex) {
    super(OPPONENT_SHOT);
    this.shotIndex = shotIndex;
  }
}
