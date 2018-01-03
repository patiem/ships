package com.epam.ships.server.gamestates.play;

import com.epam.ships.fleet.Damage;
import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

import java.util.List;

/**
 * Handles a player shot: checks if a shot makes a damage on a fleet and send proper message
 * to clients.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
class ShotHandler {
  private final Target logger = new SharedLogger(ShotHandler.class);
  private final MessageSender messageSender;
  private TurnManager turnManager;
  private List<Fleet> fleets;


  ShotHandler(final CommunicationBus communicationBus, final TurnManager turnManager,
              final List<Fleet> fleets) {
    this.turnManager = turnManager;
    this.fleets = fleets;
    this.messageSender = new MessageSender(communicationBus, logger);
  }

  /**
   * Handles player shot.
   *
   * @param isShotByFirstPlayer it checks who shots
   * @param shot                Message with shot
   * @return true if there is no more ships in opponent fleet
   */
  public boolean handle(final boolean isShotByFirstPlayer, final Message shot) {
    Fleet fleet = getRightFleet(isShotByFirstPlayer);
    return handleShot(shot, fleet);
  }

  private boolean handleShot(final Message receivedShot, final Fleet fleet) {
    logger.debug("Handling shot at " + receivedShot.getStatement());
    final Mast mast = Mast.ofIndex(receivedShot.getStatement());
    Damage damage = fleet.handleDamage(mast);
    DamageNotifier damageNotifier;
    switch (damage) {
      case HIT:
        damageNotifier = new DamageHitNotifier(messageSender, turnManager);
        break;
      case DESTRUCTED:
        damageNotifier = new DamageDestructedNotifier(messageSender, turnManager);
        break;
      default:
        damageNotifier = new DamageMissNotifier(messageSender, turnManager);
        break;
    }
    damageNotifier.notify(receivedShot);
    return fleet.isDefeated();
  }

  private Fleet getRightFleet(final boolean isShotByFirstPlayer) {
    return isShotByFirstPlayer ? fleets.get(1) : fleets.get(0);
  }
}
