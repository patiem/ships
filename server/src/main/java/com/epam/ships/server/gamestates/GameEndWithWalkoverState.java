package com.epam.ships.server.gamestates;

import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;

public class GameEndWithWalkoverState implements GameState {
  public GameEndWithWalkoverState(CommunicationBus communicationBus, TurnManager turnManager) {
  }

  @Override
  public GameState process() {
    //TODO
    return null;
  }
}
