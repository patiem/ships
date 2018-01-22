package pl.korotkevics.ships.server.game.gamestates.play;

import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.game.TurnManager;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;

/**
 * Notification for player that he hit a ship.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
class DamageHitNotifier extends DamageNotifier {
  DamageHitNotifier(final MessageSender messageSender, final TurnManager turnManager) {
    super(messageSender, turnManager);
    this.header = Header.HIT;
  }
}
