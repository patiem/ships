package pl.korotkevics.ships.server.gamestates;

import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.Message;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;
import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.TurnManager;
import pl.korotkevics.ships.server.WrappedClient;
import pl.korotkevics.ships.server.gamestates.play.PlayState;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents state of game in witch players place their fleets.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class FleetPlacementState implements GameState {
  private final CommunicationBus communicationBus;
  private final TurnManager turnManager;
  private final Target logger = new SharedLogger(FleetPlacementState.class);
  private final List<Fleet> fleets;

  /**
   * It creates state of game in witch players place their fleets.
   *
   * @param communicationBus client server communication bus
   */
  FleetPlacementState(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
    this.turnManager = new TurnManager(communicationBus.getFirstClient(),
        communicationBus.getSecondClient());
    fleets = new ArrayList<>(2);
  }

  /**
   * It receives fleets from players, and transfers game to next state.
   *
   * @return GameState - PlayState
   */
  @Override
  public GameState process() {
    askPlayersForPlaceFleet();
    receiveFleetFromBothPlayers();
    rest();
    return new PlayState(communicationBus, fleets);
  }

  private void askPlayersForPlaceFleet() {
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.sendToAll(Header.PLACEMENT);
  }

  private void receiveFleetFromBothPlayers() {
    fleets.add(receiveFloat(turnManager.getCurrentPlayer()));
    fleets.add(receiveFloat(turnManager.getOtherPlayer()));
  }

  private Fleet receiveFloat(WrappedClient player) {
    final Message fleetMsg = communicationBus.receive(player);
    logger.info("Fleet received: " + fleetMsg);
    return fleetMsg.getFleet();
  }

  private void rest() {
    final long restTime = 300;
    try {
      Thread.sleep(restTime);
    } catch (final InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}
