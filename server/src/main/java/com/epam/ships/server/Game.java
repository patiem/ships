package com.epam.ships.server;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;

/**
 * @author Piotr, Magda
 * @since 2017-12-09
 * <p>
 * It starts a communication bus
 * and receives messages from it.
 */
class Game {

    private final CommunicationBus communicationBus;
    private final Target logger = new SharedLogger(Game.class);
    private final TurnManager turnManager;
    private Fleet firstPlayerFleet;
    private Fleet secondPlayerFleet;

    Game(CommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
        this.turnManager = new TurnManager(communicationBus.getFirstClient(), communicationBus.getSecondClient());
    }

    /**
     * A draft dummy method used for demo.
     */
    void play() {
        this.notifyPlayersThatTheyCanStartGame();
        boolean isGameFinished = false;
        boolean isClientConnected = true;
        this.rest();
        this.askPlayersForPlaceFleet();
        this.receiveFleetFromBothPlayers();
        this.sendYourTurnMessage();
        while (!isGameFinished && isClientConnected) {
            Message receivedShot = this.receiveShot();
            isClientConnected = this.isClientConnected(receivedShot);
//            this.shotHandler.handle(receivedShot);
//            this.turnManager.switchPlayer();
            if (isClientConnected) {
                if (this.turnManager.isCurrentPlayerFirstPlayer()) {
                    this.handleShot(receivedShot, secondPlayerFleet);
                }else {
                    this.handleShot(receivedShot, firstPlayerFleet);
                }
            }
            this.rest();
        }
    }

    private void handleShot(Message receivedShot, Fleet fleet) {
        final Mast mast = Mast.ofIndex(Integer.valueOf(receivedShot.getStatement()));
        if (fleet.handleDamage(mast)) {
            this.handleHit(receivedShot);
        } else {
            this.handleMiss(receivedShot);
        }
    }

    private void handleHit(Message receivedShot) {
        this.sendHitMessage();
        this.sendOpponentShot(receivedShot);
    }

    private void sendHitMessage() {
        final Message turn = new MessageBuilder().withAuthor("server").withHeader("youHit").withStatus("OK").build();
        this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    }

    private void handleMiss(Message receivedShot) {
        this.sendMissMessage();
        this.sendOpponentShot(receivedShot);
        this.turnManager.switchPlayer();
        this.sendYourTurnMessage();
    }

    private void sendMissMessage() {
        final Message turn = new MessageBuilder().withAuthor("server").withHeader("youMissed").withStatus("OK").build();
        this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    }

    private void receiveFleetFromBothPlayers() {
        this.firstPlayerFleet = this.receiveFloat();
        this.turnManager.switchPlayer();
        this.secondPlayerFleet = this.receiveFloat();
        this.turnManager.switchPlayer();
    }

    private Fleet receiveFloat() {
        final Message fleet = this.communicationBus.receive(this.turnManager.getCurrentPlayer());
        logger.info("Fleet received");
        //TODO unwrap fleet
        return null;
    }

    private void askPlayersForPlaceFleet() {
        this.askForPlaceFleet();
        this.turnManager.switchPlayer();
        this.askForPlaceFleet();
        this.turnManager.switchPlayer();
    }

    private void askForPlaceFleet() {
        final Message message = new MessageBuilder().withAuthor("server").withHeader("PlaceFleet").withStatus("OK").build();
        this.communicationBus.send(turnManager.getCurrentPlayer(), message);
    }

    private void sendOpponentShot(Message receivedShot) {
        this.communicationBus.send(this.turnManager.getOtherPlayer(), receivedShot);
        logger.info("Shot send");
    }

    private Message receiveShot() {
        final Message shot = this.communicationBus.receive(this.turnManager.getCurrentPlayer());
        logger.info(shot);
        return shot;
    }

    private void sendYourTurnMessage() {
        final Message turn = new MessageBuilder().withAuthor("server").withHeader("yourTurn").withStatus("OK").build();
        this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    }

    private boolean isClientConnected(final Message messageReceived) {
        boolean isClientConnected = true;
        if ("Connection".equals(messageReceived.getHeader()) && "END".equals(messageReceived.getStatus())) {
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
        final Message message = new MessageBuilder().withAuthor("server").withHeader("opponentConnected").withStatus("OK").withStatement("true").build();
        this.communicationBus.send(this.turnManager.getCurrentPlayer(), message);
    }

    private void rest() {
        final long restTime = 300;
        try {
            Thread.sleep(restTime);
        } catch (final InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
