package pl.korotkevics.ships.server.gamestates.endgame;

import pl.korotkevics.ships.server.gamestates.GameState;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import pl.korotkevics.ships.server.CommunicationBus;

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
   * @return return a state in which game will be discontinued
   * @see #shouldBeContinued()
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
   * @return false when game can not be continued because there is no opponent.
   */
  @Override
  public boolean shouldBeContinued() {
    return false;
  }
}
