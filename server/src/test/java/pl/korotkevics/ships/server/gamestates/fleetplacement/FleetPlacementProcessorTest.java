package pl.korotkevics.ships.server.gamestates.fleetplacement;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.WrappedClient;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.api.message.Status;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test
public class FleetPlacementProcessorTest {

  public void shouldReturnReceivedFleetWhenTheHeaderIsManualPlacement() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    FleetGenerator fleetGenerator = mock(FleetGenerator.class);
    Fleet fleet = mock(Fleet.class);
    Message message = new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.MANUAL_PLACEMENT)
        .withFleet(fleet)
        .build();
    when(communicationBus.receive(wrappedClient)).thenReturn(message);
    FleetPlacementProcessor fleetPlacementProcessor = new FleetPlacementProcessor(communicationBus,
        fleetGenerator);
    //when
    Fleet receivedFleet = fleetPlacementProcessor.placeFleet(wrappedClient);
    //then
    Assert.assertEquals(receivedFleet, fleet);
  }

  public void shouldCreateRandomFleetWhenTheHeaderIsRandomPlacement() {
    //given
    CommunicationBus communicationBus = mock(CommunicationBus.class);
    WrappedClient wrappedClient = mock(WrappedClient.class);
    FleetGenerator fleetGenerator = mock(FleetGenerator.class);
    Fleet fleet = mock(Fleet.class);
    Message message = new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.RANDOM_PLACEMENT)
        .build();

    Message ready = manualPlacementMessage();

    Message randomFleet = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.RANDOM_PLACEMENT)
        .withStatus(Status.OK)
        .withFleet(fleet)
        .build();

    when(communicationBus.receive(wrappedClient)).thenReturn(message, ready);
    when(fleetGenerator.generateFleet()).thenReturn(fleet);
    FleetPlacementProcessor fleetPlacementProcessor = new FleetPlacementProcessor(communicationBus,
        fleetGenerator);
    //when
    fleetPlacementProcessor.placeFleet(wrappedClient);
    //then
    verify(communicationBus).send(wrappedClient, randomFleet);
  }


  private Message manualPlacementMessage() {
    return new MessageBuilder()
        .withAuthor(Author.CLIENT)
        .withHeader(Header.MANUAL_PLACEMENT)
        .build();
  }


}