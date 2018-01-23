package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.OpponentWithdrawEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;


/**
 * Enable to fire event reacting to opponent withdraw.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class ConnectionEndTrigger implements EventTrigger {

  private final OpponentWithdrawEvent opponentWithdrawEvent;

  ConnectionEndTrigger(OpponentWithdrawEvent opponentWithdrawEvent) {
    this.opponentWithdrawEvent = opponentWithdrawEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    //Platform.runLater(() -> button.fireEvent(new OpponentWithdrawEvent()));
    dispatcherAdapter.fireEvent(this.opponentWithdrawEvent);
  }

  @Override
  public Event getEvent() {
    return opponentWithdrawEvent;
  }
}
