package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.ShipDestroyedEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Trigger event reacting to destroyed ship.
 *
 * @author Magdalena Aarsman
 * @since 2017-01-19
 */
class ShipDestroyedTrigger implements EventTrigger {
  private final ShipDestroyedEvent shipDestroyedEvent;

  ShipDestroyedTrigger(ShipDestroyedEvent shipDestroyedEvent) {
    this.shipDestroyedEvent = shipDestroyedEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(this.shipDestroyedEvent);
  }

  @Override
  public Event getEvent() {
    return shipDestroyedEvent;
  }
  
  @Override
  public String provideDescription() {
    return "What a luck! A ship has been destroyed. Go on!";
  }
}
