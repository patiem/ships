package com.epam.ships.server;

import com.epam.ships.communication.api.Message;
import com.epam.ships.communication.impl.message.MessageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Piotr, Magda
 * @since 2017-12-09
 * <p>
 * It starts a communication bus
 * and receives messages from it.
 */
class Game {

    private CommunicationBus communicationBus;
    private Logger logger = LogManager.getLogger(Game.class);

    Game(CommunicationBus communicationBus) {
        this.communicationBus = communicationBus;
    }

    /**
     * A draft dummy method used for demo.
     */
    void letsChatALittleBit() {
        while (true) {

            Message messageReceived = communicationBus.receive();

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

                communicationBus.send(message);
            }

            if ("Connection".equals(messageReceived.getHeader()) && "END".equals(messageReceived.getStatus())) {
                break;
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
