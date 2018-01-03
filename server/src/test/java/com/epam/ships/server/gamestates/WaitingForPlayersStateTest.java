package com.epam.ships.server.gamestates;

import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.WrappedClient;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertNotNull;
@Test
public class WaitingForPlayersStateTest {

  public void shouldProcess(){
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    when(communicationBus.getFirstClient()).thenReturn(mock(WrappedClient.class));
    when(communicationBus.getSecondClient()).thenReturn(mock(WrappedClient.class));

    WaitingForPlayersState state = new WaitingForPlayersState(communicationBus);

    //when
    GameState fleetPlacementState = state.process();

    //then
    verify(communicationBus, times(1)).start();
    assertNotNull(fleetPlacementState);
  }

}