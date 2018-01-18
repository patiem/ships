package pl.korotkevics.ships.server.gamestates.play;

import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.TurnManager;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;

/**
 * Notification for player that he destroyed a ship.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
class DamageDestroyedNotifier extends DamageNotifier {
  DamageDestroyedNotifier(final MessageSender messageSender, final TurnManager turnManager) {
    super(messageSender, turnManager);
    this.header = Header.SHIP_DESTROYED;
  }
}
