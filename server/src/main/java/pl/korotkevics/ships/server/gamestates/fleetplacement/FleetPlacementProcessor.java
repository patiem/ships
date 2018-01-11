package pl.korotkevics.ships.server.gamestates.fleetplacement;

import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.MessageReceiver;
import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.WrappedClient;
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

  private Target logger = new SharedLogger(FleetPlacementProcessor.class);
  private final CommunicationBus communicationBus;
  private MessageReceiver messageReceiver;

  FleetPlacementProcessor(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
    this.messageReceiver = new MessageReceiver(communicationBus);
  }

  Fleet placeFleet(final WrappedClient player) {
    messageReceiver.receive(player);
    if (messageReceiver.isRandomPlacement()) {
      FleetGenerator generator = new FleetGenerator();
      Fleet fleet = generator.generateFleet();
      send(player, fleet);
      logger.info(fleet.toString());
      return fleet;
    } else {
      return receiveFloat();
    }
  }

  private void send(final WrappedClient player, Fleet fleet) {
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    final Message message = new MessageBuilder()
        .withAuthor(Author.SERVER)
        .withHeader(Header.RANDOM_PLACEMENT)
        .withFleet(fleet)
        .build();
    messageSender.send(player, message);
  }

  private Fleet receiveFloat() {
    return messageReceiver.getMessage().getFleet();
  }
}
