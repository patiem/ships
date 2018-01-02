package com.epam.ships.server.gamestates;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.CommunicationBus;

import java.io.IOException;

public class WaitingForPlayersState implements GameState {

  @Override
  public GameState process() {
    CommunicationBus communicationBus = null;
    try {
      communicationBus = new CommunicationBus();
      communicationBus.start();
    } catch (IOException e) {
      e.printStackTrace();
    }


    final Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.OPPONENT_CONNECTED)
        .withStatement("true")
        .build();

    communicationBus.sendToAll(message);
    return new FleetPlacementState(communicationBus);
  }
}
