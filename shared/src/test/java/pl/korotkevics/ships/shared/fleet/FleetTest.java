package pl.korotkevics.ships.shared.fleet;

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
    Fleet fleet = Fleet.ofShips(Arrays.asList(firstShip, secondShip));
    //then
    assertFalse(fleet.isDefeated());
  }

  @Test
  public void shipIsDestoyed() {
    //given
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1"))));
    //when
    Mast firstHit = Mast.ofIndex("3");
    Mast secondHit = Mast.ofIndex("2");
    Mast thirdHit = Mast.ofIndex("1");
    //then
    fleet.handleDamage(firstHit);
    fleet.handleDamage(secondHit);
    assertEquals(fleet.handleDamage(thirdHit), Damage.DESTROYED);
  }

  @Test
  public void itIsMiss() {
    //given
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1"))));
    //when
    Mast miss = Mast.ofIndex("4");
    //then
    assertEquals(fleet.handleDamage(miss), Damage.MISSED);
  }

  @Test
  public void stringRepresentationIsProper() {
    //given - when
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1"))));
    //then
    assertEquals(fleet.toString(), "Fleet(fleet={1=Ship(masts=[1, 2, 3]), 2=Ship(masts=[1, 2, 3]), 3=Ship(masts=[1, 2, 3])})");
  }

  @Test
  public void integerListRepresentationIsProper() {
    //given
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1"))));
    //when
    List<Integer> list = fleet.toIntegerList();
    //then
    assertEquals(list.toString(), "[1, 2, 3]");
  }
}
