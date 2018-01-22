package pl.korotkevics.ships.server;

/**
 * Changes players' turns.
 *
 * @author Piotr Czyż
 * @since 2017-12-20
 */
public class TurnManager {
  private final WrappedClient firstPlayer;
  private final WrappedClient secondPlayer;
  private WrappedClient currentPlayer;
  private WrappedClient otherPlayer;

  /**
   * Creates TurnManager instance, and sets firstly connected client as player who starts the game.
   *
   * @param firstPlayer  player who starts the game
   * @param secondPlayer second player in the game
   */
  public TurnManager(final WrappedClient firstPlayer, final WrappedClient secondPlayer) {
    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
    this.currentPlayer = firstPlayer;
    this.otherPlayer = secondPlayer;
  }

  /**
   * Returns a player having the turn.
   *
   * @return WrappedClient - currentPlayer
   */
  public WrappedClient getCurrentPlayer() {
    return this.currentPlayer;
  }

  /**
   * Returns a player waiting for his turn.
   *
   * @return WrappedClient - player waiting for his turn.
   */
  public WrappedClient getOtherPlayer() {
    return this.otherPlayer;
  }

  /**
   * It changes players turn.
   */
  public void switchPlayer() {
    if (this.currentPlayer.equals(this.firstPlayer)) {
      this.currentPlayer = this.secondPlayer;
      this.otherPlayer = this.firstPlayer;
    } else {
      this.currentPlayer = this.firstPlayer;
      this.otherPlayer = secondPlayer;
    }
  }

  /**
   * Check if firstPlayer is current player.
   *
   * @return true if currentPlayer is firstPlayer
   */
  public boolean isCurrentPlayerFirstPlayer() {
    return currentPlayer.equals(firstPlayer);
  }
}
