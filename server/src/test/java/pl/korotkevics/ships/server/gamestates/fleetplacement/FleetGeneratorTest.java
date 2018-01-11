package pl.korotkevics.ships.server.gamestates.fleetplacement;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.fleet.Fleet;

@Test
public class FleetGeneratorTest {


  @DataProvider(name = "manyTests")
  public Object[][] dataProvider() {
    return numberOfTests();
  }

  private Object[][] numberOfTests() {
    Object[][] objects = new Object[1000][];
    for (int i = 0; i < 1000; i++) {
      Object[] obj = new Object[] {i};
      objects[i] = obj;
    }
    return objects;
  }

  @Test(dataProvider = "manyTests")
  public void shouldGenerateFleetConsistOfTenShips(int testNumber) {
    //given
    FleetGenerator generator = new FleetGenerator();
    //when
    Fleet fleet = generator.generateFleet();
    //then
    Assert.assertEquals(fleet.isDefeated(), false);
  }
}