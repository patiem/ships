package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;

public class MessageSender {
  private CommunicationBus communicationBus;
  private Target logger;
  private final MessageRepository messages;

  public MessageSender(CommunicationBus communicationBus, Target logger) {

    this.communicationBus = communicationBus;
    this.logger = logger;
    messages = new MessageRepository();
  }

  public void send(WrappedClient addressee, Header header) {
    communicationBus.send(addressee, messages.getMessage(header));
    logger.info("Send message with header: " + header);
  }

  public void send(WrappedClient addressee, Message message) {
    communicationBus.send(addressee, message);
    logger.info("Send message with header: " + message.getHeader());
  }

  public void sendToAll(Header header) {
    communicationBus.sendToAll(messages.getMessage(header));
    logger.info("Send message to both players " + header);
  }
}
