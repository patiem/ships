package com.epam.ships.client.validators;

import com.epam.ships.client.gui.util.FieldState;
import com.epam.ships.client.gui.util.ShipOrientation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShipPlacementValidator {
  private final static int BOARD_SIZE = 10;

  private final ShipOrientation shipOrientation;
  private final int shipStartIndex;
  private final int mastCount;
  private final FieldState[] board;

  public boolean isPlacemntValid() {
    if(shipOrientation.equals(ShipOrientation.HORIZONTAL)) {
      return checkForHorizontal();
    } else {
      return checkForVertical();
    }
  }

  private boolean checkForVertical() {
    return isNotShipOnShip(shipStartIndex, mastCount)
        && isNotShipAboveShip(shipStartIndex, mastCount)
        && isNotShipBelowShip(shipStartIndex)
        && isNotShipOnTheRight(shipStartIndex, mastCount)
        && isNotShipOnTheLeft(shipStartIndex, mastCount);
  }

  private boolean isNotShipOnShip(int index, int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if (index + i >= board.length) {
        return false;
      }

      if (board[index + i].equals(FieldState.OCCUPIED)) {
        return false;
      }
    }
    return true;
  }

  private boolean isNotShipAboveShip(int index, int mastCount) {
    return !((index + mastCount < board.length)
        && (board[index + mastCount].equals(FieldState.OCCUPIED)));
  }

  private boolean isNotShipBelowShip(int index) {
    if (index > 1 && index % BOARD_SIZE != 0) {
      if (board[index -1].equals(FieldState.OCCUPIED)) {
        return false;
      }
    }

    return true;
  }

  private boolean isNotShipOnTheRight(int index, int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if (index + i - BOARD_SIZE > 0) {
        if (board[index + i - BOARD_SIZE].equals(FieldState.OCCUPIED)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isNotShipOnTheLeft(int index, int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if (index + i + BOARD_SIZE < board.length) {
        if (board[index + i + BOARD_SIZE].equals(FieldState.OCCUPIED)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkForHorizontal() {
    return true;
  }
}
