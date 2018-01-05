package pl.korotkevics.ships.server.gamestates;

import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.WrappedClient;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

@Test
public class WaitingForPlayersStateTest {

  private WaitingForPlayersState state;
  @Mock
  private CommunicationBus communicationBus;
  @Mock
  private WrappedClient wrappedClient;
  @Mock
  private WrappedClient secondWrappedClient;

  @BeforeClass
  private void before() {
    initMocks(this);
    state = new WaitingForPlayersState(communicationBus);
  }

  public void shouldProcessGameToFleetPlacementState() {
    //given
    when(communicationBus.getFirstClient()).thenReturn(wrappedClient);
    when(communicationBus.getSecondClient()).thenReturn(secondWrappedClient);

    //when
    GameState gameState= state.process();

    //then
    verify(communicationBus, times(1)).start();
    assertEquals(FleetPlacementState.class, gameState.getClass());

  }

  public void shouldBeContinuedShouldReturnTrueByDefault() {
    //when
    boolean result = state.shouldBeContinued();
    //then
    assertTrue(result);
  }

}