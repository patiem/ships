package pl.korotkevics.ships.server.communication;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;

/**
 * Sends messages to clients.
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
   * Get message by header from MessageRepository and send it to addressee.
   *
   * @param addressee message addressee
   * @param header    header of message to send
   */
  public void send(final WrappedClient addressee, final Header header) {
    communicationBus.send(addressee, messages.getMessage(header));
    logger.info("Send message with header: " + header);
  }

  /**
   * Send message to an addressee.
   *
   * @param addressee message addressee
   * @param message   message to send
   */
  public void send(final WrappedClient addressee, final Message message) {
    communicationBus.send(addressee, message);
    logger.info("Send message with header: " + message.getHeader());
  }

  /**
   * Get message by header from MessageRepository and send it to all connected clients.
   *
   * @param header header of message to send
   */
  public void sendToAll(final Header header) {
    communicationBus.sendToAll(messages.getMessage(header));
    logger.info("Send message to both players " + header);
  }

  public void send(WrappedClient currentPlayer, Header header, boolean b) {
    send(currentPlayer, header);
    communicationBus.addTranscript(currentPlayer, messages.getMessage(header));
  }

  public void send(WrappedClient currentPlayer, Message message, boolean b) {
    send(currentPlayer, message);
    communicationBus.addTranscript(currentPlayer, message);
  }
}
