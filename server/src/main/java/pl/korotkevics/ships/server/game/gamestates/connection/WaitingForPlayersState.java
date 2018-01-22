package pl.korotkevics.ships.server.game.gamestates.connection;

import pl.korotkevics.ships.server.communication.CommunicationBus;
import pl.korotkevics.ships.server.communication.MessageSender;
import pl.korotkevics.ships.server.game.gamestates.GameState;
import pl.korotkevics.ships.server.game.gamestates.fleetplacement.FleetPlacementState;
import pl.korotkevics.ships.shared.infra.communication.api.message.Header;
import pl.korotkevics.ships.shared.infra.logging.api.Target;
import pl.korotkevics.ships.shared.infra.logging.core.SharedLogger;

/**
 * Represents a state in which player is waiting for an opponent.
 *
 * @author Piotr Czyż
 * @see GameState
 * @since 2018-01-02
 */
public class WaitingForPlayersState implements GameState {
  private final Target logger = new SharedLogger(WaitingForPlayersState.class);
  private CommunicationBus communicationBus;

  /**
   * @param communicationBus client server communication bus.
   */
  public WaitingForPlayersState(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  /**
   * Transfer to fleet placement state.
   *
   * @return GameState - FleetPlacementState.
   * @see FleetPlacementState
   */
  @Override
  public GameState process() {
    communicationBus.start();

    MessageSender messageSender = new MessageSender(communicationBus, logger);
    messageSender.sendToAll(Header.OPPONENT_CONNECTED);

    return new FleetPlacementState(communicationBus);
  }
}
