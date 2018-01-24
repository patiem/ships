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
    if(this.isOneMastShip()) {
      return this.indexexToSetEmpty;
    }

    if(this.shipOrientation.equals(ShipOrientation.VERTICAL)) {
      this.sinkVertical();
    } else {
      this.sinkHorizontal();
    }

    return this.indexexToSetEmpty;
  }

  private void sinkHorizontal() {
    //while ()
  }

  private void sinkVertical() {
    //check down
    int index = this.lastHitIndex;
    while ((index < this.board.length )
    && index % BOARD_SIZE != 9 && this.board[index].equals(FieldState.HIT)) {

      if(index - BOARD_SIZE > 0) {
        this.indexexToSetEmpty.add(index - BOARD_SIZE);
      }

      if(index + BOARD_SIZE > 0) {
        this.indexexToSetEmpty.add(index + BOARD_SIZE);
      }
      index++;
    }

    if(index < this.board.length && this.board[index].equals(FieldState.EMPTY)) {
      this.indexexToSetEmpty.add(index);
    }

    if(index - BOARD_SIZE > 0) {
      this.indexexToSetEmpty.add(index - BOARD_SIZE);
    }

    if(index + BOARD_SIZE < this.board.length) {
      this.indexexToSetEmpty.add(index + BOARD_SIZE);
    }

    //check up
    index = this.lastHitIndex;
    while ((index > 0 )
    && index % BOARD_SIZE != 0 && this.board[index].equals(FieldState.HIT)) {
      if(index - BOARD_SIZE >= 0) {
        this.indexexToSetEmpty.add(index - BOARD_SIZE);
      }

      if(index + BOARD_SIZE < board.length) {
        this.indexexToSetEmpty.add(index + BOARD_SIZE);
      }
      index--;
    }
    if(index > 0 && this.board[index].equals(FieldState.EMPTY)) {
      this.indexexToSetEmpty.add(index);
    }

    if(index - BOARD_SIZE >= 0) {
      this.indexexToSetEmpty.add(index - BOARD_SIZE);
    }

    if(index + BOARD_SIZE < this.board.length) {
      this.indexexToSetEmpty.add(index + BOARD_SIZE);
    }
  }

  private boolean isOneMastShip() {

    final int startCheckIndex = lastHitIndex % BOARD_SIZE == 0 ? lastHitIndex : lastHitIndex - 1;
    final int endCheckIndex = lastHitIndex % BOARD_SIZE == 9 ? lastHitIndex : lastHitIndex + 1;

    for(int i = startCheckIndex; i <= endCheckIndex; i++) {

      //check only right/left
      if(i - BOARD_SIZE >= 0 ) {
        if(this.board[i - BOARD_SIZE].equals(FieldState.EMPTY)) {
          this.indexexToSetEmpty.add(i - BOARD_SIZE);
        } else {
          this.shipOrientation = ShipOrientation.HORIZONTAL;
          return false;
        }
      }

      if(i + BOARD_SIZE < this.board.length ) {
        if(this.board[i + BOARD_SIZE].equals(FieldState.EMPTY)) {
          this.indexexToSetEmpty.add(i + BOARD_SIZE);
        } else {
          this.shipOrientation = ShipOrientation.HORIZONTAL;
          return false;
        }
      }

      if(i != this.lastHitIndex) {
        if(this.board[i].equals(FieldState.EMPTY)) {
          this.indexexToSetEmpty.add(i);
        } else {
          this.shipOrientation = ShipOrientation.VERTICAL;
          return false;
        }
      }
    }

    return true;
  }
}
