package pl.korotkevics.ships.client.client;

import javafx.scene.control.Button;
import pl.korotkevics.ships.shared.infra.communication.api.Message;

/**
 * Trigger gui event from client listen thread.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */

interface EventTrigger {
  void fire(final Button button, final Message message);
}
