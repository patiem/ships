package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;
/**
 * Notification for client that his shot missed a target.
 *
 * @author Piotr Czyż
 * @since 02.01.2018
 */
class DamageMissNotifier extends DamageNotifier {
  DamageMissNotifier(MessageSender messageSender, TurnManager turnManager) {
    super(messageSender, turnManager);
    this.header = Header.MISS;
  }

  @Override
  protected void notify(Message receivedShot) {
    messageSender.send(turnManager.getCurrentPlayer(), header);
    informOpponentAboutShot(receivedShot);
    this.turnManager.switchPlayer();
  }
}
