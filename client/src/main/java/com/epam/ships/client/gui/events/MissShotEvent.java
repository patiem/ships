package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class MissShotEvent extends Event {
  public static final EventType<MissShotEvent> MISS_SHOT =
      new EventType<>(Event.ANY, "MISS_SHOT");

  public MissShotEvent() {
    super(MISS_SHOT);
  }
}
