package com.epam.ships.fleet;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class FleetTest {
    @Test
    public void itShouldCreateNonEmptyFleetWhenGivenWithShips() {
        //given
        Ship firstShip = Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"));
        Ship secondShip = Ship.ofMasts(Mast.ofIndex("4"), Mast.ofIndex("1"));
        //when
        List<Ship> ships = Arrays.asList(firstShip, secondShip);
        Fleet fleet = Fleet.ofShips(ships);
        //then
        assertFalse(fleet.isDefeated());
    }
    @Test
    public void shipIsDestructed() {
        //given
        List<Ship> ships = Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1")));
        Fleet fleet = Fleet.ofShips(ships);
        //when
        Mast firstHit = Mast.ofIndex("3");
        Mast secondHit = Mast.ofIndex("2");
        Mast thirdHit = Mast.ofIndex("1");
        //then
        fleet.handleDamage(firstHit);
        fleet.handleDamage(secondHit);
        assertEquals(fleet.handleDamage(thirdHit), Damage.DESTRUCTED);
    }
}
