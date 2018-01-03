package com.epam.ships.server.gamestates;

import com.epam.ships.server.CommunicationBus;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
@Test
public class GameEndWithWalkoverStateTest {

  public void shouldProcessWinState() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);

    //when
    GameEndWithWalkoverState gameEndWithWalkover= new GameEndWithWalkoverState(communicationBus);
    GameState gameState = gameEndWithWalkover.process();

    //then
    assertEquals(GameEndWithWalkoverState.class, gameState.getClass());
    verify(communicationBus, times(1)).stop();
  }

  public void shouldReturnFalseWhenShouldBeContinuedIsCall() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    //when
    GameEndWithWalkoverState gameEndWithWalkover= new GameEndWithWalkoverState(communicationBus);
    boolean result = gameEndWithWalkover.shouldBeContinued();
    //then
    assertFalse(result);
  }
}