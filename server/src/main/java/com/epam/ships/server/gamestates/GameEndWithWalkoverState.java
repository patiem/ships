package com.epam.ships.server.gamestates;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;

public class GameEndWithWalkoverState implements GameState {

  private final Target logger = new SharedLogger(GameEndWithWalkoverState.class);
  public GameEndWithWalkoverState(CommunicationBus communicationBus, TurnManager turnManager) {
  }

  @Override
  public GameState process() {
    //TODO
    return null;
  }
}
