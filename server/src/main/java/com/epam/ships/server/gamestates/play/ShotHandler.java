package com.epam.ships.server.gamestates.play;

import com.epam.ships.fleet.Damage;
import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

import java.util.List;

class ShotHandler {
  private TurnManager turnManager;
  private List<Fleet> fleets;
  private final Target logger = new SharedLogger(ShotHandler.class);
  MessageSender messageSender;


  public ShotHandler(CommunicationBus communicationBus, TurnManager turnManager, List<Fleet> fleets) {
    this.turnManager = turnManager;
    this.fleets = fleets;
    messageSender = new MessageSender(communicationBus, logger);
  }

  public boolean handle(boolean isShotByFirstPlayer, Message shot) {
    Fleet fleet = getRightFleet(isShotByFirstPlayer);
    return handleShot(shot, fleet);
  }

  private boolean handleShot(Message receivedShot, Fleet fleet) {
    final Mast mast = Mast.ofIndex(receivedShot.getStatement());
    Damage damage = fleet.handleDamage(mast);

    if (damage.equals(Damage.MISSED)) {
      this.handleMiss(receivedShot);
    } else if (damage.equals(Damage.HIT)) {
      this.handleHit(receivedShot);
    } else if (damage.equals(Damage.DESTRUCTED)) {
      this.handleDamageShot(receivedShot);
    }
    return fleet.isDefeated();
  }

  private void handleDamageShot(Message receivedShot) {
    messageSender.send(turnManager.getCurrentPlayer(), Header.HIT);
    this.informOpponentAboutShot(receivedShot);
  }


  private void handleHit(Message receivedShot) {
    messageSender.send(turnManager.getCurrentPlayer(), Header.HIT);
    this.informOpponentAboutShot(receivedShot);
  }


  private void handleMiss(Message receivedShot) {
    messageSender.send(turnManager.getCurrentPlayer(), Header.MISS);
    this.informOpponentAboutShot(receivedShot);
    this.turnManager.switchPlayer();
  }


  private void informOpponentAboutShot(Message receivedShot) {
    messageSender.send(this.turnManager.getOtherPlayer(), receivedShot);
  }

  private Fleet getRightFleet(boolean isShotByFirstPlayer) {
    if (isShotByFirstPlayer) {
      return fleets.get(1);
    } else {
      return fleets.get(0);
    }
  }
}
