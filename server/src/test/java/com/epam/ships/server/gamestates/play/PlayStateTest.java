package com.epam.ships.server.gamestates.play;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.fleet.Ship;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;
import com.epam.ships.server.WrappedClient;
import com.epam.ships.server.gamestates.GameEndWithWalkoverState;
import com.epam.ships.server.gamestates.GameEndWithWinState;
import com.epam.ships.server.gamestates.GameState;
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
    Fleet fleet2 = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("4"), Mast.ofIndex("5"))));
    List<Fleet> fleetList = Arrays.asList(fleet, fleet2);
    processShot();
    //when
    PlayState playState = new PlayState(communicationBus, fleetList);
    GameState gameState = playState.process();
    //then
    assertEquals(PlayState.class, gameState.getClass());
  }

  public void shouldProcessWin() {
    //given
    Fleet fleet2 = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("4"))));
    List<Fleet> fleetList = Arrays.asList(fleet, fleet2);
    processShot();
    //when
    PlayState playState = new PlayState(communicationBus, fleetList);
    GameState gameState = playState.process();
    //then
    assertEquals(GameEndWithWinState.class, gameState.getClass());
  }

  public void shouldProcessWalkover() {
    Fleet fleet2 = mock(Fleet.class);
    List<Fleet> fleetList = Arrays.asList(fleet, fleet2);
    Message shot = new MessageBuilder()
        .withAuthor(Author.AUTO)
        .withHeader(Header.CONNECTION)
        .withStatement("END")
        .build();
    when(communicationBus.receive(sender)).thenReturn(shot);
    when(communicationBus.getFirstClient()).thenReturn(sender);
    when(communicationBus.getSecondClient()).thenReturn(wrappedClient);
    //when
    PlayState playState = new PlayState(communicationBus, fleetList);
    GameState gameState = playState.process();
    //then
    assertEquals(GameEndWithWalkoverState.class, gameState.getClass());
  }

  private void processShot() {
    when(communicationBus.receive(sender)).thenReturn(shot());
    when(communicationBus.getFirstClient()).thenReturn(sender);
    when(communicationBus.getSecondClient()).thenReturn(wrappedClient);
  }

  private Message shot() {
    return new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.SHOT)
        .withStatement("4")
        .build();
  }
}
