package pl.korotkevics.ships.client.gui.controllers;

import pl.korotkevics.ships.client.gui.util.FieldState;
import pl.korotkevics.ships.client.gui.util.ShipOrientation;

import java.util.HashSet;
import java.util.Set;

class SunkenShipMarker {
  private final FieldState[] board;
  private static final int BOARD_SIZE = 10;
  private final int lastHitIndex;
  private Set<Integer> indicesToSetEmpty = new HashSet<>();
  private ShipOrientation shipOrientation;

  SunkenShipMarker(final FieldState[] board, final int lastHitIndex) {
    this.board = board;
    this.lastHitIndex = lastHitIndex;
  }

  Set<Integer> getIndexToColor() {
    if(this.isOneMastShip()) {
      return this.indicesToSetEmpty;
    }

    if(this.shipOrientation.equals(ShipOrientation.VERTICAL)) {
      this.sinkVertical();
    } else {
      this.sinkHorizontal();
    }

    return this.indicesToSetEmpty;
  }

  private void sinkHorizontal() {
    int index = this.lastHitIndex;
    markAtRight(index);
    markAtLeft(index);
  }

  private void markAtLeft(int index) {
    while (index < board.length && this.board[index].equals(FieldState.HIT)) {
      if(index % BOARD_SIZE != 0 && index -1 >=0){
        this.indicesToSetEmpty.add(index - 1);
      }

      if(index % BOARD_SIZE != 9 && index + 1 < board.length) {
        this.indicesToSetEmpty.add(index +1);
      }

      index = index + BOARD_SIZE;
    }

    if(index % BOARD_SIZE != 0 && index -1 >=0 && index < board.length){
      this.indicesToSetEmpty.add(index - 1);
    }

    if(index % BOARD_SIZE != 9 && index + 1 < board.length) {
      this.indicesToSetEmpty.add(index +1);
    }

    if(index < board.length && this.board[index].equals(FieldState.EMPTY)) {
      this.indicesToSetEmpty.add(index);
    }
  }

  private void markAtRight(int index) {
    while (index >= 0 && this.board[index].equals(FieldState.HIT)) {
      if(index % BOARD_SIZE != 0 && index -1 >=0){
        this.indicesToSetEmpty.add(index - 1);
      }

      if(index % BOARD_SIZE != 9 && index + 1 < board.length) {
        this.indicesToSetEmpty.add(index +1);
      }

      index = index - BOARD_SIZE;
    }

    if(index % BOARD_SIZE != 0 && index -1 >=0){
      this.indicesToSetEmpty.add(index - 1);
    }

    if(index % BOARD_SIZE != 9 && index >= 0) {
      this.indicesToSetEmpty.add(index +1);
    }

    if(index >= 0 && this.board[index].equals(FieldState.EMPTY)) {
      this.indicesToSetEmpty.add(index);
    }
  }

  private void sinkVertical() {
    //check down
    int index = this.lastHitIndex;
    markBelow(index);
    markAbove(index);
  }

  private void markAbove(int index) {
    while ((index > 0 )
    && index % BOARD_SIZE != 0 && this.board[index].equals(FieldState.HIT)) {
      if(index - BOARD_SIZE >= 0) {
        this.indicesToSetEmpty.add(index - BOARD_SIZE);
      }

      if(index + BOARD_SIZE < board.length) {
        this.indicesToSetEmpty.add(index + BOARD_SIZE);
      }
      index--;
    }
    if(index > 0 && this.board[index].equals(FieldState.EMPTY)) {
      this.indicesToSetEmpty.add(index);
    }

    if(index - BOARD_SIZE >= 0) {
      this.indicesToSetEmpty.add(index - BOARD_SIZE);
    }

    if(index + BOARD_SIZE < this.board.length) {
      this.indicesToSetEmpty.add(index + BOARD_SIZE);
    }
  }

  private void markBelow(int index) {
    while ((index < this.board.length )
    && index % BOARD_SIZE != 9 && this.board[index].equals(FieldState.HIT)) {

      if(index - BOARD_SIZE >= 0) {
        this.indicesToSetEmpty.add(index - BOARD_SIZE);
      }

      if(index + BOARD_SIZE < board.length) {
        this.indicesToSetEmpty.add(index + BOARD_SIZE);
      }
      index++;
    }

    if(index < this.board.length && this.board[index].equals(FieldState.EMPTY)) {
      this.indicesToSetEmpty.add(index);
    }

    if(index - BOARD_SIZE >= 0) {
      this.indicesToSetEmpty.add(index - BOARD_SIZE);
    }

    if(index + BOARD_SIZE < this.board.length) {
      this.indicesToSetEmpty.add(index + BOARD_SIZE);
    }
  }

  private boolean isOneMastShip() {

    final int startCheckIndex = lastHitIndex % BOARD_SIZE == 0 ? lastHitIndex : lastHitIndex - 1;
    final int endCheckIndex = lastHitIndex % BOARD_SIZE == 9 ? lastHitIndex : lastHitIndex + 1;

    for(int i = startCheckIndex; i <= endCheckIndex; i++) {
      if (checkLeftNeighbor(i)) {
        return false;
      }

      if (checkRightNeighbor(i)) {
        return false;
      }

      if (checkMiddleOne(i)) {
        return false;
      }
    }
    return true;
  }

  private boolean checkMiddleOne(final int i) {
    if(i != this.lastHitIndex) {
      if(this.board[i].equals(FieldState.EMPTY)) {
        this.indicesToSetEmpty.add(i);
      } else {
        this.shipOrientation = ShipOrientation.VERTICAL;
        return true;
      }
    }
    return false;
  }

  private boolean checkRightNeighbor(final int i) {
    if(i + BOARD_SIZE < this.board.length ) {
      if(this.board[i + BOARD_SIZE].equals(FieldState.EMPTY)) {
        this.indicesToSetEmpty.add(i + BOARD_SIZE);
      } else {
        this.shipOrientation = ShipOrientation.HORIZONTAL;
        return true;
      }
    }
    return false;
  }

  private boolean checkLeftNeighbor(final int i) {
    if(i - BOARD_SIZE >= 0 ) {
      if(this.board[i - BOARD_SIZE].equals(FieldState.EMPTY)) {
        this.indicesToSetEmpty.add(i - BOARD_SIZE);
      } else {
        this.shipOrientation = ShipOrientation.HORIZONTAL;
        return true;
      }
    }
    return false;
  }
}
