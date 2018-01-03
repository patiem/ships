package com.epam.ships.server.gamestates;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;

public class GameEndWithWalkoverState implements GameState {
  private final Target logger = new SharedLogger(GameEndWithWalkoverState.class);
  private final CommunicationBus communicationBus;

  public GameEndWithWalkoverState(CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  @Override
  public GameState process() {
    logger.info("Game ends - walk-over");
    communicationBus.stop();
    return this;
  }

  @Override
  public boolean shouldBeContinued() {
    return false;
  }
}
