package pl.korotkevics.ships.server.gamestates;

/**
 * Game state representation contract.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public interface GameState {
  
  /**
   * Process current game state.
   *
   * @return GameState
   */
  GameState process();

  /**
   * Indicates whether a game is to be continued.
   *
   * @return by default its returns true, to keep game going.
   */
  default boolean shouldBeContinued() {
    return true;
  }

}
