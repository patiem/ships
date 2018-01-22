package pl.korotkevics.ships.server.game.gamestates.fleetplacement;

import pl.korotkevics.ships.server.communication.CommunicationBus;
import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.game.TurnManager;
import pl.korotkevics.ships.server.game.gamestates.GameState;
import pl.korotkevics.ships.server.game.gamestates.endgame.GameEndWithWalkoverState;
import pl.korotkevics.ships.server.game.gamestates.play.PlayState;
import pl.korotkevics.ships.shared.fleet.Fleet;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents state of game in which players place their fleets.
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
   * @param communicationBus client server communication bus.
   */
  public FleetPlacementState(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
    this.turnManager = new TurnManager(communicationBus.getFirstClient(),
        communicationBus.getSecondClient());
    this.fleetProcessor = new FleetPlacementProcessor(communicationBus, new FleetGenerator());
    fleets = new ArrayList<>();
  }

  /**
   * Receives fleets from players, and transfers game to next state.
   *
   * @return GameState - PlayState
   * @see PlayState
   * @see GameEndWithWalkoverState
   */
  @Override
  public GameState process() {
    this.askPlayersForPlaceFleet();
    this.placeFleet();
    this.rest();
    if (this.verifyClientsAreConnected()) {
      return new PlayState(communicationBus, fleets);
    }
    return new GameEndWithWalkoverState(communicationBus);
  }

  private boolean verifyClientsAreConnected() {
    return this.fleets.stream().noneMatch(Fleet::isDefeated);
  }

  private void placeFleet() {
    final Map<Integer, Fleet> clientsFleet = new ConcurrentHashMap<>();
    Thread firstClientFleetPlacement = new Thread(() ->
        clientsFleet.put(0, this.fleetProcessor.placeFleet(this.turnManager.getCurrentPlayer())));
    firstClientFleetPlacement.start();
    Thread secondClientFleetPlacement = new Thread(() ->
        clientsFleet.put(1, this.fleetProcessor.placeFleet(this.turnManager.getOtherPlayer())));
    secondClientFleetPlacement.start();
    try {
      firstClientFleetPlacement.join();
      secondClientFleetPlacement.join();
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
    this.fleets.add(clientsFleet.get(0));
    this.fleets.add(clientsFleet.get(1));
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
