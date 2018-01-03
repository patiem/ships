package com.epam.ships.server.gamestates.play;

import com.epam.ships.fleet.Fleet;
import com.epam.ships.fleet.Mast;
import com.epam.ships.fleet.Ship;
import com.epam.ships.infra.communication.api.Message;
import com.epam.ships.infra.communication.api.message.Author;
import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.MessageRepository;
import com.epam.ships.server.WrappedClient;
import com.epam.ships.server.gamestates.GameEndWithWalkoverState;
import com.epam.ships.server.gamestates.GameEndWithWinState;
import com.epam.ships.server.gamestates.GameState;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@Test
public class PlayStateTest {

  public void shouldProcessWin() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"))));
    Fleet fleet2 = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("4"))));
    List<Fleet> fleetList = Arrays.asList(fleet, fleet2);
    WrappedClient sender = mock(WrappedClient.class);
    Message shot = new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.SHOT)
        .withStatement("4")
        .build();
    when(communicationBus.receive(sender)).thenReturn(shot);
    when(communicationBus.getFirstClient()).thenReturn(sender);
    when(communicationBus.getSecondClient()).thenReturn(mock(WrappedClient.class));
    //when
    PlayState playState = new PlayState(communicationBus, fleetList);
    GameState gameState = playState.process();
    //then
    assertEquals(GameEndWithWinState.class, gameState.getClass());
  }

  public void shouldProcessWalkover(){
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"))));
    Fleet fleet2 = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("4"))));
    List<Fleet> fleetList = Arrays.asList(fleet, fleet2);
    WrappedClient sender = mock(WrappedClient.class);
    Message shot = new MessageBuilder()
        .withAuthor(Author.AUTO)
        .withHeader(Header.CONNECTION)
        .withStatement("END")
        .build();
    when(communicationBus.receive(sender)).thenReturn(shot);
    when(communicationBus.getFirstClient()).thenReturn(sender);
    when(communicationBus.getSecondClient()).thenReturn(mock(WrappedClient.class));
    //when
    PlayState playState = new PlayState(communicationBus, fleetList);
    GameState gameState = playState.process();
    //then
    assertEquals(GameEndWithWalkoverState.class, gameState.getClass());
  }

  public void shouldKeepStateWhenNoWinner(){
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    Fleet fleet = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"))));
    Fleet fleet2 = Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("4"), Mast.ofIndex("5"))));
    List<Fleet> fleetList = Arrays.asList(fleet, fleet2);
    WrappedClient sender = mock(WrappedClient.class);
    Message shot = new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.SHOT)
        .withStatement("4")
        .build();
    when(communicationBus.receive(sender)).thenReturn(shot);
    when(communicationBus.getFirstClient()).thenReturn(sender);
    when(communicationBus.getSecondClient()).thenReturn(mock(WrappedClient.class));
    //when
    PlayState playState = new PlayState(communicationBus, fleetList);
    GameState gameState = playState.process();
    //then
    assertEquals(PlayState.class, gameState.getClass());
  }
}
