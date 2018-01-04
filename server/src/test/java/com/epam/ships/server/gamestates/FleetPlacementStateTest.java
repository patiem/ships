package com.epam.ships.server.gamestates;

import com.epam.ships.shared.fleet.Fleet;
import com.epam.ships.shared.fleet.Mast;
import com.epam.ships.shared.fleet.Ship;
import com.epam.ships.shared.infra.communication.api.Message;
import com.epam.ships.shared.infra.communication.core.message.MessageBuilder;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.WrappedClient;
import com.epam.ships.server.gamestates.play.PlayState;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;
@Test
public class FleetPlacementStateTest {

  public void shouldProcessState() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient player1 = mock(WrappedClient.class);
    WrappedClient player2 = mock(WrappedClient.class);
    when(communicationBus.getFirstClient()).thenReturn(player1);
    when(communicationBus.getSecondClient()).thenReturn(player2);

    Message fleetMsg = new MessageBuilder()
        .withFleet(Fleet.ofShips(Arrays.asList(Ship.ofMasts(Mast.ofIndex("3"), Mast.ofIndex("2"), Mast.ofIndex("1")))))
        .build();
    when(communicationBus.receive(player1)).thenReturn(fleetMsg);
    when(communicationBus.receive(player2)).thenReturn(fleetMsg);

    //when
    GameState playState = new FleetPlacementState(communicationBus).process();

    //then
    assertNotNull(playState);
    assertEquals(PlayState.class, playState.getClass());
  }
}