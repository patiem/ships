package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.TurnChangeEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting changing turn.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class TurnTrigger implements EventTrigger {
  private final TurnChangeEvent turnChangeEvent;

  TurnTrigger(TurnChangeEvent turnChangeEvent) {
    this.turnChangeEvent = turnChangeEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    if (message.getStatement().isEmpty()) {
      dispatcherAdapter.fireEvent(this.turnChangeEvent);
    }
  }
  
  @Override
  public String provideDescription() {
    return "A new turn...";
  }

  @Override
  public Event getEvent() {
    return turnChangeEvent;
  }
}
