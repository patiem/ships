package pl.korotkevics.ships.client.validators;

import pl.korotkevics.ships.client.gui.util.FieldState;
import pl.korotkevics.ships.client.gui.util.ShipOrientation;
import lombok.RequiredArgsConstructor;

/**
 * Enable validation of ships placement.
 * @author Magdalena Aarsman
 * @since 2017-12-31
 */
@RequiredArgsConstructor
public class ShipPlacementValidator {
  private static final int BOARD_SIZE = 10;

  private final ShipOrientation shipOrientation;
  private final int shipStartIndex;
  private final int mastCount;
  private final FieldState[] board;

  /**
   * Check if ship placement is valid.
   * @return true if it is valid, false if not.
   */
  public boolean isPlacementValid() {
    if (shipOrientation.equals(ShipOrientation.HORIZONTAL)) {
      return checkForHorizontal();
    } else {
      return checkForVertical();
    }
  }

  private boolean checkIfNotPassingToOtherSide(final int index, final int mastCount) {
    int startIndexMod = index % BOARD_SIZE;
    for (int i = 1; i < mastCount; i++) {
      if ((index + i) % BOARD_SIZE < startIndexMod) {
        return false;
      }
    }
    return true;
  }

  private boolean checkForVertical() {
    return isNotShipOnShip(shipStartIndex, mastCount)
        && isNotShipAboveShip(shipStartIndex, mastCount)
        && isNotShipBelowShip(shipStartIndex)
        && isNotShipOnTheRight(shipStartIndex, mastCount)
        && isNotShipOnTheLeft(shipStartIndex, mastCount)
        && checkIfNotPassingToOtherSide(shipStartIndex, mastCount);
  }

  private boolean isNotShipOnShip(final int index, final int mastCount) {
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

  private boolean isNotShipAboveShip(final int index, final int mastCount) {
    return !((index + mastCount < board.length)
        && (board[index + mastCount].equals(FieldState.OCCUPIED)));
  }

  private boolean isNotShipBelowShip(final int index) {
    return !(index > 1 && index % BOARD_SIZE != 0
        && (board[index - 1].equals(FieldState.OCCUPIED)));
  }

  private boolean isNotShipOnTheRight(final int index, final int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if (index + i - BOARD_SIZE > 0
          && (board[index + i - BOARD_SIZE].equals(FieldState.OCCUPIED))) {
        return false;
      }
    }
    return true;
  }

  private boolean isNotShipOnTheLeft(final int index, final int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if (index + i + BOARD_SIZE < board.length
          && (board[index + i + BOARD_SIZE].equals(FieldState.OCCUPIED))) {
        return false;
      }
    }
    return true;
  }

  private boolean checkForHorizontal() {
    return isNotShipOnShipHorizontal(shipStartIndex, mastCount)
        && isNotShipBelowShipHorizontal(shipStartIndex, mastCount)
        && isNotShipAboveShipHorizontal(shipStartIndex, mastCount)
        && isNotShipOnTheRightHorizontal(shipStartIndex, mastCount)
        && isNotShipOnTheLeftHorizontal(shipStartIndex);
  }

  private boolean isNotShipOnShipHorizontal(final int index, final int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if (board[index + i * BOARD_SIZE].equals(FieldState.OCCUPIED)) {
        return false;
      }
    }
    return true;
  }

  private boolean isNotShipBelowShipHorizontal(final int index, final int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if ((index + i * BOARD_SIZE) % BOARD_SIZE == 9) {
        return true;
      }
      final int oneRowBelow = 1;
      if (index + oneRowBelow + i * BOARD_SIZE >= board.length) {
        return true;
      }

      if (board[index + oneRowBelow + i * BOARD_SIZE].equals(FieldState.OCCUPIED)) {
        return false;
      }
    }
    return true;
  }

  private boolean isNotShipAboveShipHorizontal(final int index, final int mastCount) {
    for (int i = 0; i < mastCount; i++) {
      if ((index + i * BOARD_SIZE) % BOARD_SIZE == 0) {
        return true;
      }
      if (index + i * BOARD_SIZE >= board.length || index + i * BOARD_SIZE < 1) {
        return true;
      }

      if (board[index - 1 + i * BOARD_SIZE].equals(FieldState.OCCUPIED)) {
        return false;
      }
    }

    return true;
  }

  private boolean isNotShipOnTheRightHorizontal(final int index, final int mastCount) {
    if (index + mastCount * BOARD_SIZE >= board.length) {
      return true;
    }

    if (board[index + mastCount * BOARD_SIZE].equals(FieldState.OCCUPIED)) {
      return false;
    }

    return true;
  }

  private boolean isNotShipOnTheLeftHorizontal(final int index) {
    if (index - BOARD_SIZE < 1) {
      return true;
    }

    if (board[index - BOARD_SIZE].equals(FieldState.OCCUPIED)) {
      return false;
    }

    return true;
  }

}
