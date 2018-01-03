package com.epam.ships.server.gamestates;

/**
 * Interface for game states
 *
 * @author Piotr Czyż
 * @since 02.01.2018
 */
public interface GameState {

  GameState process();

  default boolean shouldBeContinued() {
    return true;
  }

}
