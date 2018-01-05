package pl.korotkevics.ships.client.client;

import javafx.scene.control.Button;

/**
 * Enable fire gui event from client listen thread.
 *
 * @author Magdalena Aarsman
 * @since 2017-12-17
 */

interface EventTrigger {
  void fire(final Button button, final String messageStatement);
}
