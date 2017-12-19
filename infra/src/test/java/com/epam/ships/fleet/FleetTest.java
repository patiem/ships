package com.epam.ships.fleet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class FleetTest {
    @Test
    public void itShouldCreateNonEmptyFleetWhenGivenWithShips() {
        //given
        Ship firstShip = Ship.ofMasts(Mast.ofIndex(3), Mast.ofIndex(2));
        Ship secondShip = Ship.ofMasts(Mast.ofIndex(4), Mast.ofIndex(1));
        //when
        Fleet fleet = Fleet.ofShips(firstShip, secondShip);
        //then
        assertFalse(fleet.isEmpty());
    }
}
