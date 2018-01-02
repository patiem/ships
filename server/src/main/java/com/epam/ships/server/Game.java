package com.epam.ships.server;

import com.epam.ships.server.gamestates.GameState;

public class Game {
  private GameState currentState;

  public Game(GameState initialState) {
    currentState = initialState;
  }

  public void loop() {
    boolean shouldBeContinued = true;
    while (shouldBeContinued) {
      currentState = currentState.process();
      shouldBeContinued = currentState.shouldBeContinued();
    }
  }

}
