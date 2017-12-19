package com.epam.ships.server;

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

    Game(CommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
        this.turnManager = new TurnManager(communicationBus.getFirstClient(), communicationBus.getSecondClient());
    }

    /**
     * A draft dummy method used for demo.
     */
    void play() {
        notifyPlayersThatTheyCanStartGame();
//        TODO: notify Users both are ready
        boolean isGameFinished = false;
        boolean isClientConnected = true;
        this.yourTurnMessage();
        while (!isGameFinished && isClientConnected) {
            Message receivedShot = receiveShot();
            isClientConnected = isClientConnected(receivedShot);
            turnManager.switchPlayer();
            if(isClientConnected){
                this.sendOpponentShot(receivedShot);
            }
            rest();
        }
    }

    private void sendOpponentShot(Message receivedShot) {
        this.communicationBus.send(this.turnManager.getCurrentPlayer(), receivedShot);
        logger.info("Shot send");
    }

    private Message receiveShot() {
        final Message shot = this.communicationBus.receive(this.turnManager.getCurrentPlayer());
        logger.info(shot);
        return shot;
    }

    private void yourTurnMessage(){
        final Message turn = new MessageBuilder().withAuthor("server").withHeader("yourTurn").withStatus("OK").build();
        this.communicationBus.send(turnManager.getCurrentPlayer(), turn);
    }

    private boolean isClientConnected(final Message messageReceived) {
        boolean isClientConnected = true;
        if ("Connection".equals(messageReceived.getHeader()) && "END".equals(messageReceived.getStatus())){
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
