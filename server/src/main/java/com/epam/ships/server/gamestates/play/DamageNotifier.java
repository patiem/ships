package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

abstract class DamageNotifier {
  protected MessageSender messageSender;
  protected TurnManager turnManager;
  protected Header header;

  DamageNotifier(MessageSender messageSender, TurnManager turnManager) {
    this.messageSender = messageSender;
    this.turnManager = turnManager;
  }

  protected void notify(Message receivedShot) {
    messageSender.send(turnManager.getCurrentPlayer(), header);
    informOpponentAboutShot(receivedShot);
  }

  void informOpponentAboutShot(Message receivedShot) {
    messageSender.send(turnManager.getOtherPlayer(), receivedShot);
  }
}
