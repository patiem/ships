package pl.korotkevics.ships.client.gui.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event firing when user win the game.
 * @author Magdalena Aarsman
 * @since 2017-12-19
 */
public class WinEvent extends Event {
  public static final EventType<WinEvent> GAME_WIN =
      new EventType<>(Event.ANY, "GAME_WIN");

  public WinEvent() {
    super(GAME_WIN);
  }
}
