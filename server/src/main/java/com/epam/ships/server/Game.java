package com.epam.ships.server;

import com.epam.ships.server.gamestates.GameState;

/**
 * Represents Game with states.
 *
 * @author Piotr Czyż
 * @since 02.01.2018
 */
public class Game {
  private GameState currentState;

  Game(GameState gameState) {
    currentState = gameState;
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
