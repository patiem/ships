package pl.korotkevics.ships.client.validators;

import pl.korotkevics.ships.client.gui.util.FieldState;
import pl.korotkevics.ships.client.gui.util.ShipOrientation;
import lombok.RequiredArgsConstructor;

import java.util.stream.IntStream;

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
    return !isOutOfRange()
        && (shipOrientation.equals(ShipOrientation.HORIZONTAL)
        ? checkForHorizontal()
        : checkForVertical());
  }

  private boolean checkIfBreakingThroughBorders() {
    int startIndexInColumn = shipStartIndex % BOARD_SIZE;
    return startIndexInColumn + (mastCount - 1) >= BOARD_SIZE;
  }

  private boolean checkForVertical() {
    return !checkIfBreakingThroughBorders()
        && !isShipOnShip()
        && !isShipTouchOtherShip();
  }

  private boolean isOutOfRange() {
    return shipOrientation.equals(ShipOrientation.VERTICAL)
        ? shipStartIndex + mastCount > board.length
        : shipStartIndex + (mastCount - 1) * BOARD_SIZE >= board.length;
  }

  private boolean isShipOnShip() {
    return IntStream
        .rangeClosed(shipStartIndex, shipStartIndex + mastCount - 1)
        .anyMatch(p -> board[p].equals(FieldState.OCCUPIED));
  }

  private boolean isShipTouchOtherShip() {
    return isShipAboveShip()
        || isShipBelowShip()
        || isShipOnTheRight()
        || isShipOnTheLeft();
  }

  private boolean isShipAboveShip() {
    int checkWidthInCell = 3;
    return !isInLastRow()
        && IntStream.iterate(shipStartIndex - BOARD_SIZE , i -> i + BOARD_SIZE)
        .limit(checkWidthInCell)
        .filter(i -> i > 0)
        .filter(i -> i + mastCount < board.length)
        .anyMatch(i -> board[i + mastCount].equals(FieldState.OCCUPIED));
  }

  private boolean isInLastRow() {
    int lastRowModulo = 9;
    return (shipStartIndex + (mastCount - 1)) % BOARD_SIZE == lastRowModulo;
  }

  private boolean isShipBelowShip() {
    int checkWidthInCell = 3;
    return !isInFirstRow() && shipStartIndex - 1 > 0
        && IntStream.iterate(shipStartIndex - BOARD_SIZE , i -> i + BOARD_SIZE)
        .limit(checkWidthInCell)
        .filter(i -> i - 1 > 0)
        .filter(i -> i < board.length)
        .anyMatch(i -> board[i - 1].equals(FieldState.OCCUPIED));
  }

  private boolean isInFirstRow() {
    return shipStartIndex % BOARD_SIZE == 0;
  }

  private boolean isShipOnTheRight() {
    return IntStream.range(shipStartIndex, shipStartIndex + mastCount)
        .filter(value -> value - BOARD_SIZE > 0)
        .anyMatch(value -> board[value - BOARD_SIZE].equals(FieldState.OCCUPIED));
  }

  private boolean isShipOnTheLeft() {
    return IntStream.range(shipStartIndex, shipStartIndex + mastCount)
        .filter(value -> value > 0)
        .filter(value -> value + BOARD_SIZE < board.length)
        .anyMatch(value -> board[value + BOARD_SIZE].equals(FieldState.OCCUPIED));
  }

  private boolean checkForHorizontal() {
    return !isShipOnShipHorizontal()
        && !isShipTouchOtherShipHorizontal();
  }

  private boolean isShipTouchOtherShipHorizontal() {
    return isShipBelowShipHorizontal()
        || isShipAboveShipHorizontal()
        || isShipOnTheRightHorizontal()
        || isShipOnTheLeftHorizontal();
  }

  private boolean isShipOnShipHorizontal() {
    return IntStream.iterate(shipStartIndex, n -> n + BOARD_SIZE)
        .limit((mastCount - 1))
        .anyMatch(n -> board[n].equals(FieldState.OCCUPIED));
  }

  private boolean isShipBelowShipHorizontal() {
    return shipStartIndex % BOARD_SIZE != 9
        && IntStream.iterate(shipStartIndex - BOARD_SIZE, n -> n + BOARD_SIZE)
        .limit(mastCount + 2L)
        .filter(n -> n > 0)
        .filter(n -> n + 1 < board.length)
        .anyMatch(n -> board[n + 1].equals(FieldState.OCCUPIED));
  }

  private boolean isShipAboveShipHorizontal() {
    return !isInFirstRow()
        && IntStream.iterate(shipStartIndex - BOARD_SIZE, n -> n + BOARD_SIZE)
        .limit(mastCount + 2L)
        .filter(n -> n > 0)
        .filter(n -> n < board.length)
        .anyMatch(n -> board[n - 1].equals(FieldState.OCCUPIED));
  }

  private boolean isShipOnTheRightHorizontal() {
    return shipStartIndex + mastCount * BOARD_SIZE < board.length
        && board[shipStartIndex + mastCount * BOARD_SIZE].equals(FieldState.OCCUPIED);
  }

  private boolean isShipOnTheLeftHorizontal() {
    return shipStartIndex - BOARD_SIZE >= 1 && board[shipStartIndex - BOARD_SIZE].equals(FieldState.OCCUPIED);
  }

}
