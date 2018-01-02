package com.epam.ships.server;

import com.epam.ships.server.gamestates.GameState;

public class Game {
  private GameState currentState;

  Game(GameState initialState) {
    currentState = initialState;
  }

  void loop() {
    boolean shouldBeContinued = true;
    GameState nextState;
    while (shouldBeContinued) {
      nextState = currentState.process();
      shouldBeContinued = currentState.shouldBeContinued();

      currentState = nextState;
    }
  }

}
