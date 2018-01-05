package pl.korotkevics.ships.server.gamestates.play;

import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.fleet.Mast;
import pl.korotkevics.ships.shared.fleet.Ship;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;
import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.WrappedClient;
import pl.korotkevics.ships.server.gamestates.GameEndWithWalkoverState;
import pl.korotkevics.ships.server.gamestates.GameEndWithWinState;
import pl.korotkevics.ships.server.gamestates.GameState;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

@Test
public class PlayStateTest {

  @Mock
  private CommunicationBus communicationBus;
  @Mock
  private Fleet fleet;
  @Mock
  private WrappedClient sender;
  @Mock
  private WrappedClient wrappedClient;

  @BeforeMethod
  void before(){
    initMocks(this);
  }


  public void shouldKeepStateWhenNoWinner() {
    Fleet secondFleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("4"), Mast.ofIndex("5"))));
    List<Fleet> fleetList = Arrays.asList(fleet, secondFleet);
    processShot();
    PlayState playState = new PlayState(communicationBus, fleetList);
    //when
    GameState gameState = playState.process();
    //then
    assertEquals(PlayState.class, gameState.getClass());
  }

  public void shouldProcessGameToWinState() {
    //given
    Fleet secondFleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("4"))));
    List<Fleet> fleetList = Arrays.asList(fleet, secondFleet);
    processShot();
    PlayState playState = new PlayState(communicationBus, fleetList);
    //when
    GameState gameState = playState.process();
    //then
    assertEquals(GameEndWithWinState.class, gameState.getClass());
  }

  public void shouldProcessGameToWalkover() {
    Fleet secondFleet = mock(Fleet.class);
    List<Fleet> fleetList = Arrays.asList(fleet, secondFleet);
    Message shot = new MessageBuilder()
        .withAuthor(Author.AUTO)
        .withHeader(Header.CONNECTION)
        .withStatement("END")
        .build();
    when(communicationBus.receive(sender)).thenReturn(shot);
    when(communicationBus.getFirstClient()).thenReturn(sender);
    when(communicationBus.getSecondClient()).thenReturn(wrappedClient);
    PlayState playState = new PlayState(communicationBus, fleetList);
    //when
    GameState gameState = playState.process();
    //then
    assertEquals(GameEndWithWalkoverState.class, gameState.getClass());
  }

  private void processShot() {
    when(communicationBus.receive(sender)).thenReturn(produceMessageWithShot());
    when(communicationBus.getFirstClient()).thenReturn(sender);
    when(communicationBus.getSecondClient()).thenReturn(wrappedClient);
  }

  private Message produceMessageWithShot() {
    return new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.SHOT)
        .withStatement("4")
        .build();
  }
}
