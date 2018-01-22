package pl.korotkevics.ships.server.game.gamestates.endgame;

import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.korotkevics.ships.server.communication.CommunicationBus;
import pl.korotkevics.ships.server.game.TurnManager;
import pl.korotkevics.ships.server.game.gamestates.GameState;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
  private void before() {
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