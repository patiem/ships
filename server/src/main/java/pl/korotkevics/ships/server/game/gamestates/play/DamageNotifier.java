package pl.korotkevics.ships.server.game.gamestates.play;

import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.game.TurnManager;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;

/**
 * Notification about strength of shot.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
abstract class DamageNotifier {
  protected MessageSender messageSender;
  protected TurnManager turnManager;
  protected Header header;

  DamageNotifier(final MessageSender messageSender, final TurnManager turnManager) {
    this.messageSender = messageSender;
    this.turnManager = turnManager;
  }

  protected void notify(final Message receivedShot) {
    messageSender.send(turnManager.getCurrentPlayer(), header);
    informOpponentAboutShot(receivedShot);
  }

  void informOpponentAboutShot(final Message receivedShot) {
    messageSender.send(turnManager.getOtherPlayer(), receivedShot);
  }
}
