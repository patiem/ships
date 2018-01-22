package pl.korotkevics.ships.server.game.gamestates.fleetplacement;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.korotkevics.ships.shared.fleet.Fleet;

import java.util.stream.IntStream;

@Test
public class FleetGeneratorTest {


  @DataProvider(name = "randomFleetsGeneratedCount")
  public Object[] dataProvider() {
    return numberOfTests();
  }

  private Object[][] numberOfTests() {
    Object[][] objects = new Object[1000][];
    IntStream.range(0, 1000).forEach(i -> objects[i] = new Object[] {i});
    return objects;
  }

  @Test(dataProvider = "randomFleetsGeneratedCount")
  public void shouldGenerateFleetConsistOfTenShips(int testNumber) {
    //given
    FleetGenerator generator = new FleetGenerator();
    //when
    Fleet fleet = generator.generateFleet();
    //then
    Assert.assertEquals(fleet.isDefeated(), false);
  }
}