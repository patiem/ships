package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import pl.korotkevics.ships.shared.fleet.Fleet;

/**
 * Event firing when a fleet is placed randomly.
 *
 * @author Magdalena Aarsman
 * @since 2017-01-15
 */
public class RandomPlacementEvent extends Event {
  public static final EventType<RandomPlacementEvent> RANDOM_PLACEMENT_EVENT =
      new EventType<>(javafx.event.Event.ANY, "RANDOM_PLACEMENT_EVENT");

  @Getter
  private final Fleet fleet;

  public RandomPlacementEvent(final Fleet fleet) {
    super(RANDOM_PLACEMENT_EVENT);
    this.fleet = fleet;
  }
}
