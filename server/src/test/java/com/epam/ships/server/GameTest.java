package com.epam.ships.server;

import com.epam.ships.server.gamestates.GameState;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@Test
public class GameTest {

  public void shouldEndAfterThreeCycles() {
    //given

    GameState initState = mock(GameState.class);
    GameState secondState = mock(GameState.class);
    GameState thirdState = mock(GameState.class);

    when(initState.process()).thenReturn(secondState);
    when(secondState.process()).thenReturn(thirdState);
    when(thirdState.process()).thenReturn(thirdState);

    when(initState.shouldBeContinued()).thenReturn(true);
    when(secondState.shouldBeContinued()).thenReturn(true);
    when(thirdState.shouldBeContinued()).thenReturn(false);


    //when
    new Game(initState).loop();

    //then
    verify(initState, times(1)).process();
    verify(secondState, times(1)).process();
    verify(thirdState, times(1)).process();
  }

}