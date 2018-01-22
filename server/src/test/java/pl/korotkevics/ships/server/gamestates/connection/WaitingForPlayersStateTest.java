package pl.korotkevics.ships.server.gamestates.connection;

import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.WrappedClient;
import pl.korotkevics.ships.server.gamestates.GameState;
import pl.korotkevics.ships.server.gamestates.fleetplacement.FleetPlacementState;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
    GameState gameState = state.process();

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