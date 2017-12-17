package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Piotr, Magda
 * @since 2017-12-09
 * <p>
 * It starts a communication bus
 * and receives messages from it.
 */
class Game {

    private CommunicationBus communicationBus;
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
//        TODO: notify Users they have to place fleet
//        TODO: notify Users both are ready
        boolean isGameFinished = false;
        while (!isGameFinished ){
            exchangeGreetings();
            turnManager.switchPlayer();
//            TODO: game loop until we will find winner
//            isGameFinished = some referee method?
            waitAWhile();
        }
    }


    private void exchangeGreetings(){
        MessageBuilder messageBuilder = new MessageBuilder();
        Message message = messageBuilder.withAuthor("server").withHeader("greetings").withStatus("OK").withStatement("Welcome on board").build();
        communicationBus.send(turnManager.getCurrentPlayer(), message);
        Message greetings = communicationBus.receive(turnManager.getCurrentPlayer());
        logger.info(greetings);
    }

    private void notifyPlayersThatTheyCanStartGame(){
       opponentConnected();
       turnManager.switchPlayer();
       opponentConnected();
   }

    private void opponentConnected(){
        MessageBuilder messageBuilder = new MessageBuilder();
        Message message = messageBuilder.withAuthor("server").withHeader("opponentConnected").withStatus("OK").withStatement("true").build();
        communicationBus.send(turnManager.getCurrentPlayer(), message);
    }

    private void waitAWhile() {
        final long aWhile = 300;
        try {
            Thread.sleep(aWhile);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
}
