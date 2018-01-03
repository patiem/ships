package com.epam.ships.server.gamestates;

import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test
public class GameEndWithWinStateTest {

  public void shouldProcessWinState(){
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    TurnManager turnManager = mock(TurnManager.class);

    //when
    GameEndWithWinState gameEndWithWinState = new GameEndWithWinState(communicationBus, turnManager);
    GameState gameState = gameEndWithWinState.process();

    //then
    assertEquals(GameEndWithWinState.class, gameState.getClass());
    verify(communicationBus, times(1)).stop();
  }

  public void shouldReturnFalseWhenShouldBeContinuedIsCall(){
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    TurnManager turnManager = mock(TurnManager.class);
    //when
    GameEndWithWinState gameEndWithWinState = new GameEndWithWinState(communicationBus, turnManager);
    boolean result = gameEndWithWinState.shouldBeContinued();
    //then
    assertFalse(result);
  }
}