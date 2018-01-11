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

  private boolean checkIfPassingToOtherSide(final int index, final int mastCount) {
    int startIndexMod = index % BOARD_SIZE;
    return (index + (mastCount - 1)) % BOARD_SIZE < startIndexMod;
  }

  private boolean checkForVertical() {
    return ! checkIfPassingToOtherSide(shipStartIndex, mastCount)
        && ! isShipOnShip(shipStartIndex, mastCount)
        && ! isShipTouchOtherShip();
  }

  private boolean isOutOfRange() {
    return shipOrientation.equals(ShipOrientation.VERTICAL)
        ? shipStartIndex + mastCount > board.length
        : shipStartIndex + (mastCount - 1) * BOARD_SIZE >= board.length;
  }

  private boolean isShipOnShip(final int index, final int mastCount) {
    return IntStream
        .rangeClosed(index, index + mastCount - 1)
        .anyMatch(p -> board[p].equals(FieldState.OCCUPIED));
  }

  private boolean isShipTouchOtherShip() {
    return isShipAboveShip(shipStartIndex, mastCount)
        || isShipBelowShip(shipStartIndex)
        || isShipOnTheRight(shipStartIndex, mastCount)
        || isShipOnTheLeft(shipStartIndex, mastCount);
  }

  private boolean isShipAboveShip(final int index, final int mastCount) {
    return (index ) % BOARD_SIZE != 9 &&
        index + mastCount < board.length
        && IntStream.iterate(index - BOARD_SIZE , i-> i + BOARD_SIZE)
        .limit(3)
        .filter(i-> i > 0)
        .filter(i-> i + mastCount < board.length)
        .anyMatch(i -> board[i + mastCount].equals(FieldState.OCCUPIED));
  }

  private boolean isShipBelowShip(final int index) {
    return index % BOARD_SIZE != 0 && index - 1 > 0
        && IntStream.iterate(index - BOARD_SIZE , i-> i + BOARD_SIZE)
        .limit(3)
        .filter(i-> i - 1 > 0)
        .filter(i-> i < board.length)
        .anyMatch(i -> board[i - 1].equals(FieldState.OCCUPIED));
  }

  private boolean isShipOnTheRight(final int index, final int mastCount) {
    return IntStream.range(index, index + mastCount)
        .filter(value -> value - BOARD_SIZE > 0)
        .anyMatch(value -> board[value - BOARD_SIZE].equals(FieldState.OCCUPIED));
  }

  private boolean isShipOnTheLeft(final int index, final int mastCount) {
    return IntStream.range(index, index + mastCount)
        .filter(value -> value > 0)
        .filter(value -> value + BOARD_SIZE < board.length)
        .anyMatch(value -> board[value + BOARD_SIZE].equals(FieldState.OCCUPIED));
  }

  private boolean checkForHorizontal() {
    return ! isShipOnShipHorizontal(shipStartIndex, mastCount)
        && ! isShipTouchOtherShipHorizontal();
  }

  private boolean isShipTouchOtherShipHorizontal() {
    return isShipBelowShipHorizontal(shipStartIndex, mastCount)
        || isShipAboveShipHorizontal(shipStartIndex, mastCount)
        || isShipOnTheRightHorizontal(shipStartIndex, mastCount)
        || isShipOnTheLeftHorizontal(shipStartIndex);
  }

  private boolean isShipOnShipHorizontal(final int index, final int mastCount) {
    return IntStream.iterate(index, n -> n + BOARD_SIZE)
        .limit((mastCount - 1))
        .anyMatch(n -> board[n].equals(FieldState.OCCUPIED));
  }

  private boolean isShipBelowShipHorizontal(final int index, final int mastCount) {
    return index % BOARD_SIZE != 9
        && IntStream.iterate(index - BOARD_SIZE, n -> n + BOARD_SIZE)
        .limit(mastCount + 2L)
        .filter(n -> n > 0)
        .filter(n -> n + 1 < board.length)
        .anyMatch(n -> board[n + 1].equals(FieldState.OCCUPIED));
  }

  private boolean isShipAboveShipHorizontal(final int index, final int mastCount) {
    return index % BOARD_SIZE != 0
        && IntStream.iterate(index - BOARD_SIZE, n -> n + BOARD_SIZE)
        .limit(mastCount + 2L)
        .filter(n -> n > 0)
        .filter(n -> n < board.length)
        .anyMatch(n -> board[n - 1].equals(FieldState.OCCUPIED));
  }

  private boolean isShipOnTheRightHorizontal(final int index, final int mastCount) {
    return index + mastCount * BOARD_SIZE < board.length
        && board[index + mastCount * BOARD_SIZE].equals(FieldState.OCCUPIED);
  }

  private boolean isShipOnTheLeftHorizontal(final int index) {
    return index - BOARD_SIZE >= 1 && board[index - BOARD_SIZE].equals(FieldState.OCCUPIED);
  }

}
