package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class RandomPlacementEvent  extends Event {
  public static final EventType<RandomPlacementEvent> RANDOM_PLACEMENT_EVENT =
      new EventType<>(javafx.event.Event.ANY, "RANDOM_PLACEMENT_EVENT");

  public RandomPlacementEvent() {
    super(RANDOM_PLACEMENT_EVENT);
  }
}
