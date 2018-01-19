package pl.korotkevics.ships.server;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.BaseMessage;

import java.util.EnumMap;

/**
 * Repository of message that server can send.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class MessageRepository {

  private EnumMap<Header, Message> messages;

  MessageRepository() {
    this.messages = new EnumMap<>(Header.class);
    messages.put(Header.OPPONENT_CONNECTED, this.opponentConnectedMessage());
    messages.put(Header.WIN, this.winMessage());
    messages.put(Header.LOSE, this.loseMessage());
    messages.put(Header.MANUAL_PLACEMENT, this.askForFleetMessage());
    messages.put(Header.HIT, this.hitMessage());
    messages.put(Header.MISS, this.missMessage());
    messages.put(Header.SHIP_DESTROYED, this.shipDestroyedMessage());
    messages.put(Header.YOUR_TURN, this.yourTurnMessage());
  }

  /**
   * Gets message from repository based on message header.
   *
   * @param header header of wonted message
   * @return proper Message from MessageRepository
   */
  public Message getMessage(final Header header) {
    return messages.get(header);
  }

  private Message yourTurnMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.YOUR_TURN)
            .build();
  }

  private Message shipDestroyedMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.SHIP_DESTROYED)
            .build();
  }

  private Message missMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.MISS)
            .build();
  }

  private Message hitMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.HIT)
            .build();
  }

  private Message askForFleetMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.MANUAL_PLACEMENT)
            .build();
  }

  private Message loseMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.LOSE)
            .build();
  }

  private Message winMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.WIN)
            .build();
  }

  private Message opponentConnectedMessage() {
    return BaseMessage.builder().setAuthor(Author.SERVER)
            .setHeader(Header.OPPONENT_CONNECTED)
            .setStatement("true")
            .build();
  }
}
