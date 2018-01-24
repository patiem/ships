package pl.korotkevics.ships.client.gui.controllers;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.korotkevics.ships.client.gui.util.FieldState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class SunkenShipMarkerTest {
  private static final int BOARD_SIZE = 10;

  @DataProvider
  static Object[][] data() {
    return new Object[][] {
        {3, produceBoard(Arrays.asList(3,4,5)), new HashSet<Integer>(Arrays.asList(2,6,12,13,14,15,16))},
        {3, produceBoard(Arrays.asList(3,13,23)), new HashSet<Integer>(Arrays.asList(2,12,22,32,33,34,4,14,24))}
    };
  }

  private static FieldState[] produceBoard(final List<Integer> occupiedPlaces) {
    FieldState[] board = new FieldState[BOARD_SIZE * BOARD_SIZE];
    for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
      if (occupiedPlaces.contains(Integer.valueOf(i))) {
        board[i] = FieldState.HIT;
      } else {
        board[i] = FieldState.EMPTY;
      }
    }

    return board;
  }

  @Test(dataProvider = "data")
  public void checkIfMarkedProperIndexes(int lastHitIndex, FieldState[] board, Set<Integer> properIndices) {
    SunkenShipMarker sunkenShipMarker = new SunkenShipMarker(board, lastHitIndex);

    Set<Integer> testIndices = sunkenShipMarker.getIndexToColor();

    assertTrue(testIndices.equals(properIndices));
  }

}