package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Button;
import pl.korotkevics.ships.client.gui.events.RandomPlacementEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to asking for random fleet.
 *
 * @author Magdalena Aarsman
 * @since 2017-01-15
 */
class RandomPlacementTrigger implements EventTrigger {

  //TODO: pass parameter to event

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(new RandomPlacementEvent(message.getFleet()));
  }

  @Override
  public Event getEvent() {
    return null;
  }
}

