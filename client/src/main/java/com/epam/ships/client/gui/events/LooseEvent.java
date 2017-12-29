package com.epam.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when user lose the game.
 * @author Magdalena Aarsman
 * @since 2017-12-19
 */
public class LooseEvent extends Event {
  public static final EventType<LooseEvent> GAME_LOSE =
      new EventType<>(Event.ANY, "GAME_LOSE");

  public LooseEvent() {
    super(GAME_LOSE);
  }
}
