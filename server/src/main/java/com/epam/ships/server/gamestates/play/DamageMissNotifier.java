package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

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
