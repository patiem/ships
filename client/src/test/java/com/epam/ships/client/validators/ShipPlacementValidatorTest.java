package com.epam.ships.client.validators;

import com.epam.ships.client.gui.util.FieldState;
import com.epam.ships.client.gui.util.ShipOrientation;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ShipPlacementValidatorTest {

  @DataProvider(name = "validPlacement")
  public static Object [][] validData() {
    return new Object[][] {
        {//orientation, // shipStartIndex, //mastCount, // board
        }
    };
  }

  @DataProvider(name = "invalidPlacement")
  public static Object [][] invalidData() {
    return new Object[][] {
        {}
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

}