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

  public MessageReceiver(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  public void receive(final WrappedClient player) {
    lastMessage = communicationBus.receive(player);
    logger.info(lastMessage);
  }

  public boolean isAShot() {
    return Header.SHOT.equals(lastMessage.getHeader());
  }

  public Message getMessage() {
    return lastMessage;
  }

  public boolean isRandomPlacement() {
    return Header.RANDOM_PLACEMENT.equals(lastMessage.getHeader());
  }
}
