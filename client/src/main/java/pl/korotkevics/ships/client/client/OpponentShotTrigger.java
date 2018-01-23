package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.OpponentShotEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to opponent shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */
class OpponentShotTrigger implements EventTrigger {
  //TODO: pass parameter to event
  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(new OpponentShotEvent(Integer.valueOf(message.getStatement())));
  }

  @Override
  public Event getEvent() {
    return null;
  }
}
