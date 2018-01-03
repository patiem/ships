package com.epam.ships.server;

import com.epam.ships.server.gamestates.GameState;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@Test
public class GameTest {

  public void shouldEndWhenShouldBeContinuedConditionIsFalse() {
    //given
    GameState initState = mock(GameState.class);
    GameState secondState = mock(GameState.class);

    when(initState.process()).thenReturn(secondState);
    when(secondState.process()).thenReturn(secondState);

    when(initState.shouldBeContinued()).thenReturn(true);
    when(secondState.shouldBeContinued()).thenReturn(false);

    //when
    new Game(initState).loop();

    //then
    verify(initState, times(1)).process();
    verify(secondState, times(1)).process();
  }

}