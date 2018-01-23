package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.HitShotEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to opponent shot.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class HitShotTrigger implements EventTrigger {
  private final HitShotEvent hitShotEvent;

  HitShotTrigger(HitShotEvent hitShotEvent) {
    this.hitShotEvent = hitShotEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(this.hitShotEvent);
  }

  @Override
  public Event getEvent() {
    return hitShotEvent;
  }
}
