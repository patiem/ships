package com.epam.ships.server;

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

/**
 * * It starts a communication bus
 * and receives messages from it.
 * @author Piotr Czyż
 * @since 2017-12-09
 */
class Game {

  private final CommunicationBus communicationBus;
  private final Target logger = new SharedLogger(Game.class);
  private final TurnManager turnManager;
  private Fleet firstPlayerFleet;
  private Fleet secondPlayerFleet;

  Game(CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
    this.turnManager = new TurnManager(communicationBus.getFirstClient(),
        communicationBus.getSecondClient());
  }

  void play() {
    this.notifyPlayersThatTheyCanStartGame();
    this.askPlayersForPlaceFleet();
    this.receiveFleetFromBothPlayers();
    this.rest();
    this.sendYourTurnMessage();
    boolean isClientConnected = true;
    boolean isGameFinished = false;
    while (!isGameFinished && isClientConnected) {
      Message receivedShot = this.receiveShot();
      isClientConnected = this.isClientConnected(receivedShot);
      if (isClientConnected) {
        if (this.turnManager.isCurrentPlayerFirstPlayer()) {
          isGameFinished = this.handleShot(receivedShot, secondPlayerFleet);
        } else {
          isGameFinished = this.handleShot(receivedShot, firstPlayerFleet);
        }
      }
      if (isGameFinished) {
        this.handleEndOfGame();
      }
      this.rest();
    }
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

  private void receiveFleetFromBothPlayers() {
    this.firstPlayerFleet = this.receiveFloat();
    this.turnManager.switchPlayer();
    this.secondPlayerFleet = this.receiveFloat();
    this.turnManager.switchPlayer();
  }

  private Fleet receiveFloat() {
    final Message fleet = this.communicationBus.receive(this.turnManager.getCurrentPlayer());
    logger.info("Fleet received: " + fleet);
    return fleet.getFleet();
  }

  private void askPlayersForPlaceFleet() {
    this.askForPlaceFleet();
    this.turnManager.switchPlayer();
    this.askForPlaceFleet();
    this.turnManager.switchPlayer();
  }

  private void askForPlaceFleet() {
    final Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.PLACEMENT)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), message);
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

  private void sendYourTurnMessage() {
    final Message turn = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.YOUR_TURN)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    logger.info("send your turn");
  }

  private boolean isClientConnected(final Message messageReceived) {
    boolean isClientConnected = true;
    if (Header.CONNECTION.equals(messageReceived.getHeader())
        && Status.END.equals(messageReceived.getStatus())) {
      isClientConnected = false;
    }
    return isClientConnected;
  }

  private void notifyPlayersThatTheyCanStartGame() {
    this.opponentConnected();
    this.turnManager.switchPlayer();
    this.opponentConnected();
    this.turnManager.switchPlayer();
  }

  private void opponentConnected() {
    final Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.OPPONENT_CONNECTED)
        .withStatement("true")
        .build();
    this.communicationBus.send(this.turnManager.getCurrentPlayer(), message);
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