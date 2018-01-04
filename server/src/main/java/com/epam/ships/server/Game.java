package com.epam.ships.server;

import com.epam.ships.server.gamestates.GameState;

/**
 * Represents Game with states.
 *
 * @author Piotr Czyż
 * @since 2017-12-13
 */
public class Game {
  private GameState currentState;

  Game(final GameState gameState) {
    currentState = gameState;
  }

  void play() {
    boolean shouldBeContinued = true;
    GameState nextState;
    while (shouldBeContinued) {
      nextState = currentState.process();
      shouldBeContinued = currentState.shouldBeContinued();
      currentState = nextState;
    }
  }

}
