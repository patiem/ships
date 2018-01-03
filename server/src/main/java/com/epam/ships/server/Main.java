package com.epam.ships.server;

import com.epam.ships.infra.logging.api.Target;
import com.epam.ships.infra.logging.core.SharedLogger;
import com.epam.ships.server.gamestates.GameState;
import com.epam.ships.server.gamestates.WaitingForPlayersState;

import java.io.IOException;

/**
 * Main class of server application.
 *
 * @author Piotr Czyż
 * @since 2017-12-09
 */
public class Main {
  private static final Target logger = new SharedLogger(Main.class);
  private static final int REST_TIME = 300;

  /**
   * Main server method. It creates communication bus and starts the game.
   */
  public static void main(String[] args) throws IOException {
    CommunicationBus communicationBus = new CommunicationBus();
    boolean shouldRun = true;
    while (shouldRun) {
      GameState initialState = new WaitingForPlayersState(communicationBus);
      new Game(initialState).loop();
      try {
        Thread.sleep(REST_TIME);
      } catch (final InterruptedException e) {
        logger.error(e.getMessage());
        Thread.currentThread().interrupt();
      }
    }
  }
}
