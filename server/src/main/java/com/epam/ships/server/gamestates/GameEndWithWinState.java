package com.epam.ships.server.gamestates;

import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

public class GameEndWithWinState implements GameState {
  private final MessageSender messageSender;
  private final Target logger = new SharedLogger(GameEndWithWinState.class);
  private CommunicationBus communicationBus;
  private TurnManager turnManager;

  public GameEndWithWinState(CommunicationBus communicationBus, TurnManager turnManager) {
    this.communicationBus = communicationBus;
    this.turnManager = turnManager;
    messageSender = new MessageSender(communicationBus, logger);
  }

  @Override
  public GameState process() {
    handleEndOfGame();
    communicationBus.stop();
    logger.info("Game ended");
    return this;
  }

  @Override
  public boolean shouldBeContinued() {
    return false;
  }

  private void handleEndOfGame() {
    messageSender.send(turnManager.getCurrentPlayer(), Header.WIN);
    messageSender.send(turnManager.getOtherPlayer(), Header.LOSE);
  }
}
