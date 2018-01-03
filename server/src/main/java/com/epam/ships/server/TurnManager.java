package com.epam.ships.server;

/**
 * Take care of players turn.
 *
 * @author Piotr Czyż
 * @since 20.12.2017
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

  public WrappedClient getCurrentPlayer() {
    return this.currentPlayer;
  }

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

  public boolean isCurrentPlayerFirstPlayer() {
    return currentPlayer.equals(firstPlayer);
  }
}
