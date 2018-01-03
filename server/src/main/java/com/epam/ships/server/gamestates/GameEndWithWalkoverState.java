package com.epam.ships.server.gamestates;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;

/**
 * Represents game state where one of players quit in the middle of game.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class GameEndWithWalkoverState implements GameState {
  private final Target logger = new SharedLogger(GameEndWithWalkoverState.class);
  private final CommunicationBus communicationBus;

  /**
   * It creates state in which there is a walk-over.
   *
   * @param communicationBus client server communication bus
   */
  public GameEndWithWalkoverState(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  /**
   * Process game end with walk-over.
   *
   * @return GameState - returns itself, but this will not be processed any more because of
   * @see this.shouldBeContinued().
   */
  @Override
  public GameState process() {
    logger.info("Game ends - walk-over");
    communicationBus.stop();
    return this;
  }

  /**
   * Set that game should not by continued since there is no opponent.
   *
   * @return false - game can not be continued because there is no opponent.
   */
  @Override
  public boolean shouldBeContinued() {
    return false;
  }
}
