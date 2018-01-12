package pl.korotkevics.ships.server.gamestates.fleetplacement;

import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.TurnManager;
import pl.korotkevics.ships.server.gamestates.GameState;
import pl.korotkevics.ships.server.gamestates.play.PlayState;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

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
  private final FleetPlacementProcessor fleetProcessor;
  private final TurnManager turnManager;
  private final Target logger = new SharedLogger(FleetPlacementState.class);
  private final List<Fleet> fleets;

  /**
   * It creates state of game in witch players place their fleets.
   *
   * @param communicationBus client server communication bus
   */
  public FleetPlacementState(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
    this.turnManager = new TurnManager(communicationBus.getFirstClient(),
        communicationBus.getSecondClient());
    this.fleetProcessor = new FleetPlacementProcessor(communicationBus);
    fleets = new ArrayList<>();
  }

  /**
   * It receives fleets from players, and transfers game to next state.
   *
   * @return GameState - PlayState
   */
  @Override
  public GameState process() {
    this.askPlayersForPlaceFleet();
    this.placeFleet();
    this.rest();
    return new PlayState(communicationBus, fleets);
  }

  private void placeFleet() {
    fleets.add(this.fleetProcessor.placeFleet(this.turnManager.getCurrentPlayer()));
    fleets.add(this.fleetProcessor.placeFleet(this.turnManager.getOtherPlayer()));
  }

  private void askPlayersForPlaceFleet() {
    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.sendToAll(Header.MANUAL_PLACEMENT);
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
