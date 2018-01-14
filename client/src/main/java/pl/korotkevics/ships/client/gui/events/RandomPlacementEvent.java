package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;
import pl.korotkevics.ships.shared.fleet.Fleet;

public class RandomPlacementEvent  extends Event {
  public static final EventType<RandomPlacementEvent> RANDOM_PLACEMENT_EVENT =
      new EventType<>(javafx.event.Event.ANY, "RANDOM_PLACEMENT_EVENT");

  @Getter
  final Fleet fleet;

  public RandomPlacementEvent(Fleet fleet) {
    super(RANDOM_PLACEMENT_EVENT);
    this.fleet = fleet;
  }
}
