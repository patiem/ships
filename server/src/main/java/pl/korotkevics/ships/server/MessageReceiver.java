package pl.korotkevics.ships.server;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

/**
 * Receive message from client.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class MessageReceiver {
  private final Target logger = new SharedLogger(MessageReceiver.class);
  private CommunicationBus communicationBus;
  private Message lastMessage;

  /**
   * Creates instance of MessageReceiver witch accepts client messages.
   *
   * @param communicationBus client server communication bus.
   */
  public MessageReceiver(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  /**
   * Receives message from given player.
   *
   * @param wrappedClient current playing player.
   */
  public void receive(final WrappedClient wrappedClient) {
    lastMessage = communicationBus.receive(wrappedClient);
    logger.info(lastMessage);
  }

  /**
   * Checks if last received message was a shot.
   *
   * @return true if last message has header equals to SHOT.
   */
  public boolean isAShot() {
    return Header.SHOT.equals(lastMessage.getHeader());
  }

  /**
   * Return last received message.
   *
   * @return message from player.
   */
  public Message getMessage() {
    return lastMessage;
  }

  /**
   * Checks if last received message was a shot.
   *
   * @return true if last message has header equals to RANDOM_PLACEMENT.
   */
  public boolean isRandomPlacement() {
    return Header.RANDOM_PLACEMENT.equals(lastMessage.getHeader());
  }
}
