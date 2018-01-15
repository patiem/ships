package pl.korotkevics.ships.client.validators;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.korotkevics.ships.client.gui.util.FieldState;
import pl.korotkevics.ships.client.gui.util.ShipOrientation;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ShipPlacementValidatorTest {

  private static final int BOARD_SIZE = 10;

  @DataProvider(name = "validPlacement")
  public static Object [][] validData() {
    return new Object[][] {
        {ShipOrientation.VERTICAL, 0, 3, produceBoard(Arrays.asList(99,81))},
        {ShipOrientation.HORIZONTAL, 0, 3, produceBoard(Arrays.asList(4,2,3))},
        {ShipOrientation.VERTICAL, 0, 4, produceBoard(Arrays.asList(20,30,40))},
        {ShipOrientation.VERTICAL, 99, 1, produceBoard(Arrays.asList(20,30,40))},
        {ShipOrientation.HORIZONTAL, 99, 1, produceBoard(Arrays.asList(20,30,40))},
        {ShipOrientation.HORIZONTAL, 79, 1, produceBoard(Arrays.asList(80,90,70,60))},
        {ShipOrientation.VERTICAL, 79, 1, produceBoard(Arrays.asList(80,90,70,60))},
        {ShipOrientation.VERTICAL, 77, 3, produceBoard(Arrays.asList(70,80,90))}
    };
  }

  @DataProvider(name = "invalidPlacement")
  public static Object [][] invalidData() {
    return new Object[][] {
        {ShipOrientation.HORIZONTAL, 0, 3, produceBoard(Arrays.asList(0,1,2,3))},
        {ShipOrientation.VERTICAL, 0, 3, produceBoard(Arrays.asList(0,1,2,3))},
        {ShipOrientation.HORIZONTAL, 25, 2, produceBoard(Arrays.asList(45,1,2,3))},
        {ShipOrientation.VERTICAL, 25, 2, produceBoard(Arrays.asList(15,1,2,3))},
        {ShipOrientation.HORIZONTAL, 25, 2, produceBoard(Arrays.asList(24,1,2,3))},
        {ShipOrientation.VERTICAL, 25, 2, produceBoard(Arrays.asList(27,1,2,3))},

        {ShipOrientation.HORIZONTAL, 70, 4, produceBoard(Arrays.asList(1))},
        {ShipOrientation.VERTICAL, 98, 4, produceBoard(Arrays.asList(1))},
        //corners
        {ShipOrientation.VERTICAL, 25, 2, produceBoard(Arrays.asList(14,1,2,3))},
        {ShipOrientation.VERTICAL, 25, 2, produceBoard(Arrays.asList(34,1,2,3))},
        {ShipOrientation.VERTICAL, 25, 2, produceBoard(Arrays.asList(17,1,2,3))},
        {ShipOrientation.VERTICAL, 25, 2, produceBoard(Arrays.asList(37,1,2,3))},

        {ShipOrientation.HORIZONTAL, 25, 2, produceBoard(Arrays.asList(44,1,2,3))},
        {ShipOrientation.HORIZONTAL, 25, 2, produceBoard(Arrays.asList(46,1,2,3))},
        {ShipOrientation.HORIZONTAL, 25, 2, produceBoard(Arrays.asList(14,1,2,3))},
        {ShipOrientation.HORIZONTAL, 25, 2, produceBoard(Arrays.asList(16,1,2,3))}
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

  @Test
  void shipPassingToOtherSideShouldCauseInvalidPlacement() {
    //given
    ShipOrientation shipOrientation = ShipOrientation.VERTICAL;
    int shipStartingIndex = 7;
    int mastCount = 4;
    FieldState[] board = produceBoard(Arrays.asList(1));
    ShipPlacementValidator shipPlacementValidator =
        new ShipPlacementValidator(shipOrientation, shipStartingIndex, mastCount, board);

    //when
    boolean isValid = shipPlacementValidator.isPlacementValid();

    //then
    assertFalse(isValid);
  }

  @Test
  void shipNotPassingToOtherSideShouldCauseValidPlacement() {
    //given
    ShipOrientation shipOrientation = ShipOrientation.VERTICAL;
    int shipStartingIndex = 6;
    int mastCount = 4;
    FieldState[] board = produceBoard(Arrays.asList(1));
    ShipPlacementValidator shipPlacementValidator =
        new ShipPlacementValidator(shipOrientation, shipStartingIndex, mastCount, board);

    //when
    boolean isValid = shipPlacementValidator.isPlacementValid();

    //then
    assertTrue(isValid);
  }

  private static FieldState [] produceBoard(final List<Integer> occupiedPlaces) {
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