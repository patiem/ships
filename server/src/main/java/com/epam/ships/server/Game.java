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
        while (!isGameFinished && isClientConnected) {
            Message messageReceived = exchangeGreetings();
            isClientConnected = isClientConnected(messageReceived);
            turnManager.switchPlayer();
//            TODO: game loop until we will find winner
//            isGameFinished = some referee method?

            rest();
        }
    }

    private final boolean isClientConnected(final Message messageReceived) {
        boolean isClientConnected = true;
        if ("Connection".equals(messageReceived.getHeader()) && "END".equals(messageReceived.getStatus())) {
                isClientConnected = false;
            }
        return isClientConnected;
    }

    private final Message exchangeGreetings() {
        final Message message = new MessageBuilder().withAuthor("server").withHeader("greetings").withStatus("OK").withStatement("Welcome on board").build();
        this.communicationBus.send(this.turnManager.getCurrentPlayer(), message);
        final Message greetings = this.communicationBus.receive(this.turnManager.getCurrentPlayer());
        logger.info(greetings);
        return greetings;
    }

    private void notifyPlayersThatTheyCanStartGame() {
        this.opponentConnected();
        this.turnManager.switchPlayer();
        this.opponentConnected();
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
