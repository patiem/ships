package com.epam.ships.server;

class TurnManager {
  private final WrappedClient firstPlayer;
  private final WrappedClient secondPlayer;
  private WrappedClient currentPlayer;
  private WrappedClient otherPlayer;

  TurnManager(final WrappedClient firstPlayer, final WrappedClient secondPlayer) {
    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
    this.currentPlayer = firstPlayer;
    this.otherPlayer = secondPlayer;
  }

  final WrappedClient getCurrentPlayer() {
    return this.currentPlayer;
  }

  final WrappedClient getOtherPlayer() {
    return this.otherPlayer;
  }

  void switchPlayer() {
    if (this.currentPlayer.equals(this.firstPlayer)) {
      this.currentPlayer = this.secondPlayer;
      this.otherPlayer = this.firstPlayer;
    } else {
      this.currentPlayer = this.firstPlayer;
      this.otherPlayer = secondPlayer;
    }
  }

  boolean isCurrentPlayerFirstPlayer() {
    return currentPlayer.equals(firstPlayer);
  }
}
