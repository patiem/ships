package com.epam.ships.server;

/**
 * Take care of players turn.
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
   * Creates TurnManager instance.
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
   * Gets player which has a turn.
   *
   * @return WrappedClient - current player
   */
  public WrappedClient getCurrentPlayer() {
    return this.currentPlayer;
  }

  /**
   * Gets player which is waiting for his turn.
   *
   * @return WrappedClient - player waiting for his turn.
   */
  public WrappedClient getOtherPlayer() {
    return this.otherPlayer;
  }

  /**
   * It's changes players turn.
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
