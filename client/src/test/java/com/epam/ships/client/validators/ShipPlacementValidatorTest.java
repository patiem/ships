package com.epam.ships.client.validators;

import com.epam.ships.client.gui.util.FieldState;
import com.epam.ships.client.gui.util.ShipOrientation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class ShipPlacementValidatorTest {

  private static final int BOARD_SIZE = 10;

  @DataProvider(name = "validPlacement")
  public static Object [][] validData() {
    return new Object[][] {
        {ShipOrientation.VERTICAL, 0, 3, producedBoard(Arrays.asList(99,81))}
    };
  }

  @DataProvider(name = "invalidPlacement")
  public static Object [][] invalidData() {
    return new Object[][] {
        {ShipOrientation.HORIZONTAL, 0, 3, producedBoard(Arrays.asList(0,1,2,3))}
    };
  }


  @Test(dataProvider = "validPlacement")
  public void validPlacement(ShipOrientation shipOrientation, int shipStartIndex, int mastCount,
                             FieldState[] board) {
    //given
    ShipPlacementValidator shipPlacementValidator =
        new ShipPlacementValidator(shipOrientation, shipStartIndex, mastCount, board);
    //when
    boolean isValid = shipPlacementValidator.isPlacementValid();
    //then
    assertTrue(isValid);

  }

  @Test(dataProvider = "invalidPlacement")
  void invalidPlacement(ShipOrientation shipOrientation, int shipStartIndex, int mastCount,
                        FieldState[] board) {
    //given
    ShipPlacementValidator shipPlacementValidator =
        new ShipPlacementValidator(shipOrientation, shipStartIndex, mastCount, board);
    //when
    boolean isValid = shipPlacementValidator.isPlacementValid();
    //then
    assertFalse(isValid);
  }

  private static FieldState [] producedBoard(List<Integer> occupiedPlaces) {
    FieldState [] board = new FieldState[BOARD_SIZE * BOARD_SIZE];
    for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
      if(occupiedPlaces.contains(Integer.valueOf(i))) {
        board[i] = FieldState.OCCUPIED;
      } else {
        board[i] = FieldState.EMPTY;
      }
    }

    return board;
  }
}