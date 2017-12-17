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
    void letsChatALittleBit() {
        boolean flag = true;
        while (flag) {

            simpleConversation();
            turnManager.switchPlayer();
            simpleConversation();
        }
    }

    private boolean simpleConversation() {
        boolean flag = true;
        Message messageReceived = communicationBus.receive(turnManager.getCurrentPlayer());

        logger.info(messageReceived);

        if (StringUtils.isNotEmpty(messageReceived.getAuthor())) {
            String author = messageReceived.getAuthor();

            MessageBuilder messageBuilder = new MessageBuilder()
                    .withAuthor("Name Validation")
                    .withHeader("Server");

            Message message;

            if (Character.isUpperCase(author.charAt(0))) {
                message = messageBuilder
                        .withStatus("OK")
                        .withStatement("No warnings")
                        .build();
            } else {
                message = messageBuilder
                        .withStatus("ERR")
                        .withStatement("Name should begin with a capital letter")
                        .build();
            }

            communicationBus.send(turnManager.getCurrentPlayer(), message);
        }
        if ("Connection".equals(messageReceived.getHeader()) && "END".equals(messageReceived.getStatus())) {
            flag = false;
        }
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        return flag;
    }
}
