package pl.korotkevics.ships.server.gamestates.fleetplacement;

import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.MessageReceiver;
import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.WrappedClient;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.BaseMessage;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

/**
 * Bases on header it returns generated fleet or received one.
 *
 * @author Piotr Czyż
 * @since 2018-01-10
 */
class FleetPlacementProcessor {

  private final CommunicationBus communicationBus;
  private Target logger = new SharedLogger(FleetPlacementProcessor.class);
  private MessageReceiver messageReceiver;
  private final FleetGenerator fleetGenerator;

  FleetPlacementProcessor(final CommunicationBus communicationBus,
                          final FleetGenerator fleetGenerator) {
    this.communicationBus = communicationBus;
    this.messageReceiver = new MessageReceiver(communicationBus);
    this.fleetGenerator = fleetGenerator;
  }

  Fleet placeFleet(final WrappedClient wrappedClient) {
    this.messageReceiver.receive(wrappedClient);
    if (this.messageReceiver.isRandomPlacement()) {
      Fleet fleet = this.fleetGenerator.generateFleet();
      this.send(wrappedClient, fleet);
      logger.info(fleet.toString());
      return this.placeFleet(wrappedClient);
    }
    return receiveFloat();
  }

  private void send(final WrappedClient wrappedClient, final Fleet fleet) {
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    final Message message = BaseMessage.builder()
            .setAuthor(Author.SERVER)
            .setHeader(Header.RANDOM_PLACEMENT)
            .setFleet(fleet)
            .build();
    messageSender.send(wrappedClient, message);
  }

  private Fleet receiveFloat() {
    return this.messageReceiver.getMessage().getFleet();
  }
}
