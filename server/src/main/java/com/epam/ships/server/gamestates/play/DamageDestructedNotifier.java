package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

class DamageDestructedNotifier extends DamageNotifier {
  DamageDestructedNotifier(MessageSender messageSender, TurnManager turnManager) {
    super(messageSender, turnManager);
    this.header = Header.SHIP_DESTRUCTED;
  }
}
