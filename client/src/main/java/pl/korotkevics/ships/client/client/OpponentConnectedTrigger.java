package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.OpponentConnectedEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to opponent connecting.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
class OpponentConnectedTrigger implements EventTrigger {
  private final OpponentConnectedEvent opponentConnectedEvent;

  OpponentConnectedTrigger(OpponentConnectedEvent opponentConnectedEvent) {
    this.opponentConnectedEvent = opponentConnectedEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(this.opponentConnectedEvent);
  }

  @Override
  public Event getEvent() {
    return opponentConnectedEvent;
  }
}
