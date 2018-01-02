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

class ShotHandler {
  private final Target logger = new SharedLogger(ShotHandler.class);
  private final MessageSender messageSender;
  private TurnManager turnManager;
  private List<Fleet> fleets;


  ShotHandler(CommunicationBus communicationBus, TurnManager turnManager, List<Fleet> fleets) {
    this.turnManager = turnManager;
    this.fleets = fleets;
    this.messageSender = new MessageSender(communicationBus, logger);
  }

  public boolean handle(boolean isShotByFirstPlayer, Message shot) {
    Fleet fleet = getRightFleet(isShotByFirstPlayer);
    return handleShot(shot, fleet);
  }

  private boolean handleShot(Message receivedShot, Fleet fleet) {
    logger.debug("Handling shot at " + receivedShot.getStatement());
    final Mast mast = Mast.ofIndex(receivedShot.getStatement());
    Damage damage = fleet.handleDamage(mast);
    DamageNotifier damageNotifier = new DamageMissNotifier(messageSender, turnManager);
    if (damage.equals(Damage.MISSED)) {
      damageNotifier = new DamageMissNotifier(messageSender, turnManager);
    } else if (damage.equals(Damage.HIT)) {
      damageNotifier = new DamageHitNotifier(messageSender, turnManager);
    } else if (damage.equals(Damage.DESTRUCTED)) {
      damageNotifier = new DamageDestructedNotifier(messageSender, turnManager);
    }
    damageNotifier.notify(receivedShot);
    return fleet.isDefeated();
  }

  private Fleet getRightFleet(boolean isShotByFirstPlayer) {
    if (isShotByFirstPlayer) {
      return fleets.get(1);
    } else {
      return fleets.get(0);
    }
  }
}
