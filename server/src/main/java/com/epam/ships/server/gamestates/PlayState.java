package com.epam.ships.server.gamestates;

import com.epam.ships.fleet.Damage;
import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.api.message.Status;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;

import java.io.IOException;
import java.util.List;

public class PlayState implements GameState {
  private CommunicationBus communicationBus;
  private List<Fleet> fleets;
  private boolean isClientDisconnected;
  private boolean isGameFinished;
  private final Target logger = new SharedLogger(PlayState.class);
  private final TurnManager turnManager;

  public PlayState(CommunicationBus communicationBus, List<Fleet> fleets) {
    this.communicationBus = communicationBus;
    this.fleets = fleets;
    isGameFinished = false;
    isClientDisconnected = false;
    this.turnManager = new TurnManager(communicationBus.getFirstClient(),
        communicationBus.getSecondClient());
  }

  @Override
  public GameState process() {
    sendYourTurnMessage();
    Message receivedShot = this.receiveShot();
    isClientDisconnected = this.isClientConnected(receivedShot);
    if (!isClientDisconnected) {
      if (this.turnManager.isCurrentPlayerFirstPlayer()) {
        isGameFinished = this.handleShot(receivedShot, fleets.get(1));
      } else {
        isGameFinished = this.handleShot(receivedShot, fleets.get(0));
      }
    }

    if (shouldGameBeEnded()) {
      return new EndGameState(communicationBus, turnManager);
    }

    rest();
    return this;
  }

  private boolean shouldGameBeEnded() {
    return isGameFinished || isClientDisconnected;
  }


  private void sendYourTurnMessage() {
    final Message turn = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.YOUR_TURN)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    logger.info("send your turn");
  }


  private boolean handleShot(Message receivedShot, Fleet fleet) {
    final Mast mast = Mast.ofIndex(receivedShot.getStatement());
    logger.debug(receivedShot.getStatement());
    Damage damage = fleet.handleDamage(mast);
    if (damage.equals(Damage.MISSED)) {
      logger.debug("got here: missed");
      this.handleMiss(receivedShot);
    } else if (damage.equals(Damage.HIT)) {
      logger.debug("got here: hit");
      this.handleHit(receivedShot);
    } else if (damage.equals(Damage.DESTRUCTED)) {
      logger.debug("got here: destructed");
      this.handleDamageShot(receivedShot);
    }
    return fleet.isDefeated();
  }

  private void handleDamageShot(Message receivedShot) {
    this.sendShipDestructedMessage();
    this.sendShotToOpponent(receivedShot);
  }

  private void handleEndOfGame() {
    this.sendWonMessage();
    this.sendLostMessage();
  }

  private void sendWonMessage() {
    final Message won = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.WIN)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), won);
  }

  private void sendLostMessage() {
    final Message lost = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.LOSE)
        .build();
    this.communicationBus.send(turnManager.getOtherPlayer(), lost);
  }

  private void sendShipDestructedMessage() {
    final Message hit = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.SHIP_DESTRUCTED)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), hit);
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

  private Message receiveShot() {
    final Message shot = this.communicationBus.receive(this.turnManager.getCurrentPlayer());
    logger.info(shot);
    return shot;
  }


  private boolean isClientConnected(final Message messageReceived) {
    boolean isClientConnected = true;
    if (Header.CONNECTION.equals(messageReceived.getHeader())
        && Status.END.equals(messageReceived.getStatus())) {
      isClientConnected = false;
    }
    return isClientConnected;
  }

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
