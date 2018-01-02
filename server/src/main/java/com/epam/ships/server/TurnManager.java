package com.epam.ships.server;

public class TurnManager {
  private final WrappedClient firstPlayer;
  private final WrappedClient secondPlayer;
  private WrappedClient currentPlayer;
  private WrappedClient otherPlayer;

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
