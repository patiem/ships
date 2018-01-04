package com.epam.ships.server.gamestates;

import com.epam.ships.server.CommunicationBus;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test
public class GameEndWithWalkoverStateTest {

  private GameEndWithWalkoverState gameEndWithWalkover;
  @Mock
  private CommunicationBus communicationBus;

  @BeforeClass
  private void before() {
    initMocks(this);
    gameEndWithWalkover = new GameEndWithWalkoverState(communicationBus);
  }

  public void shouldProcessWinStateAndStayInThisState() {
    //given - when
    GameState gameState = gameEndWithWalkover.process();
    //then
    assertEquals(GameEndWithWalkoverState.class, gameState.getClass());
  }

  public void shouldProcessWinAndVerifyThatCommunicationBusHasBeenStopped() {
    //given - when
    gameEndWithWalkover.process();
    //then
    verify(communicationBus, times(1)).stop();
  }

  public void shouldReturnFalseWhenShouldBeContinuedIsCall() {
    //given - when
    boolean result = gameEndWithWalkover.shouldBeContinued();
    //then
    assertFalse(result);
  }
}