package pl.korotkevics.ships.server.gamestates.connection;

import pl.korotkevics.ships.server.CommunicationBus;
import pl.korotkevics.ships.server.MessageSender;
import pl.korotkevics.ships.server.gamestates.GameState;
import pl.korotkevics.ships.server.gamestates.fleetplacement.FleetPlacementState;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

/**
 * Represents a state in which player is waiting for an opponent.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 * @see GameState
 */
public class WaitingForPlayersState implements GameState {
  private final Target logger = new SharedLogger(WaitingForPlayersState.class);
  private CommunicationBus communicationBus;

  /**
   * @param communicationBus client server communication bus
   */
  public WaitingForPlayersState(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  /**
   * Transfer to fleet placement state.
   *
   * @see FleetPlacementState
   * @return GameState - FleetPlacementState
   */
  @Override
  public GameState process() {
    communicationBus.start();

    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.sendToAll(Header.OPPONENT_CONNECTED);

    return new FleetPlacementState(communicationBus);
  }
}
