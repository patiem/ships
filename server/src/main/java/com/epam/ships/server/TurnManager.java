package com.epam.ships.server;

class TurnManager {
    private final WrappedClient firstPlayer;
    private final WrappedClient secondPlayer;
    private WrappedClient currentPlayer;

    TurnManager(final WrappedClient firstPlayer,final WrappedClient secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentPlayer = firstPlayer;
    }

    final WrappedClient getCurrentPlayer() {
        return this.currentPlayer;
    }

    void switchPlayer() {
        if (this.currentPlayer.equals(this.firstPlayer)) {
            this.currentPlayer = this.secondPlayer;
        } else {
            this.currentPlayer = this.firstPlayer;
        }
    }
}
