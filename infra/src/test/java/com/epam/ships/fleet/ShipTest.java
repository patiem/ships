package com.epam.ships.fleet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class ShipTest {

    @Test
    public void itShouldCreateEqualShipsWithGivenIndices() {
        //given - when
        Ship firstShip = Ship.ofMasts(new Mast(3), new Mast(2));
        Ship secondShip = Ship.ofMasts(new Mast(3), new Mast(2));
        //then
        assertEquals(firstShip, secondShip);
    }

    @Test
    public void itShouldCreateNotEqualShipsWithDifferentGivenIndices() {
        //given - when
        Ship firstShip = Ship.ofMasts(new Mast(4), new Mast(2));
        Ship secondShip = Ship.ofMasts(new Mast(3), new Mast(2));
        //then
        assertNotEquals(firstShip, secondShip);
    }

}