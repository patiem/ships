package pl.korotkevics.ships.server;

import org.testng.annotations.Test;
import pl.korotkevics.ships.server.gamestates.GameState;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    new Game(initState).play();

    //then
    verify(initState, times(1)).process();
    verify(secondState, times(1)).process();
  }

}