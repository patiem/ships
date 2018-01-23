package pl.korotkevics.ships.client.client;

import javafx.event.Event;
import pl.korotkevics.ships.shared.infra.communication.api.Message;


/**
 * Trigger gui event from client listen thread.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */

interface EventTrigger {
  void fire(final DispatcherAdapter dispatcherAdapter, final Message message);

   Event getEvent();
}
