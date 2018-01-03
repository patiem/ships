package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;
/**
 * Notification for player that he hit a ship.
 *
 * @author Piotr Czyż
 * @since 02.01.2018
 */
class DamageHitNotifier extends DamageNotifier {
  DamageHitNotifier(MessageSender messageSender, TurnManager turnManager) {
    super(messageSender, turnManager);
    this.header = Header.HIT;
  }
}
