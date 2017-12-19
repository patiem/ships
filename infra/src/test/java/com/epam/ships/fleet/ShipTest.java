package com.epam.ships.fleet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class ShipTest {

    @Test
    public void itShouldCreateEqualShipsWithGivenIndices() {
        //given - when
        Ship firstShip = Ship.ofMasts(Mast.ofIndex(3), Mast.ofIndex(2));
        Ship secondShip = Ship.ofMasts(Mast.ofIndex(3), Mast.ofIndex(2));
        //then
        assertEquals(firstShip, secondShip);
    }

    @Test
    public void itShouldCreateNotEqualShipsWithDifferentGivenIndices() {
        //given - when
        Ship firstShip = Ship.ofMasts(Mast.ofIndex(4), Mast.ofIndex(2));
        Ship secondShip = Ship.ofMasts(Mast.ofIndex(3), Mast.ofIndex(2));
        //then
        assertNotEquals(firstShip, secondShip);
    }

}