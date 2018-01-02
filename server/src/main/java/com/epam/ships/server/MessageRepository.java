package com.epam.ships.server;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;

import java.util.EnumMap;

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

  public Message getMessage(Header header) {
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
