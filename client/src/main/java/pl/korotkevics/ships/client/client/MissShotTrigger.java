package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.MissShotEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to missing shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class MissShotTrigger implements EventTrigger {

  private final MissShotEvent missShotEvent;

  MissShotTrigger(MissShotEvent missShotEvent) {
    this.missShotEvent = missShotEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(this.missShotEvent);
  }

  @Override
  public Event getEvent() {
    return missShotEvent;
  }
}
