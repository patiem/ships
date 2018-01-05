package pl.korotkevics.ships.server.gamestates;

/**
 * Interface for game states.
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
   * Stays that game should or should not be continued.
   *
   * @return by default its returns true, to keep game going.
   */
  default boolean shouldBeContinued() {
    return true;
  }

}
