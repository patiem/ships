package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

public class LooseEvent extends Event {
  public static final EventType<LooseEvent> GAME_LOSE =
      new EventType<>(Event.ANY, "GAME_LOSE");

  public LooseEvent() {
    super(GAME_LOSE);
  }
}
