package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;

/**
 * It's sends messages to client.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class MessageSender {
  private final MessageRepository messages;
  private CommunicationBus communicationBus;
  private Target logger;

  /**
   * Creates Message sender instance.
   *
   * @param communicationBus client server communication bus
   * @param logger           logger comes from class witch want to sand proper message
   */
  public MessageSender(final CommunicationBus communicationBus, final Target logger) {
    this.communicationBus = communicationBus;
    this.logger = logger;
    messages = new MessageRepository();
  }

  /**
   * Send message to given client bases on message header.
   *
   * @param addressee message addressee
   * @param header    header of message to send
   */
  public void send(final WrappedClient addressee, final Header header) {
    communicationBus.send(addressee, messages.getMessage(header));
    logger.info("Send message with header: " + header);
  }

  /**
   * Send given message to proper given client.
   *
   * @param addressee message addressee
   * @param message   message to send
   */
  public void send(final WrappedClient addressee, final Message message) {
    communicationBus.send(addressee, message);
    logger.info("Send message with header: " + message.getHeader());
  }

  /**
   * Send message to all connected clients.
   *
   * @param header header of message to send
   */
  public void sendToAll(final Header header) {
    communicationBus.sendToAll(messages.getMessage(header));
    logger.info("Send message to both players " + header);
  }
}
