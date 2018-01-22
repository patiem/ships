package pl.korotkevics.ships.server.game.gamestates.play;

import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.game.TurnManager;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;

/**
 * Notification for client that his shot missed a target.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
class DamageMissNotifier extends DamageNotifier {
  DamageMissNotifier(final MessageSender messageSender, final TurnManager turnManager) {
    super(messageSender, turnManager);
    this.header = Header.MISS;
  }

  @Override
  protected void notify(final Message receivedShot) {
    messageSender.send(turnManager.getCurrentPlayer(), header);
    informOpponentAboutShot(receivedShot);
    this.turnManager.switchPlayer();
  }
}
