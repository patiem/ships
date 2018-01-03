package com.epam.ships.server.gamestates;

import com.epam.ships.infra.communication.api.message.Header;
import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.CommunicationBus;
import com.epam.ships.server.MessageSender;
import com.epam.ships.server.TurnManager;

/**
 * Represents game state where game have a winner.
 *
 * @author Piotr Czyż
 * @since 2018-01-02
 */
public class GameEndWithWinState implements GameState {
  private final MessageSender messageSender;
  private final Target logger = new SharedLogger(GameEndWithWinState.class);
  private CommunicationBus communicationBus;
  private TurnManager turnManager;

  /**
   * It creates state in which we have a winner.
   *
   * @param communicationBus client server communication bus
   * @param turnManager      changes players' turns
   */
  public GameEndWithWinState(final CommunicationBus communicationBus,
                             final TurnManager turnManager) {
    this.communicationBus = communicationBus;
    this.turnManager = turnManager;
    messageSender = new MessageSender(communicationBus, logger);
  }

  /**
   * Process end of game with the winner, and close communication bus.
   *
   * @return GameState
   */
  @Override
  public GameState process() {
    handleEndOfGame();
    communicationBus.stop();
    logger.info("Game ended");
    return this;
  }

  /**
   * Set that game should not by continued since we have a winner.
   *
   * @return boolean
   */
  @Override
  public boolean shouldBeContinued() {
    return false;
  }

  private void handleEndOfGame() {
    messageSender.send(turnManager.getCurrentPlayer(), Header.WIN);
    messageSender.send(turnManager.getOtherPlayer(), Header.LOSE);
  }
}
