package com.epam.ships.server.gamestates;

import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.TurnManager;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test
public class GameEndWithWinStateTest {

  private GameEndWithWinState gameEndWithWinState;

  @Mock
  private CommunicationBus communicationBus;
  @Mock
  private TurnManager turnManager;

  @BeforeClass
  private void before(){
    initMocks(this);
    gameEndWithWinState = new GameEndWithWinState(communicationBus, turnManager);
  }

  public void shouldStayInWinState() {
    //given - when
    GameState gameState = gameEndWithWinState.process();
    //then
    assertEquals(GameEndWithWinState.class, gameState.getClass());
    verify(communicationBus, times(1)).stop();

  }

  public void shouldReturnFalseWhenShouldBeContinuedIsCall() {
    //given - when
    boolean result = gameEndWithWinState.shouldBeContinued();
    //then
    assertFalse(result);
  }
}