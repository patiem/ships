package pl.korotkevics.ships.server.gamestates.fleetplacement;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.WrappedClient;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Test
public class FleetPlacementProcessorTest {

  public void shouldReturnReceivedFleetWhenTheHeaderIsPlacement() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    Fleet fleet = mock(Fleet.class);
    Message message = new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.PLACEMENT)
        .withFleet(fleet)
        .build();
    when(communicationBus.receive(wrappedClient)).thenReturn(message);
    FleetPlacementProcessor fleetPlacementProcessor = new FleetPlacementProcessor(communicationBus);
    //when
    Fleet receivedFleet = fleetPlacementProcessor.placeFleet(wrappedClient);
    //then
    Assert.assertEquals(receivedFleet, fleet);
  }

  public void shouldCreateRandomFleetWhenTheHeaderIsRandomPlacement() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    Message message = new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.RANDOM_PLACEMENT)
        .build();
    when(communicationBus.receive(wrappedClient)).thenReturn(message);
    FleetPlacementProcessor fleetPlacementProcessor = new FleetPlacementProcessor(communicationBus);
    //when
    Fleet generatedFleet = fleetPlacementProcessor.placeFleet(wrappedClient);
    //then
    Assert.assertNotNull(generatedFleet);
  }


}