package com.epam.ships.server;

class TurnManager {
    private final WrappedClient firstPlayer;
    private final WrappedClient secondPlayer;
    private WrappedClient currentPlayer;

    TurnManager(WrappedClient firstPlayer, WrappedClient secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        currentPlayer = firstPlayer;
    }

    public WrappedClient getCurrentPlayer() {
        return currentPlayer;
    }

    void switchPlayer(){
        if(currentPlayer.equals(firstPlayer)){
            currentPlayer = secondPlayer;
        }else {
            currentPlayer = firstPlayer;
        }
    }
}
