package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.WinEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to win the game.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class WinTrigger implements EventTrigger {

  private final WinEvent winEvent;

  WinTrigger(WinEvent winEvent) {
    this.winEvent = winEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(this.winEvent);
  }

  @Override
  public Event getEvent() {
    return winEvent;
  }
  
  @Override
  public String provideDescription() {
    return "Victory!! Yeah";
  }
}
