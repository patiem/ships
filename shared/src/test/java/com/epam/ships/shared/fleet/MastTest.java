package com.epam.ships.shared.fleet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class MastTest {
  @Test
  public void itShouldCreateEqualMastsWhenGivenSameIndices() {
    //given - when
    Mast firstMast = Mast.ofIndex("3");
    Mast secondMast = Mast.ofIndex("3");
    //then
    assertEquals(firstMast, secondMast);
  }

  @Test
  public void itShouldCreateEqualMastsWhenGivenDifferentIndices() {
    //given - when
    Mast firstMast = Mast.ofIndex("2");
    Mast secondMast = Mast.ofIndex("3");
    //then
    assertNotEquals(firstMast, secondMast);
  }
}
