package com.epam.ships.server.gamestates;

import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;

public class EndGameState implements GameState {
  private CommunicationBus communicationBus;
  private TurnManager turnManager;

  public EndGameState(CommunicationBus communicationBus, TurnManager turnManager) {
    this.communicationBus = communicationBus;

    this.turnManager = turnManager;
  }

  @Override
  public GameState process() {
    handleEndOfGame();
    communicationBus.stop();
    return null;
  }

  private void handleEndOfGame() {
    this.sendWonMessage();
    this.sendLostMessage();
  }

  private void sendWonMessage() {
    final Message won = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.WIN)
        .build();
    this.communicationBus.send(turnManager.getCurrentPlayer(), won);
  }

  private void sendLostMessage() {
    final Message lost = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.LOSE)
        .build();
    this.communicationBus.send(turnManager.getOtherPlayer(), lost);
  }
}
