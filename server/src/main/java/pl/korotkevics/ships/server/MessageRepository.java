package pl.korotkevics.ships.server;

import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

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
    messages.put(Header.PLACEMENT, this.askForFleetMessage());
    messages.put(Header.HIT, this.hitMessage());
    messages.put(Header.MISS, this.missMessage());
    messages.put(Header.SHIP_DESTRUCTED, this.shipDestructedMessage());
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
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.YOUR_TURN)
        .build();
  }

  private Message shipDestructedMessage() {
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.SHIP_DESTRUCTED)
        .build();
  }

  private Message missMessage() {
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.MISS)
        .build();
  }

  private Message hitMessage() {
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.HIT)
        .build();
  }

  private Message askForFleetMessage() {
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.PLACEMENT)
        .build();
  }

  private Message loseMessage() {
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.LOSE)
        .build();
  }

  private Message winMessage() {
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.WIN)
        .build();
  }

  private Message opponentConnectedMessage() {
    return new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.OPPONENT_CONNECTED)
        .withStatement("true")
        .build();
  }
}
