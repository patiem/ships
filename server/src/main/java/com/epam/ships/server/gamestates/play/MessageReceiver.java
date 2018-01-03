package com.epam.ships.server.gamestates.play;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.WrappedClient;

/**
 * Receive message from client.
 *
 * @author Piotr Czyż
 * @since 02.01.2018
 */
public class MessageReceiver {
  private final Target logger = new SharedLogger(MessageReceiver.class);
  private CommunicationBus communicationBus;
  private Message lastMessage;

  MessageReceiver(CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  void receive(WrappedClient player) {
    lastMessage = communicationBus.receive(player);
    logger.info(lastMessage);
  }

  boolean isAShot() {
    return Header.SHOT.equals(lastMessage.getHeader());
  }

  Message getMessage() {
    return lastMessage;
  }
}
