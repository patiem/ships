package com.epam.ships.fleet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class ShipTest {

    @Test
    public void itShouldCreateEqualShipsWithGivenIndices() {
        //given - when
        Ship firstShip = new ShipBuilder().withIndices(new Index(3), new Index(2)).build();
        Ship secondShip = new ShipBuilder().withIndices(new Index(3), new Index(2)).build();
        //then
        assertEquals(firstShip, secondShip);
    }

    @Test
    public void itShouldCreateNotEqualShipsWithDifferentGivenIndices() {
        //given - when
        Ship firstShip = new ShipBuilder().withIndices(new Index(4), new Index(2)).build();
        Ship secondShip = new ShipBuilder().withIndices(new Index(3), new Index(2)).build();
        //then
        assertNotEquals(firstShip, secondShip);
    }

}