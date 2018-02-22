package pl.korotkevics.ships.server.game.gamestates.fleetplacement;

import pl.korotkevics.ships.server.communication.CommunicationBus;
import pl.korotkevics.ships.server.communication.MessageReceiver;
import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.communication.WrappedClient;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Author;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.communication.core.message.MessageBuilder;
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
  private final FleetGenerator fleetGenerator;
  private Target logger = new SharedLogger(FleetPlacementProcessor.class);
  private MessageReceiver messageReceiver;

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
    final Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.RANDOM_PLACEMENT)
        .withFleet(fleet)
        .build();
    messageSender.send(wrappedClient, message, true);
  }

  private Fleet receiveFloat() {
    return this.messageReceiver.getMessage().getFleet();
  }
}
