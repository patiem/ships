package pl.korotkevics.ships.client.gui.controllers;

import pl.korotkevics.ships.client.gui.util.FieldState;
import pl.korotkevics.ships.client.gui.util.ShipOrientation;

import java.util.HashSet;
import java.util.Set;

public class ShipDestroyer {
  private final FieldState[] board;
  private static final int BOARD_SIZE = 10;
  private final int lastHitIndex;
  private Set<Integer> indexexToSetEmpty = new HashSet<>();
  private ShipOrientation shipOrientation;

  public ShipDestroyer(FieldState[] board, int lastHitIndex) {
    this.board = board;
    this.lastHitIndex = lastHitIndex;
  }

  public Set<Integer> getIndexToColor() {
    final int maxMastCount = 4;

    if(isOneMastShip()) {
      return indexexToSetEmpty;
    }

    return indexexToSetEmpty;
  }

  private boolean isOneMastShip() {

    final int startCheckIndex = lastHitIndex % 10 == 0 ? lastHitIndex : lastHitIndex - 1;
    final int endCheckIndex = lastHitIndex % 9 == 0 ? lastHitIndex : lastHitIndex + 1;

    for(int i = startCheckIndex; i <= endCheckIndex; i++) {

      //check only right/left
      if(i - BOARD_SIZE > 0 ) {
        if(board[i - BOARD_SIZE].equals(FieldState.EMPTY)) {
          indexexToSetEmpty.add(i - BOARD_SIZE);
        } else {
          shipOrientation = ShipOrientation.HORIZONTAL;
          return false;
        }
      }

      if(i + BOARD_SIZE < board.length ) {
        if(board[i + BOARD_SIZE].equals(FieldState.EMPTY)) {
          indexexToSetEmpty.add(i + BOARD_SIZE);
        } else {
          shipOrientation = ShipOrientation.HORIZONTAL;
          return false;
        }
      }

      if(i != lastHitIndex) {
        if(board[i].equals(FieldState.EMPTY)) {
          indexexToSetEmpty.add(i);
        } else {
          shipOrientation = ShipOrientation.VERTICAL;
          return false;
        }
      }
    }

    return true;
  }
}
