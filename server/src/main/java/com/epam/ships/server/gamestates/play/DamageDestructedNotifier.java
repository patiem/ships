package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

/**
 * Notification for player that he destroyed a ship.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
class DamageDestructedNotifier extends DamageNotifier {
  DamageDestructedNotifier(final MessageSender messageSender,final TurnManager turnManager) {
    super(messageSender, turnManager);
    this.header = Header.SHIP_DESTRUCTED;
  }
}
