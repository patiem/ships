package com.epam.ships.server.gamestates.play;

import com.epam.ships.fleet.Damage;
import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;


import java.util.List;

class ShotHandler {
  private CommunicationBus communicationBus;
  private TurnManager turnManager;
  private List<Fleet> fleets;
  private final Target logger = new SharedLogger(ShotHandler.class);

  public ShotHandler(CommunicationBus communicationBus, TurnManager turnManager, List<Fleet> fleets) {
    this.communicationBus = communicationBus;
    this.turnManager = turnManager;

    this.fleets = fleets;
  }

  public boolean handle(boolean isShotByFirstPlayer, Message shot) {
    if (isShotByFirstPlayer) {
      return this.handleShot(shot, fleets.get(1));
    } else {
      return handleShot(shot, fleets.get(0));
    }
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
    this.sendShipDestructedMessage();
    this.sendShotToOpponent(receivedShot);
  }


  private void handleHit(Message receivedShot) {
    this.sendHitMessage();
    this.sendShotToOpponent(receivedShot);
  }

  private void sendHitMessage() {
    final Message hit = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.HIT)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), hit);
  }

  private void handleMiss(Message receivedShot) {
    this.sendMissMessage();
    this.sendShotToOpponent(receivedShot);
    this.turnManager.switchPlayer();
    rest();
    this.sendYourTurnMessage();
  }

  private void sendMissMessage() {
    final Message miss = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.MISS)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), miss);
  }



  private void sendShotToOpponent(Message receivedShot) {
    this.communicationBus.send(this.turnManager.getOtherPlayer(), receivedShot);
    logger.info("Shot send");
  }



  private void sendShipDestructedMessage() {
    final Message hit = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.SHIP_DESTRUCTED)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), hit);
  }


  //TODO remove
  private void sendYourTurnMessage() {
    final Message turn = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.YOUR_TURN)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    logger.info("send your turn");
  }


  //TODO remove
  private void rest() {
    final long restTime = 300;
    try {
      Thread.sleep(restTime);
    } catch (final InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}
