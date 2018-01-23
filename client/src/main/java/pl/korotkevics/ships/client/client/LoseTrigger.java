package pl.korotkevics.ships.client.client;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Button;
import lombok.Getter;
import pl.korotkevics.ships.client.gui.events.LossEvent;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Enable to fire event reacting to lose the game.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-18
 */
class LoseTrigger implements EventTrigger {
  @Getter
  private final LossEvent lossEvent;

  LoseTrigger(LossEvent lossEvent) {
    this.lossEvent = lossEvent;
  }

  @Override
  public void fire(final DispatcherAdapter dispatcherAdapter, final Message message) {
    dispatcherAdapter.fireEvent(this.lossEvent);
  }

  @Override
  public Event getEvent() {
    return lossEvent;
  }
}
