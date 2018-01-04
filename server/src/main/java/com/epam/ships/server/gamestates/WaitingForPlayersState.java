package com.epam.ships.server.gamestates;

import com.epam.ships.shared.infra.communication.api.message.Header;
import com.epam.ships.shared.infra.logging.api.Target;
import com.epam.ships.shared.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.MessageSender;

/**
 * Represents state where player is waiting for opponent.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class WaitingForPlayersState implements GameState {
  private final Target logger = new SharedLogger(WaitingForPlayersState.class);
  private CommunicationBus communicationBus;

  /**
   * Create instance of WaitingForPlayersState.
   *
   * @param communicationBus client server communication bus
   */
  public WaitingForPlayersState(final CommunicationBus communicationBus) {
    this.communicationBus = communicationBus;
  }

  /**
   * Process game from waiting for opponent to FleetPlacementState.
   *
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
